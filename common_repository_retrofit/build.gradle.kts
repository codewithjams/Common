plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(project(":common_data_network"))
	api(project(":common_utility_resources"))
	api(project(":common_persistence"))

	api(Dependencies.Square.Retrofit.standardLibrary)
	api(Dependencies.Square.Retrofit.moshiConverter)

	implementation(Dependencies.Kotlin.coRoutines)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
