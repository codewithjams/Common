plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	api(Dependencies.Square.Retrofit.standardLibrary)
	api(Dependencies.Square.Retrofit.mock)

	implementation(Dependencies.Kotlin.coRoutinesCore)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
