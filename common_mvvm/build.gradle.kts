plugins {
	id(Plugins.commonLibraryModulePlugin)
}

dependencies {

	api(project(":common_lifecycle"))

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
