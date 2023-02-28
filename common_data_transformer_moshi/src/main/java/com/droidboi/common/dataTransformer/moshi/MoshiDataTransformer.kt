package com.droidboi.common.dataTransformer.moshi

import com.droidboi.common.dataTransformer.DataTransformer

import com.squareup.moshi.Moshi

/**
 * [DataTransformer] implemented with [Moshi].
 *
 * @param moshi Instance of [Moshi] resposible for all transformations.
 * @author Ritwik Jamuar
 */
class MoshiDataTransformer(private val moshi: Moshi) : DataTransformer {

	/*-------------------------------- DataTransformer Callbacks ---------------------------------*/

	override suspend fun <T> transformFromJSON(json: String?, objectType: Class<T>): T? = try {
		if (json.isNullOrEmpty()) {
			null
		} else {
			moshi.adapter(objectType).fromJson(json)
		}
	} catch (e: Exception) {
		null
	}

	override suspend fun <T> transformToJSON(instance: T?, objectType: Class<T>): String? = try {
		if (instance == null) {
			null
		} else {
			moshi.adapter(objectType).toJson(instance)
		}
	} catch (e: Exception) {
		null
	}

}
