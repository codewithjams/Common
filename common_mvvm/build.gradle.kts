plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	implementation(Dependencies.Kotlin.coRoutinesCore)
	implementation(Dependencies.JetPack.Annotation.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
