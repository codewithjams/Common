package com.droidboi.common.rest.retrofit.data.rest

import com.droidboi.common.rest.retrofit.RESTError

import com.squareup.moshi.Json

/**
 * Some Response encapsulating the data.
 *
 * @param data [String] denoting some data.
 * @author Ritwik Jamuar
 */
data class TestResponse(
	@field: Json(name = "data") val data: String
)

/**
 * Some Error Response.
 *
 * @param code [String] denoting the Error Code thrown by Server.
 * @param description [String] denoting the Error Description.
 * @author Ritwik Jamuar
 */
data class TestErrorResponse(
	@field: Json(name = "code") val code: String,
	@field: Json(name = "description") val description: String
): RESTError
