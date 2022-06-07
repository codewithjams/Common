plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(Dependencies.JetPack.Core.standardLibraryKTX)
	api(Dependencies.JetPack.AppCompat.standardLibrary)

	implementation(Dependencies.Kotlin.coRoutines)
	implementation(Dependencies.JetPack.Lifecycle.runTime)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
