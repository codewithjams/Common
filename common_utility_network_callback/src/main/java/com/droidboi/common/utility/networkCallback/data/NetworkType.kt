package com.droidboi.common.utility.networkCallback.data

import com.droidboi.common.utility.networkCallback.constant.*

/**
 * Represents the Type of Network onto which Data Connection is established.
 *
 * @author Ritwik Jamuar
 */
sealed class NetworkType {

	/**
	 * [NetworkType] denoting WiFi Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	object WiFi: NetworkType() { override fun toString(): String = NETWORK_WiFi }

	/**
	 * [NetworkType] denoting Cellular Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	sealed class Cellular: NetworkType() {

		/**
		 * [Cellular] Network but uncertain about Generation of Telephony used.
		 *
		 * @author Ritwik Jamuar
		 */
		object Unknown: Cellular() { override fun toString(): String = NETWORK_CELLULAR_UNKNOWN }

		/**
		 * [Cellular] Network with 2G Connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object Generation2: Cellular() { override fun toString(): String = NETWORK_GENERATION_2 }

		/**
		 * [Cellular] Network with 3G Connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object Generation3: Cellular() { override fun toString(): String = NETWORK_GENERATION_3 }

		/**
		 * [Cellular] Network with 4G Connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object Generation4: Cellular() { override fun toString(): String = NETWORK_GENERATION_4 }

		/**
		 * [Cellular] Network with 5G Connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object Generation5: Cellular() { override fun toString(): String = NETWORK_GENERATION_5 }

	}

	/**
	 * [NetworkType] denoting Ethernet Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	object Ethernet: NetworkType() { override fun toString(): String = NETWORK_ETHERNET }

	/**
	 * [NetworkType] denoting VPN Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	object VPN: NetworkType() { override fun toString(): String = NETWORK_VPN }

	/**
	 * [NetworkType] denoting Bluetooth Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	object Bluetooth: NetworkType() { override fun toString(): String = NETWORK_BLUETOOTH }

	/**
	 * [NetworkType] denoting None or Unknown Data Connection.
	 *
	 * @author Ritwik Jamuar
	 */
	object None : NetworkType() { override fun toString(): String = "" }

}
