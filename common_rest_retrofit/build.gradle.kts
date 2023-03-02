plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	api(Dependencies.Square.Retrofit.standardLibrary)

	implementation(Dependencies.Kotlin.coRoutinesCore)

	testImplementation(Dependencies.Google.Truth.standardLibrary)
	testImplementation(Dependencies.JUnit.standardLibrary)
	testImplementation(Dependencies.Square.Moshi.kotlin)
	testImplementation(Dependencies.Square.OkHttp3.mockWebServer)
	testImplementation(Dependencies.Square.Retrofit.moshiConverter)

}
