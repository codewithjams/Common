package com.droidboi.common.persistence.di

import android.content.Context

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.preferencesDataStoreFile

import com.droidboi.common.persistence.dataStore.DataStorePreference

import com.droidboi.common.persistence.constant.DATA_STORE_FILE_NAME

import dagger.Module
import dagger.Provides

import javax.inject.Named

/**
 * [Module] for providing [DataStorePreference].
 *
 *
 * [DataStorePreference] depends on [DataStore] of type [Preferences]. So we fetch that first.
 * But to fulfill [DataStore], we need to supply the File Name of [DataStore],
 * which in case differs for every Application.
 *
 *
 * So, at outside [Module], we need a [Provides] + [Named] annotated method
 * in order to supply the File Name of [DataStore].
 *
 * @author Ritwik Jamuar
 */
@Module
class PersistenceModule {

	/**
	 * Provides the instance of [DataStorePreference].
	 *
	 * @param dataStore Instance of [DataStore] from [providesDataStore].
	 * @return New Instance of [DataStorePreference].
	 */
	@Provides
	fun providesDataStorePreference(dataStore: DataStore<Preferences>): DataStorePreference =
		DataStorePreference(dataStore)

	/**
	 * Provides the instance of [DataStore].
	 *
	 * @param context Instance of Application's [Context].
	 * @param fileName [String] denoting the File Name of the [DataStore].
	 */
	@Provides
	fun providesDataStore(
		context: Context,
		@Named(DATA_STORE_FILE_NAME) fileName: String
	): DataStore<Preferences> = PreferenceDataStoreFactory.create {
		context.preferencesDataStoreFile(fileName)
	}

}
