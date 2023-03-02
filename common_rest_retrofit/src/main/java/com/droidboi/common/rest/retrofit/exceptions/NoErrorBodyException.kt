package com.droidboi.common.rest.retrofit.exceptions

import com.droidboi.common.rest.retrofit.RetrofitRESTCaller

private const val ERROR_MESSAGE_ERROR_BODY_NOT_FOUND = "Error Body not found"

/**
 * [RuntimeException] indicating that [okhttp3.ResponseBody] masquerading as Error Body is not found
 * from the [retrofit2.Response] after [RetrofitRESTCaller.performRESTCall].
 *
 * @see RetrofitRESTCaller.performRESTCall
 * @author Ritwik Jamuar
 */
class NoErrorBodyException : RuntimeException() {

	/*----------------------------------- Throwable Callbacks ------------------------------------*/

	override val message: String
		get() = ERROR_MESSAGE_ERROR_BODY_NOT_FOUND

	/*------------------------------------- Object Callbacks -------------------------------------*/

	override fun equals(other: Any?): Boolean {

		if (other == null)
			return false

		if (other !is NoErrorBodyException)
			return false

		return true

	}

	override fun hashCode(): Int {
		return javaClass.hashCode()
	}

}
