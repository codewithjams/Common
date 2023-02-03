package com.droidboi.common.utility.networkCallback.data

/**
 * Various State of Network.
 *
 * @author Ritwik Jamuar
 */
enum class NetworkConnectivityStatus {

	/**
	 * Denotes that Network is available.
	 */
	AVAILABLE,

	/**
	 * Denotes that Network is unavailable.
	 */
	UNAVAILABLE,

	/**
	 * Denotes that Network is on verge of losing connectivity.
	 */
	LOSING,

	/**
	 * Denotes that Network is lost.
	 */
	LOST

}
