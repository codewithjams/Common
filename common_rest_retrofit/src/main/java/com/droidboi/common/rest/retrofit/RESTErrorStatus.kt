package com.droidboi.common.rest.retrofit

/**
 * Sealed Class representing different kind of statuses one can encounter
 * when REST API Call fails.
 *
 * @author Ritwik Jamuar
 */
sealed class RESTErrorStatus {

	/**
	 * Indicates that the REST API Call failure is recoverable and thrown by source
	 * indicating some exception with Server Side.
	 *
	 * @param statusCodeHTTP [Int] denoting the HTTP Status Code of the REST API Failure.
	 * @param errorCode [String] denoting the Error Code provided from the source.
	 * @param errorDescription [String] denoting the description of Error provided
	 * from the source.
	 * @author Ritwik Jamuar
	 */
	data class Recoverable(
		val statusCodeHTTP: Int,
		val errorCode: String,
		val errorDescription: String
	) : RESTErrorStatus()

	/**
	 * Indicates that the REST API Call failure is a result of current user's session
	 * being expired.
	 *
	 * @author Ritwik Jamuar
	 */
	object SessionExpired : RESTErrorStatus()

	/**
	 * Indicates that the REST API Call failure is a result of absence of Error Body.
	 *
	 * @author Ritwik Jamuar
	 */
	object NoErrorBody : RESTErrorStatus()

}
