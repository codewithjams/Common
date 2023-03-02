package com.droidboi.common.rest.retrofit.rest

import com.droidboi.common.rest.retrofit.data.rest.TestResponse

import retrofit2.Response

import retrofit2.http.GET

/**
 * Retrofit's REST Interface that consists of End-Points of the Server.
 *
 * @author Ritwik Jamuar
 */
interface TestRESTInterface {

	/**
	 * Get Some Response.
	 *
	 * @return Instance of [Response] of [TestResponse] as the Reponse Body.
	 */
	@GET("/something")
	suspend fun getSomething(): Response<TestResponse>

}
