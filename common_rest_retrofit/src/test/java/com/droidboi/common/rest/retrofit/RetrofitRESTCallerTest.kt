package com.droidboi.common.rest.retrofit

import com.droidboi.common.rest.retrofit.data.ui.TestData

import com.droidboi.common.rest.retrofit.exceptions.CustomEOFException
import com.droidboi.common.rest.retrofit.exceptions.NoErrorBodyException

import com.droidboi.common.rest.retrofit.repository.TestRepository

import com.droidboi.common.rest.retrofit.repository.inMemory.TestInMemoryData

import com.droidboi.common.rest.retrofit.rest.TestRESTInterface

import com.google.common.truth.Truth

import com.squareup.moshi.Moshi

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

import okhttp3.OkHttpClient

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy

import org.junit.After
import org.junit.Before
import org.junit.Test

import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory

import java.io.IOException
import java.io.InputStream

import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

import java.util.concurrent.TimeUnit.SECONDS

/**
 * Unit Test class to test out [RetrofitRESTCaller].
 *
 * @author Ritwik Jamuar
 */
class RetrofitRESTCallerTest {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [MockWebServer] to mock a Web Server.
	 */
	private val mockWebServer: MockWebServer by lazy {
		MockWebServer()
	}

	/**
	 * Reference of [Moshi] to perform (de)serialization of object to JSON and vice-versa.
	 */
	private val moshi: Moshi by lazy {
		Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
	}

	/**
	 * Reference of [TestRepository] which implements [RetrofitRESTCaller]
	 * so we can perform assertions around it.
	 */
	private val repository: TestRepository by lazy {
		TestRepository(
			TestInMemoryData(),
			Retrofit.Builder()
				.baseUrl(mockWebServer.url("/"))
				.client(
					OkHttpClient.Builder()
						.connectTimeout(1, SECONDS)
						.readTimeout(1, SECONDS)
						.writeTimeout(1, SECONDS)
						.build()
				).addConverterFactory(
					MoshiConverterFactory.create(moshi).asLenient()
				).build()
				.create(TestRESTInterface::class.java),
			moshi
		)
	}

	/*-------------------------------------- Test Callbacks --------------------------------------*/

	@Before
	fun setUp() {
		mockWebServer.start()
	}

	@After
	fun cleanUp() {
		mockWebServer.shutdown()
	}

	/*---------------------------------------- Test Cases ----------------------------------------*/

	@Test
	fun `test case when Response is successful`() {

		val json = getJSONFromResources("test_response_success.json")
			?: return

		// Setting Response Code as 200 and Response JSON to simulate a successful REST API Call.
		mockWebServer.enqueue(
			MockResponse().setResponseCode(HTTP_OK).setBody(json)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(ResultWrapper.Success(TestData("Something")))
			}
		}

	}

	@Test
	fun `test case when Response is successful yet no Body received from Server`() {

		// Setting Response Code as 200 while deliberately left out the Response Body
		// to simulate Success without Response Body
		mockWebServer.enqueue(
			MockResponse().setResponseCode(HTTP_OK)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(
					ResultWrapper.Error.NetworkErrorIO(CustomEOFException("End of input"))
				)
			}
		}

	}

	@Test
	fun `test case when Response is unsuccessful and receives error from the Server`() {

		val json = getJSONFromResources("test_response_error.json") ?: return

		// Setting Response Code as 400 and Error Response JSON to simulate Error caused in Server.
		mockWebServer.enqueue(
			MockResponse().setResponseCode(HTTP_BAD_REQUEST).setBody(json)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(
					ResultWrapper.Error.Recoverable(
						HTTP_BAD_REQUEST,
						"400.01",
						"Some Description"
					)
				)
			}
		}

	}

	@Test
	fun `test case when Server throws no Error Body`() {

		// Setting Response Code as 400 while deliberately left out the Response Body
		// to simulate Error Response without Error Body.
		mockWebServer.enqueue(
			MockResponse().setResponseCode(HTTP_BAD_REQUEST)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(ResultWrapper.Error.Unknown(NoErrorBodyException()))
			}
		}

	}

	@Test
	fun `test case when current Session is expired`() {

		val json = getJSONFromResources("test_response_error_session_expired.json")
			?: return

		// Setting Response Code as 401 (as this code is configured in repository
		// for Session Expiry) and Error Response Body to simulate Session Expiry.
		mockWebServer.enqueue(
			MockResponse().setResponseCode(HTTP_UNAUTHORIZED).setBody(json)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(ResultWrapper.Error.SessionExpired)
			}
		}

	}

	@Test
	fun `test case when there is trouble connecting to the network`() {

		val json = getJSONFromResources("test_response_success.json") ?: return

		// Setting Response Code as 200 and Response Body while also configuring the server
		// to disconnect at start in order to simulate trouble in connecting to the Server.
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(HTTP_OK)
				.setBody(json)
				.setSocketPolicy(SocketPolicy.DISCONNECT_AT_START)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(ResultWrapper.Error.NetworkErrorConnection)
			}
		}

	}

	@Test
	fun `test case when Response is delayed by the Server`() {

		val json = getJSONFromResources("test_response_success.json") ?: return

		// Setting Response Code as 200 and Response Body while also configuring the server
		// to time-out (i.e. take 5 seconds to provide the response) in order to simulate
		// time-out from the Server.
		mockWebServer.enqueue(
			MockResponse()
				.setResponseCode(HTTP_OK)
				.setBody(json)
				.setBodyDelay(5, SECONDS)
		)

		runBlocking {
			fetchResultOfSomething().apply {
				Truth.assertThat(this).hasSize(2)
				Truth.assertThat(this).contains(ResultWrapper.Loading)
				Truth.assertThat(this).contains(ResultWrapper.Error.NetworkErrorTimeOut)
			}
		}

	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Collects the content of [TestRepository.fetchSomething] and wraps it in the [List].
	 *
	 * @return [List] of [ResultWrapper] of [TestData].
	 */
	private suspend fun fetchResultOfSomething(): List<ResultWrapper<TestData>> =
		mutableListOf<ResultWrapper<TestData>>().apply {
			repository.fetchSomething(Dispatchers.IO).collect { result: ResultWrapper<TestData> ->
					add(result)
			}
		}

	/**
	 * Gets the content of JSON stored under `resources` directory.
	 *
	 * @param fileName [String] denoting the name of JSON File.
	 * @return If fetching is successful, then a [String] with JSON content, otherwise `null`.
	 */
	private fun getJSONFromResources(fileName: String): String? {

		if (fileName.isEmpty())
			return null

		if (!fileName.endsWith(".json"))
			return null

		val stream: InputStream = try {
			val stream = javaClass.classLoader.getResourceAsStream(fileName) ?: return null
			stream
		} catch (e: IOException) {
			return null
		}

		val size: Int = try {
			stream.available()
		} catch (e: IOException) {
			return null
		}

		val contents = ByteArray(size)

		try {
			stream.read(contents)
			stream.close()
		} catch (e: IOException) {
			return null
		}

		return String(contents)

	}

}
