package sample.ritwik.common.data.network

import sample.ritwik.common.utility.constant.*

/**
 * Open Class to Map different types of Network.
 *
 * @author Ritwik Jamuar
 */
open class NetworkType {

    /**
     * Class to identify itself as [NetworkType] of WiFi.
     *
     * @author Ritwik Jamuar
     */
    class WiFi: NetworkType() {

        override fun toString(): String = NETWORK_WiFi

    }

    /**
     * Class to identify itself as [NetworkType] of Mobile.
     *
     * @author Ritwik Jamuar
     */
    open class Mobile: NetworkType() {

        /**
         * Class to identify itself as [Mobile] Network with 2G Connection.
         *
         * @author Ritwik Jamuar
         */
        class Generation2: Mobile() {

            override fun toString(): String = NETWORK_GENERATION_2

        }

        /**
         * Class to identify itself as [Mobile] Network with 3G Connection.
         *
         * @author Ritwik Jamuar
         */
        class Generation3: Mobile() {

            override fun toString(): String = NETWORK_GENERATION_3

        }

        /**
         * Class to identify itself as [Mobile] Network with 4G Connection.
         *
         * @author Ritwik Jamuar
         */
        class Generation4: Mobile() {

            override fun toString(): String = NETWORK_GENERATION_4

        }

        /**
         * Class to identify itself as [Mobile] Network with 5G Connection.
         *
         * @author Ritwik Jamuar
         */
        class Generation5: Mobile() {

            override fun toString(): String = NETWORK_GENERATION_5

        }

    }

    /**
     * Class to identify itself as No [NetworkType].
     *
     * @author Ritwik Jamuar
     */
    class None: NetworkType() {

        override fun toString(): String = ""

    }

}
