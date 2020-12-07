package sample.ritwik.app.di.activity

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides

import sample.ritwik.app.component.network.RESTInterface

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

import sample.ritwik.common.component.persistence.DataStorePreference

import sample.ritwik.common.utility.helper.ResourceUtils

/**
 * [Module] of [sample.ritwik.app.ui.activity.MainActivity].
 *
 *
 * The purpose of creating a [Module] for [sample.ritwik.app.ui.activity.MainActivity] is to provide
 * it's dependants, which in our case is these two things:
 * 1. [sample.ritwik.common.mvvm.viewModel.VMFactory].
 * 2. [sample.ritwik.common.utility.helper.NetworkUtils].
 *
 *
 * Now out of these two, [sample.ritwik.common.utility.helper.NetworkUtils] is provided from
 * [sample.ritwik.common.di.module.CommonModule].
 *
 *
 * But, [sample.ritwik.common.mvvm.viewModel.VMFactory] is unique for every [android.app.Activity].
 *
 *
 * From the current architecture, every sub-class of
 * [sample.ritwik.common.mvvm.viewModel.BaseViewModel] needs to have following:
 * 1. Instance of sub-class that extends [sample.ritwik.common.mvvm.repository.BaseRepository].
 * 2. Instance of sub-class that extends [sample.ritwik.common.mvvm.model.BaseModel].
 *
 *
 * This is why we create [Module] annotated Module Class for every [android.app.Activity].
 * The purpose of this Class is to facilitate the providing of above dependencies of
 * [sample.ritwik.common.mvvm.viewModel.BaseViewModel].
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
     *   [sample.ritwik.common.di.module.PersistenceModule].
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
