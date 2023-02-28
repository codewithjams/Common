plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {
	namespace = "com.droidboi.common.utility.networkCallback"
}

dependencies {

	api(Dependencies.Kotlin.coRoutines)
	api(Dependencies.JetPack.Core.standardLibraryKTX)
	api(Dependencies.JetPack.AppCompat.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
