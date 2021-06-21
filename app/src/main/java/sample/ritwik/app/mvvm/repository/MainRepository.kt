package sample.ritwik.app.mvvm.repository

import com.droidboi.common.persistence.dataStore.DataStorePreference

import com.droidboi.common.repository.BaseRepository

import com.droidboi.common.utility.resources.helper.ResourceUtils

import com.squareup.moshi.Moshi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

import sample.ritwik.app.component.network.RESTInterface

import sample.ritwik.app.data.network.ComponentsResponse

import sample.ritwik.app.data.ui.LibraryComponent

import javax.inject.Inject

/**
 * Repository of [sample.ritwik.app.mvvm.viewModel.MainViewModel].
 *
 * @param restInterface Instance of [RESTInterface] to perform REST API Calls.
 * @param dataStorePreference Instance of [DataStorePreference] to perform storing/retrieving data
 *   from Persistent Storage.
 * @param moshi Instance of [Moshi] to serialize/de-serialize any Classes.
 * @param resourceUtils Instance of [ResourceUtils] to fetch the resources existing
 *   within this Application.
 * @author Ritwik Jamuar
 */
class MainRepository @Inject constructor(
    private val restInterface: RESTInterface,
    override val dataStorePreference: DataStorePreference,
    override val moshi: Moshi,
    override val resourceUtils: ResourceUtils
) : BaseRepository() {

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Provides the [List] of [LibraryComponent] from the JSON in the Assets.
     *
     * @return [List] of [LibraryComponent].
     */
    fun provideListOfLibraryComponents(): Flow<List<LibraryComponent>> = extractClassInstanceFromAssets(
        ComponentsResponse::class.java,
        "LibraryComponents.json"
    ).map { response ->
        response?.result ?: ArrayList()
    }.flowOn(ioDispatcher)

}
