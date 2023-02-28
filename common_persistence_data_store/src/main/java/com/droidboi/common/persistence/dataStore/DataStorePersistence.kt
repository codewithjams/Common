package com.droidboi.common.persistence.dataStore

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.core.Preferences

import com.droidboi.common.persistence.Persistence

import com.droidboi.common.dataTransformer.DataTransformer

import kotlinx.coroutines.flow.first

/**
 * [Persistence] implemented with [DataStore].
 *
 * @param dataStore Instance of [DataStore] from which all persistence operation happens.
 * @param transformer Instance of [DataTransformer] to help transforming
 *   JSON From/To Class instance.
 * @author Ritwik Jamuar
 */
open class DataStorePersistence (
	private val dataStore: DataStore<Preferences>,
	private val transformer: DataTransformer
) : Persistence {

	/*---------------------------------- Persistence Callbacks -----------------------------------*/

	override suspend fun retrieveString(key: String, defaultValue: String?): String? =
		retrieveValue(stringPreferencesKey(key), defaultValue)

	override suspend fun persistString(key: String, value: String?) {
		persistValue(stringPreferencesKey(key), value)
	}

	override suspend fun retrieveInteger(key: String, defaultValue: Int): Int? =
		retrieveValue(intPreferencesKey(key), defaultValue)

	override suspend fun persistInteger(key: String, value: Int?) {
		persistValue(intPreferencesKey(key), value)
	}

	override suspend fun retrieveLong(key: String, defaultValue: Long): Long? =
		retrieveValue(longPreferencesKey(key), defaultValue)

	override suspend fun persistLong(key: String, value: Long?) {
		persistValue(longPreferencesKey(key), value)
	}

	override suspend fun retrieveFloat(key: String, defaultValue: Float): Float? =
		retrieveValue(floatPreferencesKey(key), defaultValue)

	override suspend fun persistFloat(key: String, value: Float?) {
		persistValue(floatPreferencesKey(key), value)
	}

	override suspend fun retrieveBoolean(key: String, defaultValue: Boolean): Boolean? =
		retrieveValue(booleanPreferencesKey(key), defaultValue)

	override suspend fun persistBoolean(key: String, value: Boolean?) {
		persistValue(booleanPreferencesKey(key), value)
	}

	override suspend fun <T> retrieveObject(key: String, objectClass: Class<T>): T? =
		transformer.transformFromJSON(
			retrieveValue(stringPreferencesKey(key), null),
			objectClass
		)

	override suspend fun <T> persistObject(key: String, value: T?, objectClass: Class<T>) {
		persistValue(stringPreferencesKey(key), transformer.transformToJSON(value, objectClass))
	}

	override suspend fun purgeMemory() {
		clearDataStore()
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Retrieves a [T] stored against given [key] from the [dataStore].
	 *
	 * @param T Any Class
	 *
	 * @param key [String] denoting the Key onto which the value we desire is mapped to.
	 * @param defaultValue Default value as [T] that should be given
	 *   in the event the [key] does not exist.
	 *
	 * @return If the [key] found in the persistence storage, then a [T] if value is there,
	 *   else `null`.
	 */
	private suspend fun <T> retrieveValue(key: Preferences.Key<T>, defaultValue: T?): T? =
		dataStore.data.first()[key] ?: defaultValue

	/**
	 * Persists a given [value] against the given [key] in the [dataStore].
	 *
	 * @param T Any Class.
	 *
	 * @param key [String] denoting the Key at which we wish to persist the [value].
	 * @param value [T] denoting the Value we wish to persist against the given [key].
	 */
	private suspend fun <T> persistValue(key: Preferences.Key<T>, value: T?) {
		dataStore.edit { preferences ->
			if (value == null) {
				preferences.remove(key)
				return@edit
			}
			preferences[key] = value
		}
	}

	/**
	 * Clears the [dataStore] of it's persisted Key-Values.
	 */
	private suspend fun clearDataStore() {
		dataStore.edit { preferences ->
			preferences.clear()
		}
	}

}
