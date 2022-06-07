plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
