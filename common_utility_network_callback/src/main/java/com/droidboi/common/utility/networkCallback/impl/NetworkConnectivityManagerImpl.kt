package com.droidboi.common.utility.networkCallback.impl

import android.content.Context

import android.net.*

import android.os.Build

import android.telephony.TelephonyManager

import com.droidboi.common.utility.networkCallback.NetworkConnectivityManager

import com.droidboi.common.utility.networkCallback.data.NetworkConnectivityStatus
import com.droidboi.common.utility.networkCallback.data.NetworkData

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

import javax.inject.Inject

class NetworkConnectivityManagerImpl @Inject constructor(override val context: Context) :
	NetworkConnectivityManager {

	/*---------------------------------------- Components ----------------------------------------*/

	private val networkRequest: NetworkRequest by lazy {
		NetworkRequest.Builder().apply {

			safelyAddTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
			safelyAddTransportType(NetworkCapabilities.TRANSPORT_WIFI)
			safelyAddTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
			safelyAddTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
			safelyAddTransportType(NetworkCapabilities.TRANSPORT_VPN)

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				safelyAddTransportType(NetworkCapabilities.TRANSPORT_WIFI_AWARE)
			}

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
				safelyAddTransportType(NetworkCapabilities.TRANSPORT_LOWPAN)
			}

		}.build()
	}

	private val _networkState = MutableStateFlow(NetworkData())

	private var _listener: NetworkConnectivityManager.Listener? = null

	/*------------------------------------- Network Callbacks ------------------------------------*/

	private val networkCallback = object : ConnectivityManager.NetworkCallback() {

		override fun onAvailable(network: Network) {
			super.onAvailable(network)
			determineNetworkType(network, NetworkConnectivityStatus.AVAILABLE)
		}

		override fun onLosing(network: Network, maxMsToLive: Int) {
			super.onLosing(network, maxMsToLive)
			determineNetworkType(network, NetworkConnectivityStatus.LOSING)
		}

		override fun onLost(network: Network) {
			super.onLost(network)
			determineNetworkType(network, NetworkConnectivityStatus.LOST)
		}

		override fun onUnavailable() {
			super.onUnavailable()
			determineNetworkType(null, NetworkConnectivityStatus.UNAVAILABLE)
		}

	}

	/*------------------------------ NetworkCallbackUtils Callbacks ------------------------------*/

	override val networkState: Flow<NetworkData>
		get() = _networkState

	override val connectivityManager: ConnectivityManager =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

	override val telephonyManager: TelephonyManager =
		context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

	override var listener: NetworkConnectivityManager.Listener? = _listener

	override fun registerNetworkCallback() {
		connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
	}

	override fun unRegisterNetworkCallback() {
		connectivityManager.unregisterNetworkCallback(networkCallback)
	}

	override fun setValue(data: NetworkData) {
		_networkState.value = data
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	private fun NetworkRequest.Builder.safelyAddTransportType(capabilities: Int) {
		try {
			addTransportType(capabilities)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

}
