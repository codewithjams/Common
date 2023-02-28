package sample.ritwik.app.repository

import com.droidboi.common.dataTransformer.DataTransformer

import com.droidboi.common.persistence.Persistence

import com.droidboi.common.repository.Repository

import com.droidboi.common.rest.retrofit.RetrofitRESTCaller

import com.droidboi.common.utility.resources.ResourceUtils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import sample.ritwik.app.repository.rest.RESTInterface

import sample.ritwik.app.data.network.ComponentsResponse

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.repository.inMemory.MainInMemoryData

import java.io.IOException
import java.io.InputStream

import java.nio.charset.StandardCharsets

/**
 * [Repository] of this application.
 *
 * @author Ritwik Jamuar
 */
interface ApplicationRepository : Repository<MainInMemoryData>, RetrofitRESTCaller {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [RESTInterface] to perform REST API Calls.
	 */
	val restInterface: RESTInterface

	/**
	 * Reference of [Persistence] to persist or retrieve data from Persistence Storage.
	 */
	val persistence: Persistence

	/**
	 * Reference of [DataTransformer] to transform class instance to JSON & vice-versa.
	 */
	val dataTransformer: DataTransformer

	/**
	 * Reference of [ResourceUtils] to access Android Resources.
	 */
	val resourceUtils: ResourceUtils

	/*----------------------------------- Repository Callbacks -----------------------------------*/

	override val inMemoryData: MainInMemoryData
		get() = MainInMemoryData()

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Provides the [List] of [LibraryComponent] from the JSON in the Assets.
	 *
	 * @return [Flow] of [List] of [LibraryComponent].
	 */
	suspend fun provideListOfLibraryComponents() =
		flow {

			if (inMemoryData.libraryComponents.isNotEmpty()) {
				emit(inMemoryData.libraryComponents)
				return@flow
			}

			dataTransformer.transformFromJSON(
				extractJSONFromAssets("LibraryComponents.json"),
				ComponentsResponse::class.java
			)?.let { response ->
				inMemoryData.addLibraryComponents(response.result)
				emit(response.result)
			}

		}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Extracts a JSON File as a [String] stored under assets folder.
	 *
	 * @param fileName [String] as the name of the JSON File stored in the assets folder.
	 *
	 * @return If the JSON File is found and no trouble opening the file, then a [String]
	 *   as the JSON Body, else `null`.
	 */
	private fun extractJSONFromAssets(fileName: String): String? {

		// Halt the further execution and return null if the filename is empty or
		// doesn't end with extension .json.
		if (fileName.isEmpty() || !fileName.endsWith(".json"))
			return null

		// At this point, the File Name provided is not empty and ends with '.json' extension,
		// so we start reading the file and extract it to a String.

		val stream: InputStream = try {
			resourceUtils.getAsset(fileName) // Open the File from AssetManager.
		} catch (e: IOException) {
			// Unable to open file, no further extraction possible, hence halting with null.
			return null
		}

		// Determine the size of the contents from the InputStream.
		val size: Int = stream.available()

		// Create an array of Bytes which stores the content of files in the form of bytes.
		val buffer = ByteArray(size)

		// Read all the contents of InputStream to the buffer created above.
		stream.read(buffer)

		// Close the InputStream.
		stream.close()

		// Create a new String using the buffer that was read.
		return String(buffer, StandardCharsets.UTF_8)

	}

}
