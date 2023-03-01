plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	implementation(Dependencies.Kotlin.coRoutinesCore)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
