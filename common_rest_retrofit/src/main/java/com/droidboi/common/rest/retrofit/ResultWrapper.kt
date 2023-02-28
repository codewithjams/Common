package com.droidboi.common.rest.retrofit

/**
 * Sealed Class representing different states of a REST API Call.
 *
 * @param T Any Data Class
 * @author Ritwik Jamuar
 */
sealed class ResultWrapper<out T> {

	/**
	 * [ResultWrapper] denoting the Loading state.
	 *
	 * @author Ritwik Jamuar
	 */
	object Loading : ResultWrapper<Nothing>()

	/**
	 * [ResultWrapper] denoting the Success state.
	 *
	 * @param T Any Data Class
	 * @param data Instance of [T] denoting the data successfully processed.
	 * @author Ritwik Jamuar
	 */
	data class Success<T>(val data: T) : ResultWrapper<T>()

	/**
	 * [ResultWrapper] denoting the Error state.
	 *
	 * @author Ritwik Jamuar
	 */
	sealed class Error : ResultWrapper<Nothing>() {

		/**
		 * [Error] caused when there's something wrong with Network Connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object NetworkErrorConnection : Error()

		/**
		 * [Error] caused when SSL Handshake couldn't happen between Client & Server,
		 * hence unable to establish a secure connection.
		 *
		 * @author Ritwik Jamuar
		 */
		object NetworkErrorSSLHandShake : Error()

		/**
		 * [Error] caused when something went wrong while the REST API Call performs
		 * the IO operations.
		 *
		 * @author Ritwik Jamuar
		 */
		object NetworkErrorIO : Error()

		/**
		 * [Error] caused in the source server.
		 *
		 * @param statusCodeHTTP [Int] denoting the HTTP Status Code when the REST API Call failed.
		 * @param errorCode [String] denoting the Error Code thrown by the source.
		 * @param errorDescription [String] describing the cause of Error mentioned by the source.
		 */
		data class Recoverable(
			val statusCodeHTTP: Int,
			val errorCode: String,
			val errorDescription: String
		) : Error()

		/**
		 * [Error] caused due to current user's session being expired.
		 *
		 * @author Ritwik Jamuar
		 */
		object SessionExpired : Error()

		/**
		 * [Error] is not enlisted in known causes and the cause is wrapped in [throwable].
		 *
		 * @param throwable Instance of [Throwable] wrapping up the cause of failure of
		 *   REST API Call.
		 * @author Ritwik Jamuar
		 */
		data class Unknown(val throwable: Throwable) : Error()

	}

}
