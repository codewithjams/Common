package sample.ritwik.app.di.application.module

import android.content.Context

import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.preferencesDataStoreFile

import com.droidboi.common.dataTransformer.DataTransformer

import com.droidboi.common.persistence.dataStore.DataStorePersistence

import dagger.Module
import dagger.Provides

import sample.ritwik.app.di.application.scope.AppScope
import javax.inject.Named

@Module(
	includes = [
		DataTransformerModule::class
	]
)
class PersistenceModule {

	@Provides
	@AppScope
	fun providesDataStorePersistence(
		preferences: DataStore<Preferences>,
		transformer: DataTransformer
	): DataStorePersistence = DataStorePersistence(preferences, transformer)

	@Provides
	@AppScope
	fun providesDataStorePreference(
		context: Context,
		@Named(DATA_STORE_FILE_NAME) fileName: String
	): DataStore<Preferences> = PreferenceDataStoreFactory.create {
		context.preferencesDataStoreFile(fileName)
	}

}
