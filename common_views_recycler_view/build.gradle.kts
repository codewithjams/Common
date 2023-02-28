plugins {
	id(Plugins.commonLibraryModulePlugin)
}

android {
	namespace = "com.droidboi.common.views.recyclerView"
	buildFeatures {
		dataBinding = true
	}
}

dependencies {

	implementation(Dependencies.JetPack.RecyclerView.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
