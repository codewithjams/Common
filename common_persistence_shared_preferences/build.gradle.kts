plugins {
	id(Plugins.commonLibraryModulePlugin)
}

dependencies {

	api(project(":common_persistence"))
	api(project(":common_data_transformer"))

	api(Dependencies.JetPack.DataStore.core)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
