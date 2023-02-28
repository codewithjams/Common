plugins {
	id(Plugins.commonLibraryModulePlugin)
}

android {
	namespace = "com.droidboi.common.views.core"
	buildFeatures {
		dataBinding = true
	}
}

dependencies {

	implementation(Dependencies.JetPack.Core.standardLibraryKTX)
	implementation(Dependencies.JetPack.AppCompat.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
