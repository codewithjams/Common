package sample.ritwik.app.data.ui

import com.squareup.moshi.Json

/**
 * Data Class to encapsulate a Library Component.
 *
 * @param title [String] denoting the Name of the Library Component.
 * @param description [String] denoting the Description of the Library Component.
 * @author Ritwik Jamuar
 */
data class LibraryComponent(
	@field: Json(name = "title") val title: String,
	@field: Json(name = "description") val description: String
)
