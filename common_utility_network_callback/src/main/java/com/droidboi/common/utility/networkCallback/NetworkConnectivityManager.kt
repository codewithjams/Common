package com.droidboi.common.utility.networkCallback

import android.Manifest.permission.READ_PHONE_STATE

import android.content.Context

import android.content.pm.PackageManager

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

import android.os.Build
import android.os.Build.VERSION.SDK_INT

import android.telephony.TelephonyManager

import androidx.annotation.RequiresApi

import androidx.core.app.ActivityCompat

import com.droidboi.common.utility.networkCallback.data.NetworkConnectivityStatus
import com.droidboi.common.utility.networkCallback.data.NetworkData
import com.droidboi.common.utility.networkCallback.data.NetworkType

import kotlinx.coroutines.flow.Flow

/**
 * Manages to listen to changes in Network.
 *
 * @author Ritwik Jamuar
 */
interface NetworkConnectivityManager {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [Context] used to instantiate [connectivityManager] & [telephonyManager] and
	 * also determining the necessary permission.
	 */
	val context: Context

	/**
	 * Reference of [ConnectivityManager] to listen to [ConnectivityManager.NetworkCallback]
	 * and determine the type of Network.
	 */
	val connectivityManager: ConnectivityManager

	/**
	 * Reference of [TelephonyManager] to determine the type of Cellular Network.
	 */
	val telephonyManager: TelephonyManager

	/**
	 * [Flow] of [NetworkData] to propagate the update in Network connectivity to its collectors.
	 */
	val networkState: Flow<NetworkData>

	/**
	 * Reference of [Listener] to propagate events from this [NetworkConnectivityManager]
	 */
	var listener: Listener?

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Trigger for registering Network Callbacks.
	 */
	fun registerNetworkCallback()

	/**
	 * Trigger for un-registering Network Callbacks.
	 */
	fun unRegisterNetworkCallback()

	/**
	 * Propagates the latest [data] to the consumers of [networkState].
	 *
	 * @param data [NetworkData] encapsulating the current Network information.
	 */
	fun setValue(data: NetworkData)

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Registers the necessary callbacks.
	 */
	fun registerCallbacks() {
		try {
			unRegisterNetworkCallback()
			registerNetworkCallback()
		} catch (e: Exception) {
			registerNetworkCallback()
		}
	}

