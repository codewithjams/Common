package sample.ritwik.app.di.application.module

import com.droidboi.common.dataTransformer.DataTransformer

import com.droidboi.common.persistence.dataStore.DataStorePersistence

import com.droidboi.common.utility.resources.ResourceUtils

import dagger.Module
import dagger.Provides

import sample.ritwik.app.repository.rest.RESTInterface

import sample.ritwik.app.di.application.scope.AppScope

import sample.ritwik.app.repository.ApplicationRepository

import sample.ritwik.app.repository.impl.ApplicationRepositoryImpl

@Module(
	includes = [
		RESTModule::class,
		PersistenceModule::class
	]
)
class RepositoryModule {

	/**
	 * Provides the [ApplicationRepository].
	 *
	 * @param dataStorePersistence Instance of [DataStorePersistence].
	 * @param restInterface Instance of [RESTInterface].
	 * @param dataTransformer Instance of [DataTransformer].
	 * @param resourceUtils Instance of [ResourceUtils].
	 *
	 * @return New Instance of [ApplicationRepository].
	 */
	@Provides
	@AppScope
	fun providesMainRepository(
		restInterface: RESTInterface,
		dataStorePersistence: DataStorePersistence,
		dataTransformer: DataTransformer,
		resourceUtils: ResourceUtils
	): ApplicationRepository =
		ApplicationRepositoryImpl(dataStorePersistence, restInterface, dataTransformer, resourceUtils)

}
