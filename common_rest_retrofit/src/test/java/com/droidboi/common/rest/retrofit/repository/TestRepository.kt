package com.droidboi.common.rest.retrofit.repository

import com.droidboi.common.rest.retrofit.RESTErrorStatus
import com.droidboi.common.rest.retrofit.ResultWrapper
import com.droidboi.common.rest.retrofit.RetrofitRESTCaller

import com.droidboi.common.rest.retrofit.data.rest.TestErrorResponse

import com.droidboi.common.rest.retrofit.data.ui.TestData

import com.droidboi.common.rest.retrofit.repository.inMemory.TestInMemoryData

import com.droidboi.common.rest.retrofit.rest.TestRESTInterface

import com.squareup.moshi.Moshi

import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.flow.Flow

import okhttp3.ResponseBody

import java.io.IOException

/**
 * Repository to simulate one REST API Call using [RetrofitRESTCaller].
 *
 * @param inMemoryData [TestInMemoryData] as the in-memory data holder of this Repository.
 * @param restInterface [TestRESTInterface] to perform REST API Calls.
 * @param moshi Instance of [Moshi] to help serialize any object to JSON and vice-versa.
 * @author Ritwik Jamuar
 */
class TestRepository(
	private val inMemoryData: TestInMemoryData,
	private val restInterface: TestRESTInterface,
	private val moshi: Moshi
) : RetrofitRESTCaller {

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Performs REST API Call to fetch something.
	 *
	 * @param dispatcher [CoroutineDispatcher] on which this [Flow] should execute on.
	 * @return [Flow] of [ResultWrapper] of [TestData].
	 */
	fun fetchSomething(dispatcher: CoroutineDispatcher): Flow<ResultWrapper<TestData>> =
		performRESTCall(
			{
				restInterface.getSomething()
			},
			{ response ->
				TestData(response.data)
			},
			{ responseBody ->
				responseBody.extractJSON()?.let { json ->
					moshi.adapter(TestErrorResponse::class.java).fromJson(json)
				}
			},
			{ errorResponse, httpStatusCode ->
				when(httpStatusCode) {
					401 -> RESTErrorStatus.SessionExpired
					else -> RESTErrorStatus.Recoverable(
						httpStatusCode,
						errorResponse.code,
						errorResponse.description
					)
				}
			},
			dispatcher
		)

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Extracts JSON String embedded in the [ResponseBody].
	 *
	 * @return If the extraction went well, then a [String] as the JSON, otherwise `null`.
	 */
	private fun ResponseBody.extractJSON(): String? {

		val stream = source().inputStream()

		val size = try {
			stream.available()
		} catch (e : IOException) {
			return null
		}

		val contents = ByteArray(size)

		try {
			stream.read(contents)
			stream.close()
		} catch (e : IOException) {
			return null
		}

		val result = String(contents)

		if (result.isEmpty())
			return null

		return String(contents)

	}

}
