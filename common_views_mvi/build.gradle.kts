plugins {
	id(Plugins.commonLibraryModulePlugin)
}

android {
	namespace = "com.droidboi.common.views.mvi"
}

dependencies {

	api(project(":common_mvi"))

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)
	implementation(Dependencies.JetPack.Lifecycle.runTime)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
