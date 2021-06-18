package com.droidboi.common.persistence.dataStore

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.*

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

/**
 * Wrapper Class of [DataStore] of type [Preferences] that exposes commonly used methods
 * for Persistence Storage.
 *
 * @param dataStore Reference of [DataStore] of type [Preferences] to access the functionality of
 *   Persistence Storage.
 * @author Ritwik Jamuar
 */
class DataStorePreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Gets the [String] from the given [key].
     *
     * @param key [String] denoting the Key against which [String] value is stored.
     * @param defaultValue [String] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [String].
     */
    fun getString(key: String, defaultValue: String): Flow<String?> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [String] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [String] denoting the Value to be stored against the [key].
     */
    suspend fun putString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Integer] from the given [key].
     *
     * @param key [String] denoting the Key against which [Integer] value is stored.
     * @param defaultValue [Integer] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Integer].
     */
    fun getInteger(key: String, defaultValue: Int): Flow<Int?> = dataStore.data.map { preferences ->
        preferences[intPreferencesKey(key)] ?: defaultValue
    }

    /**
     * Puts the [Int] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Int] denoting the Value to be stored against the [key].
     */
    suspend fun putInteger(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Long] from the given [key].
     *
     * @param key [String] denoting the Key against which [Long] value is stored.
     * @param defaultValue [Long] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Long].
     */
    fun getLong(key: String, defaultValue: Long): Flow<Long?> = dataStore.data.map { preferences ->
        preferences[longPreferencesKey(key)] ?: defaultValue
    }

    /**
     * Puts the [Long] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Long] denoting the Value to be stored against the [key].
     */
    suspend fun putLong(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Float] from the given [key].
     *
     * @param key [String] denoting the Key against which [Float] value is stored.
     * @param defaultValue [Float] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Float].
     */
    fun getFloat(key: String, defaultValue: Float): Flow<Float?> =
        dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Float] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Float] denoting the Value to be stored against the [key].
     */
    suspend fun putFloat(key: String, value: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Double] from the given [key].
     *
     * @param key [String] denoting the Key against which [Double] value is stored.
     * @param defaultValue [Double] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Double].
     */
    fun getDouble(key: String, defaultValue: Double): Flow<Double?> =
        dataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Double] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Double] denoting the Value to be stored against the [key].
     */
    suspend fun putDouble(key: String, value: Double) {
        dataStore.edit { preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Boolean] from the given [key].
     *
     * @param key [String] denoting the Key against which [Boolean] value is stored.
     * @param defaultValue [Boolean] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Boolean].
     */
    fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean?> =
        dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Boolean] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Boolean] denoting the Value to be stored against the [key].
     */
    suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

}
