plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(project(":common_utility_network_callback"))
	api(project(":common_utility_permissions"))
	api(project(":common_utility_resources"))
	api(project(":common_utility_views"))

	implementation(Dependencies.Google.Dagger.standardLibrary)
	implementation(Dependencies.Google.Dagger.android)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
