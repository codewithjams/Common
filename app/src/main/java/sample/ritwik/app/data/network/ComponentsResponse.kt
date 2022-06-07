package sample.ritwik.app.data.network

import com.squareup.moshi.Json

import sample.ritwik.app.data.ui.LibraryComponent

/**
 * Data Class to represent the response of REST API to fetch all the Library Components.
 *
 * @param status [String] denoting the Status of REST API.
 * @param result [List] of [LibraryComponent] denoting the Components.
 * @author Ritwik Jamuar
 */
data class ComponentsResponse(
	@field: Json(name = "status") val status: String,
	@field: Json(name = "result") val result: List<LibraryComponent>
)
