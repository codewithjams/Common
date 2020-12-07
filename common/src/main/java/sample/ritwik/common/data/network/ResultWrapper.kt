package sample.ritwik.common.data.network

/**
 * Class to Map Result of call to any REST API.
 *
 * @param T Any Class
 * @author Ritwik Jamuar
 */
sealed class ResultWrapper<out T> {

    /**
     * Extension of [ResultWrapper] denoting the result of REST API as Success.
     *
     * @param T Any Class
     * @param data Instance of [T] denoting the Data received from response of REST API.
     * @author Ritwik Jamuar
     */
    data class Success<out T>(val data: T): ResultWrapper<T>()

    /**
     * Extension of [ResultWrapper] denoting the result of REST API as Error.
     *
     * @param T Any Class
     * @author Ritwik Jamuar
     */
    sealed class Error<out T>: ResultWrapper<T>() {

        /**
         * Extension of [Error] denoting the Error as Connection Error.
         *
         * @param T Any Class
         * @author Ritwik Jamuar
         */
        class NetworkConnectionError<out T>: Error<T>()

        /**
         * Extension of [Error] denoting the Error as SSL Hand Shake Error.
         *
         * @param T Any Class
         * @author Ritwik Jamuar
         */
        class SSLHandShakeError<out T>: Error<T>()

        /**
         * Extension of [Error] denoting the Error as Network Error.
         *
         * @param T Any Class
         * @author Ritwik Jamuar
         */
        class NetworkError<out T>: Error<T>()

        /**
         * Extension of [Error] denoting the Error which is recoverable in nature.
         *
         * @param T Any Class
         * @param code [Integer] denoting the Error Code.
         * @param message [String] denoting the Error Message or Error Description.
         * @author Ritwik Jamuar
         */
        data class RecoverableError<out T>(
            val code: Int,
            val message: String
        ): Error<T>()

        /**
         * Extension of [Error] denoting the expiry of current Session of the User.
         *
         * @param T Any Class
         * @author Ritwik Jamuar
         */
        class SessionExpired<out T>: Error<T>()

        /**
         * Extension of [Error] denoting the Error of any category.
         *
         * @param T Any Class
         * @author Ritwik Jamuar
         */
        class Other<out T>: Error<T>()

    }

}
