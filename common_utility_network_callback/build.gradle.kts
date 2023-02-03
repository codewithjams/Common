plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

	api(Dependencies.Kotlin.coRoutines)
	api(Dependencies.JetPack.Core.standardLibraryKTX)
	api(Dependencies.JetPack.AppCompat.standardLibrary)

	implementation(Dependencies.Google.Dagger.standardLibrary)
	implementation(Dependencies.Google.Dagger.android)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
