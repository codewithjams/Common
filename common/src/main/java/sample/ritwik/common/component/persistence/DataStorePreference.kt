package sample.ritwik.common.component.persistence

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Wrapper Class of [DataStore] of type [Preferences] that exposes commonly used methods
 * for Persistence Storage.
 *
 * @param dataStore Reference of [DataStore] of type [Preferences] to access the functionality of
 *   Persistence Storage.
 * @author Ritwik Jamuar
 */
class DataStorePreference(
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
            preferences[preferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [String] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [String] denoting the Value to be stored against the [key].
     */
    suspend fun putString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
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
        preferences[preferencesKey(key)] ?: defaultValue
    }

    /**
     * Puts the [Int] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Int] denoting the Value to be stored against the [key].
     */
    suspend fun putInteger(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
        }
    }

    /**
     * Gets the [Short] from the given [key].
     *
     * @param key [String] denoting the Key against which [Short] value is stored.
     * @param defaultValue [Short] denoting the Default Value to be returned if the [key] does
     *   not exist.
     * @return [Flow] of Nullable [Short].
     */
    fun getShort(key: String, defaultValue: Short): Flow<Short?> =
        dataStore.data.map { preferences ->
            preferences[preferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Short] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Short] denoting the Value to be stored against the [key].
     */
    suspend fun putShort(key: String, value: Short) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
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
        preferences[preferencesKey(key)] ?: defaultValue
    }

    /**
     * Puts the [Long] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Long] denoting the Value to be stored against the [key].
     */
    suspend fun putLong(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
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
            preferences[preferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Float] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Float] denoting the Value to be stored against the [key].
     */
    suspend fun putFloat(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
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
            preferences[preferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Double] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Double] denoting the Value to be stored against the [key].
     */
    suspend fun putDouble(key: String, value: Double) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
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
            preferences[preferencesKey(key)] ?: defaultValue
        }

    /**
     * Puts the [Boolean] for the given [key].
     *
     * @param key [String] denoting the Key against which [value] is to be stored.
     * @param value [Boolean] denoting the Value to be stored against the [key].
     */
    suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[preferencesKey(key)] = value
        }
    }

}
