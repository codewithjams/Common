plugins {
	id(Plugins.commonApplicationModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {

	defaultConfig {
		applicationId = "sample.ritwik.app"

		versionCode = 1
		versionName = "1.0"
	}

	buildFeatures {
		dataBinding = true
	}

}

dependencies {

	implementation(project(":common"))
	implementation(Dependencies.JetPack.Legacy.v4Support)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)
	kapt(Dependencies.JetPack.Lifecycle.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
