package com.droidboi.common.utility.networkCallback.data

/**
 * Data Class to contain the data related to Network.
 *
 * @param available [Boolean] flag to tell whether the network is available or not.
 * @param type Instance of [NetworkType] to identify the type of Active Network Connection.
 * @param status Instance of [NetworkConnectivityStatus] denoting the status of current Network.
 * @author Ritwik Jamuar
 */
data class NetworkData(
	val available: Boolean = false,
	val type: NetworkType = NetworkType.None,
	val status: NetworkConnectivityStatus = NetworkConnectivityStatus.UNAVAILABLE
)