	/**
	 * Un-registers the necessary callbacks.
	 */
	fun unRegisterCallbacks() {
		try {
			unRegisterNetworkCallback()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	/**
	 * For a given [network] determine the type of Connectivity.
	 *
	 * @param network Currently active [Network].
	 * @param status [NetworkConnectivityStatus] as the current Status of Network.
	 */
	fun determineNetworkType(network: Network?, status: NetworkConnectivityStatus) {

		// If network provided is null, it can mean that network is simply unavailable.
		// Thus, e would set nothing as Connected Network type and halt the further execution.
		if (network == null) {
			setValue(NetworkData( false, NetworkType.None, status))
			return
		}

		// If the latest status of network in Unavailable, as the status suggests
		// the network itself is not available, thus we would set nothing as Connected Network Type
		// and halt the further execution.
		if (NetworkConnectivityStatus.UNAVAILABLE == status) {
			setValue(NetworkData(false, NetworkType.None, status))
			return
		}

		// Get the Network Capabilities from Connectivity Manager for current network.
		val capabilities = connectivityManager.getNetworkCapabilities(network)

		// If the Network Capabilities are not found for a given network,
		// we can't determine the type of Connectivity for this network.
		// Thus, we would set nothing as Connected Network type and halt the further execution.
		if (capabilities == null) {
			setValue(NetworkData(false, NetworkType.None, status))
			return
		}

		when {
			capabilities.hasWiFi() ->
				setValue(NetworkData(true, NetworkType.WiFi, status))
			capabilities.hasEthernet() ->
				setValue(NetworkData(true, NetworkType.Ethernet, status))
			capabilities.hasVPN() ->
				setValue(NetworkData(true, NetworkType.VPN, status))
			capabilities.hasBluetooth() ->
				setValue(NetworkData(true, NetworkType.Bluetooth, status))
			capabilities.hasMobile() ->
				determineCellularNetworkType(network, status)
		}

	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Determines the Cellular [NetworkType] from given [network].
	 *
	 * @param network Currently active [Network] onto which determination is to be done.
	 */
	private fun determineCellularNetworkType(network: Network, status: NetworkConnectivityStatus) {

		// Presently, there are two ways we can determine the Cellular Network type.
		// 1. Modern -> Best Approach, but only works post Android Nougat (24)
		// 2. Legacy -> Normal Approach, but methods used are deprecated.

		if (SDK_INT >= Build.VERSION_CODES.N) {
			determineCellularNetworkTypeModern(status)
		} else {
			determineCellularNetworkTypeLegacy(network, status)
		}

	}

	/**
	 * Modern approach to determine the type of Cellular Network.
	 */
	@RequiresApi(api = Build.VERSION_CODES.N)
	private fun determineCellularNetworkTypeModern(status: NetworkConnectivityStatus) {

		// If the permission to read Phone's state is not granted by  the user,
		// halt the further execution because for modern approach to work,
		// user must grant this application of the permission mentioned.
		// Also, before halting, we set the Network Type as Cellular Unknown,
		// as we are sure at this point that the Network Type is Cellular.
		if (!context.checkPermission(READ_PHONE_STATE)) {
			setValue(NetworkData(true, NetworkType.Cellular.Unknown, status))
			listener?.onReadPhoneStateNotGranted()
			return
		}

		// At this point, the permission to read Phone's state is granted by the user,
		// so we can employ the modern approach now.

		setValue(NetworkData(true, getCellularNetworkType(telephonyManager.dataNetworkType), status))

	}

	/**
	 * Legacy approach to determine the type of Cellular Network.
	 *
	 * @param network Current [Network] onto which determination is to be done.
	 */
	private fun determineCellularNetworkTypeLegacy(network: Network, status: NetworkConnectivityStatus) {

		// Get the NetworkInfo of current Network from ConnectivityManager.
		@Suppress("DEPRECATION")
		val networkInfo = connectivityManager.getNetworkInfo(network)

		// If the NetworkInfo is not available for current Network, then it's not possible
		// to determine the specific Cellular Network type.
		// So, we set the currently connected Network Type as Cellular Unknown
		// and halt the further execution.
		if (networkInfo == null) {
			setValue(NetworkData(true, NetworkType.Cellular.Unknown, status))
			return
		}

		@Suppress("DEPRECATION")
		setValue(NetworkData(true, getCellularNetworkType(networkInfo.subtype), status))

	}

	/**
	 * Provides the type of Cellular Network from the given argument.
	 *
	 * @param type [Integer] as one of the constants from [TelephonyManager]
	 * denoting the Cellular Network Type.
	 * @return Determined [NetworkType.Cellular] denoting the Cellular Network Type based on [type].
	 */
	private fun getCellularNetworkType(type: Int): NetworkType.Cellular {

		val networkTypeIDEN = if (SDK_INT >= Build.VERSION_CODES.FROYO) {
			TelephonyManager.NETWORK_TYPE_IDEN
		} else {
			11 /* API <  8: Replace by 11 */
		}

		val networkTypeGSM = if (SDK_INT >= Build.VERSION_CODES.N_MR1) {
			TelephonyManager.NETWORK_TYPE_GSM
		} else {
			16 /* API < 25: Replace by 16 */
		}

		val networkTypeEVDORevB = if (SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			TelephonyManager.NETWORK_TYPE_EVDO_B
		} else {
			12 /* API <  9: Replace by 12 */
		}

		val networkTypeHSPAP = if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			TelephonyManager.NETWORK_TYPE_HSPAP
		} else {
			15 /* API < 13: Replace by 15 */
		}

		val networkTypeEHRPD = if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			TelephonyManager.NETWORK_TYPE_EHRPD
		} else {
			14 /* API < 11: Replace by 14 */
		}

		val networkTypeTDSCDMA = if (SDK_INT >= Build.VERSION_CODES.N_MR1) {
			TelephonyManager.NETWORK_TYPE_TD_SCDMA
		} else {
			17 /* API < 25: Replace by 17 */
		}

		val networkTypeLTE = if (SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			TelephonyManager.NETWORK_TYPE_LTE
		} else {
			13 /* API < 11: Replace by 13 */
		}

		val networkTypeIWLAN = if (SDK_INT >= Build.VERSION_CODES.N_MR1) {
			TelephonyManager.NETWORK_TYPE_IWLAN
		} else {
			18 /* API < 25: Replace by 18 */
		}

		return when (type) {

			TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE,
			TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT,
			networkTypeIDEN, networkTypeGSM ->
				NetworkType.Cellular.Generation2

			TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0,
			TelephonyManager.NETWORK_TYPE_EVDO_A, networkTypeEVDORevB,
			TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA,
			TelephonyManager.NETWORK_TYPE_HSPA, networkTypeHSPAP, networkTypeEHRPD,
			networkTypeTDSCDMA ->
				NetworkType.Cellular.Generation3

			networkTypeLTE, networkTypeIWLAN ->
				NetworkType.Cellular.Generation4

			TelephonyManager.NETWORK_TYPE_NR ->
				NetworkType.Cellular.Generation5

			TelephonyManager.NETWORK_TYPE_UNKNOWN ->
				NetworkType.Cellular.Unknown

			else -> NetworkType.Cellular.Unknown

		}

	}

	/**
	 * For a given [NetworkCapabilities] checks if it supports WiFi.
	 */
	private fun NetworkCapabilities.hasWiFi(): Boolean =
		if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
			true
		} else {
			if (SDK_INT >= Build.VERSION_CODES.O) {
				hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE)
			} else {
				false
			}
		}

	/**
	 * For a given [NetworkCapabilities] checks if it supports Ethernet.
	 */
	private fun NetworkCapabilities.hasEthernet(): Boolean =
		if (hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
			true
		} else {
			if (SDK_INT >= Build.VERSION_CODES.S) {
				hasTransport(NetworkCapabilities.TRANSPORT_USB)
			} else {
				false
			}
		}

	/**
	 * For a given [NetworkCapabilities] checks if it supports VPN.
	 */
	private fun NetworkCapabilities.hasVPN(): Boolean =
		hasTransport(NetworkCapabilities.TRANSPORT_VPN)

	/**
	 * For a given [NetworkCapabilities] checks if it supports Bluetooth.
	 */
	private fun NetworkCapabilities.hasBluetooth(): Boolean =
		hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)

	/**
	 * For a given [NetworkCapabilities] checks if it supports Mobile.
	 */
	private fun NetworkCapabilities.hasMobile(): Boolean =
		hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

	/**
	 * For a given context checks whether the given [permission] is granted or not.
	 *
	 * @param permission [String] denoting the permission for checking whether it is granted or not.
	 * @return `true` if granted, else `false`.
	 */
	private fun Context.checkPermission(permission: String): Boolean =
		ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

	/*---------------------------------------- Interfaces ----------------------------------------*/

	/**
	 * Propagates important events from [NetworkConnectivityManager].
	 *
	 * @author Ritwik Jamuar
	 */
	interface Listener {

		/**
		 * Notifies that permission for reading Phone's state is not granted by the user.
		 */
		fun onReadPhoneStateNotGranted()

	}

}
