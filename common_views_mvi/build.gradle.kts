plugins {
	id(Plugins.commonLibraryModulePlugin)
}

android {
	buildFeatures {
		dataBinding = true
	}
}

dependencies {

	api(project(":common_mvi"))
	api(project(":common_views_core"))

	implementation(Dependencies.JetPack.Core.standardLibraryKTX)
	implementation(Dependencies.JetPack.AppCompat.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
