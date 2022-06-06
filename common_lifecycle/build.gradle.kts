plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(Dependencies.JetPack.Core.standardLibraryKTX)
	api(Dependencies.JetPack.Lifecycle.viewModel)
	api(Dependencies.JetPack.Lifecycle.liveData)
	api(Dependencies.JetPack.Lifecycle.runTime)
	api(Dependencies.Google.Dagger.standardLibrary)
	api(Dependencies.Google.Dagger.android)

	kapt(Dependencies.JetPack.Lifecycle.compiler)
	kapt(Dependencies.Google.Dagger.compiler)
	kapt(Dependencies.Google.Dagger.androidProcessor)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
