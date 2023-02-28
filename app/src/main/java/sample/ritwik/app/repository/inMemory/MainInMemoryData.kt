package sample.ritwik.app.repository.inMemory

import com.droidboi.common.repository.inMemory.InMemoryData

import sample.ritwik.app.data.ui.LibraryComponent

data class MainInMemoryData(
	val libraryComponents: MutableList<LibraryComponent> = mutableListOf()
): InMemoryData {

	fun addLibraryComponents(components: List<LibraryComponent>) {
		libraryComponents.clear()
		libraryComponents.addAll(components)
	}

}
