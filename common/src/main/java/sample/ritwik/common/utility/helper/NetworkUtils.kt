package sample.ritwik.common.utility.helper

import android.net.*

import android.os.Build

import android.telephony.TelephonyManager

import androidx.annotation.MainThread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import sample.ritwik.common.BuildConfig

import sample.ritwik.common.data.network.NetworkType

import sample.ritwik.common.data.ui.NetworkData

import sample.ritwik.common.mvvm.viewModel.BaseViewModel

import sample.ritwik.common.utility.constant.NETWORK_ACTION_CHANGED
import sample.ritwik.common.utility.constant.NETWORK_PERMISSION_REQUIRED

import javax.inject.Inject

/**
 * Utility Class to provide proper updates on Network.
 *
 * @param connectivityManager Reference of [ConnectivityManager] to register/un-register any
 *   [ConnectivityManager.NetworkCallback] and determine the [NetworkInfo].
 * @param telephonyManager Reference of [TelephonyManager] to determine the Mobile Network Type.
 * @author Ritwik Jamuar
 */
class NetworkUtils @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val telephonyManager: TelephonyManager
) {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [NetworkRequest] to be used with [connectivityManager] for
     * registering the [networkCallback].
     */
    private val networkRequest: NetworkRequest by lazy { provideNetworkRequest() }

    /**
     * [CoroutineScope] with [Dispatchers.Main] for performing any operation under Main Thread.
     */
    private lateinit var mainThreadScope: CoroutineScope

    /*----------------------------------------- LiveData -----------------------------------------*/

    /**
     * [MutableLiveData] to propagate the changes of [networkData] to it's Observers
     * via [networkLiveData].
     */
    private lateinit var _networkLiveData: MutableLiveData<NetworkData>

    /**
     * [LiveData] to propagate the changes in [networkData] to it's Observers.
     */
    val networkLiveData: LiveData<NetworkData>
        get() = _networkLiveData

    /*------------------------------------- Member Variables -------------------------------------*/

    /**
     * Reference of [NetworkData] to contain all the data related to Network.
     */
    private lateinit var networkData: NetworkData

    /**
     * [Boolean] flag to denote whether the reading Phone State Permission is granted by the User
     * or not.
     */
    var isReadingPhoneStatePermitted: Boolean = false

    /*------------------------------------- Network Callbacks ------------------------------------*/

    /**
     * Callback of [connectivityManager].
     */
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            onNetworkUpdate()
        }

        override fun onUnavailable() {
            super.onUnavailable()
            onNetworkUpdate()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            onNetworkUpdate()
        }

    }

    /*------------------------------------ Initializer Block -------------------------------------*/

    init {
        initializeComponents()
    }

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Registers the callbacks of Network.
     */
    fun registerCallbacks() = try {
        unRegisterNetworkCallbacks()
        registerNetworkCallbacks()
    } catch (e: Exception) {
        registerNetworkCallbacks()
    }

    /**
     * Un-Registers the callbacks of Network.
     */
    fun unRegisterCallbacks() = try {
        unRegisterNetworkCallbacks()
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            android.util.Log.e(NetworkUtils::class.java.simpleName, "")
        }
    }

    /**
     * Determines the type of Network.
     *
     * @return New Instance of [NetworkType] which classifies the type of Network.
     */
    @Suppress("DEPRECATION")
    fun determineNetworkType(): NetworkType = try {
        val info: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (info == null || !info.isConnected) {
            NetworkType.None()
        } else {
            when(info.type) {
                ConnectivityManager.TYPE_WIFI -> NetworkType.WiFi()
                ConnectivityManager.TYPE_MOBILE -> determineMobileNetworkType()
                else -> NetworkType.None()
            }
        }
    } catch (e: SecurityException) {
        networkData.action = NETWORK_PERMISSION_REQUIRED
        NetworkType.None()
    } catch (e: Exception) {
        NetworkType.None()
    }

    /*-------------------------------------- Private Methods -------------------------------------*/

    /**
     * Initializes all the components of [NetworkUtils].
     */
    private fun initializeComponents() {
        initializeCoRoutineScope()
        initializeLiveData()
        initializeVariables()
    }

    /**
     * Initializes the [CoroutineScope]s of this [BaseViewModel].
     */
    private fun initializeCoRoutineScope() {
        mainThreadScope = CoroutineScope(Dispatchers.Main)
    }

    /**
     * Initializes all the [LiveData]s.
     */
    private fun initializeLiveData() {
        _networkLiveData = MutableLiveData()
    }

    /**
     * Initializes all the variables.
     */
    private fun initializeVariables() {
        networkData = NetworkData()
    }

    /**
     * Provides the [NetworkRequest] with all types of HTTP Transport added to it.
     *
     * @return New Instance of [NetworkRequest].
     */
    private fun provideNetworkRequest(): NetworkRequest = NetworkRequest.Builder().apply {

        safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_CELLULAR)
        safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_WIFI)
        safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_BLUETOOTH)
        safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_ETHERNET)
        safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_VPN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_WIFI_AWARE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            safelyAddTransportType(this, NetworkCapabilities.TRANSPORT_LOWPAN)
        }

    }.build()

    /**
     * Safely adds a given [NetworkCapabilities] to the [builder].
     *
     * @param builder Instance of [NetworkRequest.Builder] on which [capability] will be added.
     * @param capability [Int] denoting a constant from [NetworkCapabilities].
     */
    private fun safelyAddTransportType(builder: NetworkRequest.Builder, capability: Int) = try {
        builder.addTransportType(capability)
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace()
        } else {
            android.util.Log.e(NetworkUtils::class.java.simpleName, "")
        }
    }

    /**
     * Registers the [networkCallback] using [connectivityManager].
     */
    private fun registerNetworkCallbacks() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    /**
     * Un-Registers the [networkCallback] using [connectivityManager].
     */
    private fun unRegisterNetworkCallbacks() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    /**
     * Handles the update of Network from [networkCallback].
     */
    private fun onNetworkUpdate() {

        // Set the values of NetworkData.
        with(networkData) {
            action = NETWORK_ACTION_CHANGED
            isNetworkAvailable = isNetworkAvailable()
            networkType = determineNetworkType()
        }

        mainThreadScope.launch {
            notifyUpdateOnNetworkData() // Notify the change through LiveData.
        }

    }

    /**
     * Checks whether the device is connected to the Network or not.
     *
     *
     * In order to determine connectivity, [connectivityManager] is used to fetch the [NetworkInfo].
     *
     *
     * Through [NetworkInfo], it is determined whether device is connected to the Network or not.
     *
     * @return false, if the [NetworkInfo] is either not extracted from [connectivityManager] or
     *   Network is not connected, else true.
     */
    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean = try {
        val info: NetworkInfo? = connectivityManager.activeNetworkInfo
        info?.isConnected ?: false
    } catch (e: Exception) {
        false
    }

    /**
     * Determines the type of Mobile Network
     *
     * @return New Instance of [NetworkType] which classifies the type of Mobile Network.
     * @throws SecurityException This exception is thrown when accessing the method
     *   [TelephonyManager.getNetworkType] while the permission for Reading Phone State
     *   is not granted by the user.
     */
    @Suppress("DEPRECATION")
    @Throws(SecurityException::class)
    private fun determineMobileNetworkType(): NetworkType = if (isReadingPhoneStatePermitted) {
        when (telephonyManager.networkType) {

            TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN, TelephonyManager.NETWORK_TYPE_GSM
            -> NetworkType.Mobile.Generation2()

            TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EHRPD, TelephonyManager.NETWORK_TYPE_TD_SCDMA
            -> NetworkType.Mobile.Generation3()

            TelephonyManager.NETWORK_TYPE_LTE, TelephonyManager.NETWORK_TYPE_IWLAN
            -> NetworkType.Mobile.Generation4()

            TelephonyManager.NETWORK_TYPE_NR
            -> NetworkType.Mobile.Generation5()

            TelephonyManager.NETWORK_TYPE_UNKNOWN
            -> NetworkType.None()

            else -> NetworkType.None()

        }
    } else {
        networkData.action = NETWORK_PERMISSION_REQUIRED
        NetworkType.None()
    }

    /**
     * Notifies the change in [networkData] via [networkLiveData].
     *
     *
     * NOTE: Make sure to run this method in Main Thread.
     *
     * @throws IllegalStateException This exception is thrown by accessing [LiveData.setValue] in
     *   Background Thread.
     */
    @MainThread
    private fun notifyUpdateOnNetworkData() {
        _networkLiveData.value = networkData
    }

}
