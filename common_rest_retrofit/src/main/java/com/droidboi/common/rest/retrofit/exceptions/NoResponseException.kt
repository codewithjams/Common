package com.droidboi.common.rest.retrofit.exceptions

import com.droidboi.common.rest.retrofit.RetrofitRESTCaller

private const val ERROR_MESSAGE_NO_RESPONSE = "No Response"

/**
 * [RuntimeException] indicating that [retrofit2.Response] we get from performing REST API Call is not provided
 * after [RetrofitRESTCaller.performRESTCall].
 *
 * @see RetrofitRESTCaller.performRESTCall
 * @author Ritwik Jamuar
 */
class NoResponseException : RuntimeException() {

	override val message: String
		get() = ERROR_MESSAGE_NO_RESPONSE

}
