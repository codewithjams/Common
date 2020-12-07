package sample.ritwik.app.mvvm.repository

import com.squareup.moshi.Moshi

import sample.ritwik.app.component.network.RESTInterface

import sample.ritwik.app.data.network.ComponentsResponse

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.common.component.persistence.DataStorePreference

import sample.ritwik.common.mvvm.repository.BaseRepository

import sample.ritwik.common.utility.helper.ResourceUtils

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
    fun provideListOfLibraryComponents(): List<LibraryComponent> =

        // Use 'resourceUtils' to get the JSON File.
        with(resourceUtils.getAsset("LibraryComponents.json")) {

            // Initialize the ByteArray as buffer on which contents of JSON will be stored.
            val buffer = ByteArray(available())

            // Read the 'buffer' using  the given InputStream.
            read(buffer)

            // Close the InputStream after 'buffer' is read.
            close()

            // Instantiate a new List of LibraryComponent.
            ArrayList<LibraryComponent>().apply {

                // Using Moshi, Convert the JSON to an instance of ComponentResponse.
                moshi.adapter(ComponentsResponse::class.java).fromJson(String(buffer))
                    ?.let { response ->
                        // Add all the Library Components from response to this new List.
                        addAll(response.result)
                    }

            }

        }

}
