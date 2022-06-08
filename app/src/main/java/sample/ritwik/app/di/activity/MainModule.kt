package sample.ritwik.app.di.activity

import com.droidboi.common.persistence.dataStore.DataStorePreference

import com.droidboi.common.utility.resources.ResourceUtils

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides

import sample.ritwik.app.component.network.RESTInterface

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

/**
 * [Module] of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @author Ritwik Jamuar
 */
@Module
class MainModule {

	/**
	 * Provides the Repository of [sample.ritwik.app.mvvm.viewModel.MainViewModel].
	 *
	 * @param restInterface Instance of [RESTInterface] fulfilled from
	 *   [sample.ritwik.app.di.application.module.RESTInterfaceModule].
	 * @param dataStorePreference Instance of [DataStorePreference] fulfilled from
	 *   [com.droidboi.common.persistence.di.PersistenceModule].
	 * @param moshi Instance of [Moshi] fulfilled from
	 *   [sample.ritwik.common.di.module.MoshiModule].
	 * @param resourceUtils Instance of [ResourceUtils] fulfilled from
	 *   [sample.ritwik.common.di.module.CommonModule].
	 * @return New Instance of [MainRepository].
	 */
	@Provides
	fun providesRepository(
		restInterface: RESTInterface,
		dataStorePreference: DataStorePreference,
		moshi: Moshi,
		resourceUtils: ResourceUtils,
	): MainRepository = MainRepository(restInterface, dataStorePreference, moshi, resourceUtils)

	/**
	 * Provides the Model of [sample.ritwik.app.mvvm.viewModel.MainViewModel].
	 *
	 * @return New Instance of [MainModel].
	 */
	@Provides
	fun providesModel(): MainModel = MainModel()

}
