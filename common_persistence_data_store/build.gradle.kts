plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	api(project(":common_persistence"))
	api(project(":common_data_transformer"))

	api(Dependencies.JetPack.DataStore.core)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
