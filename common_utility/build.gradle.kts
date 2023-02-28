plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {
	namespace = "com.droidboi.common.utility"
}

dependencies {

	api(project(":common_utility_network_callback"))
	api(project(":common_utility_resources"))
	api(project(":common_utility_views"))

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
