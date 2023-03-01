plugins {
	id(Plugins.androidLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {
	namespace = "com.droidboi.common.views.mvvm"
}

dependencies {

	api(project(":common_mvvm"))

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)
	implementation(Dependencies.JetPack.Lifecycle.runTime)

	kapt(Dependencies.JetPack.Lifecycle.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
