plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(Dependencies.Google.Dagger.standardLibrary)
	api(Dependencies.Google.Dagger.android)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
