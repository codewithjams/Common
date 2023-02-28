package com.droidboi.common.persistence

/**
 * Abstraction for encapsulating common functionalities that a Persistence Storage should offer.
 *
 * @author Ritwik Jamuar
 */
interface Persistence {

	/**
	 * Retrieves a [String] stored against given [key] from persistence storage.
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [String] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [String] if value is there,
	 *   else `null`.
	 */
	suspend fun retrieveString(key: String, defaultValue: String? = null): String?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [String] denoting the Value we wish to persist against the given [key].
	 */
	suspend fun persistString(key: String, value: String?)

	/**
	 * Retrieves a [Int] stored against given [key] from persistence storage.
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [Int] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [Int] if value is there,
	 *   else `null`.
	 */
	suspend fun retrieveInteger(key: String, defaultValue: Int = 0): Int?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [Int] denoting the Value we wish to persist against the given [key].
	 */
	suspend fun persistInteger(key: String, value: Int?)

	/**
	 * Retrieves a [Long] stored against given [key] from persistence storage.
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [Long] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [Long] if value is there,
	 *   else `null`.
	 */
	suspend fun retrieveLong(key: String, defaultValue: Long = 0L): Long?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [Long] denoting the Value we wish to persist against the given [key].
	 */
	suspend fun persistLong(key: String, value: Long?)

	/**
	 * Retrieves a [Float] stored against given [key] from persistence storage.
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [Float] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [Float] if value is there,
	 *   else `null`.
	 */
	suspend fun retrieveFloat(key: String, defaultValue: Float = 0F): Float?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [Float] denoting the Value we wish to persist against the given [key].
	 */
	suspend fun persistFloat(key: String, value: Float?)

	/**
	 * Retrieves a [Boolean] stored against given [key] from persistence storage.
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [Boolean] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [Boolean] if value is there,
	 *   else `null`.
	 */
	suspend fun retrieveBoolean(key: String, defaultValue: Boolean = false): Boolean?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [Boolean] denoting the Value we wish to persist against the given [key].
	 */
	suspend fun persistBoolean(key: String, value: Boolean?)

	/**
	 * Retrieves a [T] stored against given [key] from persistence storage.
	 *
	 * @param T Any Data Class
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param objectClass [Class] of [T] which helps in identifying the instance of [T].
	 *
	 * @return If the [key] found in the persistence storage, then a [T] if value is there,
	 *   else `null`.
	 */
	suspend fun <T> retrieveObject(key: String, objectClass: Class<T>): T?

	/**
	 * Persists a given [value] against the given [key].
	 *
	 * @param T Any Data Class
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [T] denoting the Value we wish to persist against the given [key].
	 * @param objectClass [Class] of [T] which helps in identifying the instance of [T].
	 */
	suspend fun <T> persistObject(key: String, value: T?, objectClass: Class<T>)

	/**
	 * Purges all of the contents of Persistence Storage.
	 */
	suspend fun purgeMemory()

}
