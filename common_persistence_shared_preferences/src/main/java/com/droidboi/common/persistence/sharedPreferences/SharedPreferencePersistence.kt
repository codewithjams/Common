package com.droidboi.common.persistence.sharedPreferences

import android.content.SharedPreferences

import com.droidboi.common.persistence.Persistence

import com.droidboi.common.dataTransformer.DataTransformer

/**
 * [Persistence] implemented with [SharedPreferences].
 *
 * @param preferences Instance of [SharedPreferences] from which all the persistence operation
 *   happens.
 * @param transformer Instance of [DataTransformer] to help transforming
 *   JSON From/To Class instance.
 * @author Ritwik Jamuar
 */
class SharedPreferencePersistence constructor(
	private val preferences: SharedPreferences,
	private val transformer: DataTransformer
) : Persistence {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [SharedPreferences.Editor] which enables actual persisting of values into
	 * Persistence Storage.
	 */
	private val editor: SharedPreferences.Editor = preferences.edit()

	/*---------------------------------- Persistence Callbacks -----------------------------------*/

	override suspend fun retrieveString(key: String, defaultValue: String?): String? =
		preferences.getString(key, defaultValue)

	override suspend fun persistString(key: String, value: String?) {
		editor.putString(key, value).apply()
	}

	override suspend fun retrieveInteger(key: String, defaultValue: Int): Int =
		preferences.getInt(key, defaultValue)

	override suspend fun persistInteger(key: String, value: Int?) {
		if (value == null) {
			editor.putInt(key, 0)
			return
		}
		editor.putInt(key, value).apply()
	}

	override suspend fun retrieveLong(key: String, defaultValue: Long): Long =
		preferences.getLong(key, defaultValue)

	override suspend fun persistLong(key: String, value: Long?) {
		if (value == null) {
			editor.putLong(key, 0L)
			return
		}
		editor.putLong(key, value).apply()
	}

	override suspend fun retrieveFloat(key: String, defaultValue: Float): Float =
		preferences.getFloat(key, defaultValue)

	override suspend fun persistFloat(key: String, value: Float?) {
		if (value == null) {
			editor.putFloat(key, 0F)
			return
		}
		editor.putFloat(key, value).apply()
	}

	override suspend fun retrieveBoolean(key: String, defaultValue: Boolean): Boolean =
		preferences.getBoolean(key, defaultValue)

	override suspend fun persistBoolean(key: String, value: Boolean?) {
		if (value == null) {
			editor.putBoolean(key, false)
			return
		}
		editor.putBoolean(key, value).apply()
	}

	override suspend fun <T> retrieveObject(key: String, objectClass: Class<T>): T? =
		transformer.transformFromJSON(preferences.getString(key, null), objectClass)

	override suspend fun <T> persistObject(key: String, value: T?, objectClass: Class<T>) {
		editor.putString(key, transformer.transformToJSON(value, objectClass)).apply()
	}

	override suspend fun purgeMemory() {
		editor.clear().apply()
	}

}
