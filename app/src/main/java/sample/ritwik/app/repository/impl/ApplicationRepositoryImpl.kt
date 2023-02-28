package sample.ritwik.app.repository.impl

import com.droidboi.common.dataTransformer.DataTransformer

import com.droidboi.common.persistence.Persistence

import com.droidboi.common.persistence.dataStore.DataStorePersistence

import com.droidboi.common.utility.resources.ResourceUtils

import sample.ritwik.app.repository.rest.RESTInterface

import sample.ritwik.app.repository.ApplicationRepository

import javax.inject.Inject

/**
 * Implementation of [ApplicationRepository].
 *
 * @param dataStorePersistence Instance of [DataStorePersistence]
 *   for [ApplicationRepository.persistence].
 * @param restInterface        Instance of [RESTInterface]
 *   for [ApplicationRepository.restInterface].
 * @param dataTransformer      Instance of [DataTransformer]
 *   for [ApplicationRepository.dataTransformer].
 * @param resourceUtils        Instance of [ResourceUtils]
 *   for [ApplicationRepository.resourceUtils].
 *
 * @author Ritwik Jamuar
 */
class ApplicationRepositoryImpl @Inject constructor(
	private val dataStorePersistence: DataStorePersistence,
	override val restInterface: RESTInterface,
	override val dataTransformer: DataTransformer,
	override val resourceUtils: ResourceUtils
) : ApplicationRepository {

	override val persistence: Persistence
		get() = dataStorePersistence

}
