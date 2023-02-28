plugins {
	id(Plugins.commonLibraryModulePlugin)
}

dependencies {

	api(project(":common_mvi"))

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)
	implementation(Dependencies.JetPack.Lifecycle.runTime)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
