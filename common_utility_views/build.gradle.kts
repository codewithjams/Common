plugins {
	id(Plugins.androidLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {
	namespace = "com.droidboi.common.utility.views"
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
