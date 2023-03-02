package com.droidboi.common.rest.retrofit.exceptions

import java.io.EOFException

/**
 * Custom [EOFException] to be used strictly for Test assertions.
 *
 * @param message [String] as message for [Throwable].
 * @author Ritwik Jamuar
 */
class CustomEOFException(override val message: String?) : EOFException() {

	/*------------------------------------- Object Callbacks -------------------------------------*/

	override fun equals(other: Any?): Boolean {

		if (other == null)
			return false

		if (other !is EOFException)
			return false

		return true

	}

	override fun hashCode(): Int = message?.hashCode() ?: 0

}
