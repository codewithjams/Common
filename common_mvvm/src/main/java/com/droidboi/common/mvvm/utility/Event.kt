package com.droidboi.common.mvvm.utility

/**
 * Generic Value Container to emulate the Single Event nature.
 *
 * @param T Any Class
 * @param content [T] as value to be stored.
 * @author Ritwik Jamuar
 */
data class Event<T>(private val content: T) {

	/**
	 * [Boolean] flag to check if the content has been accessed already.
	 */
	private var handled: Boolean = false

	/**
	 * Provides the content if it hasn't been [handled] previously.
	 *
	 * @return [content] if not [handled], else `null`.
	 * @see peekContent
	 */
	fun getContentIfNotHandled(): T? = if (handled) {
		null
	} else {
		this.handled = true
		content
	}

	/**
	 * Shows the content in this container.
	 *
	 *
	 * Though, invoking this method will not change the Container behavior.
	 *
	 * @return [content].
	 * @see getContentIfNotHandled
	 */
	fun peekContent(): T = content

}
