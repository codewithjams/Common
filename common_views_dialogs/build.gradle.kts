plugins {
	id(Plugins.androidLibraryModulePlugin)
}

android {
	namespace = "com.droidboi.common.views.dialogs"
	buildFeatures {
		dataBinding = true
	}
}

dependencies {

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)
	implementation(Dependencies.Google.MaterialDesign.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
