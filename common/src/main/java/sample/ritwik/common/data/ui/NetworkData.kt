package sample.ritwik.common.data.ui

import sample.ritwik.common.data.network.NetworkType

import sample.ritwik.common.utility.constant.NETWORK_ACTION_NONE

/**
 * Data Class to contain the data related to Network.
 *
 * @param action [Integer] to mark any Action. Default is [NETWORK_ACTION_NONE].
 * @param isNetworkAvailable [Boolean] flag to tell whether the network is available or not.
 *   Default value is false.
 * @param networkType Instance of [NetworkType] to identify the type of Active Network Connection.
 *   Default is [NetworkType.None].
 * @author Ritwik Jamuar
 */
data class NetworkData(
    var action: Int = NETWORK_ACTION_NONE,
    var isNetworkAvailable: Boolean = false,
    var networkType: NetworkType = NetworkType.None()
)
