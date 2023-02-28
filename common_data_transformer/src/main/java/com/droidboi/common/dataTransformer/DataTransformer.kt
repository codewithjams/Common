package com.droidboi.common.dataTransformer

/**
 * Transforms a given object to a JSON String and vice-versa.
 *
 * @author Ritwik Jamuar
 */
interface DataTransformer {

	/**
	 * Transforms a given [json] to an instance of [T].
	 *
	 * @param T Any Data Class
	 *
	 * @param json [String] as the JSON.
	 * @param objectType [Class] of [T] which helps in identifying the instance of [T].
	 *
	 * @return If the transformation is successful, then an instance of [T], otherwise `null`.
	 */
	suspend fun <T> transformFromJSON(json: String?, objectType: Class<T>): T?

	/**
	 * Transforms a given [instance] to a JSON String.
	 *
	 * @param T Any Data Class
	 *
	 * @param instance Instance of [T] we wish to convert.
	 * @param objectType [Class] of [T] which helps in identifying the instance of [T].
	 *
	 * @return If the transformation is successful, then a [String] as JSON of [instance],
	 *   otherwise `null`.
	 */
	suspend fun <T> transformToJSON(instance: T?, objectType: Class<T>): String?

}
