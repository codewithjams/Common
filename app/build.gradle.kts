plugins {
	id(Plugins.commonApplicationModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {

	namespace = "sample.ritwik.app"

	defaultConfig {
		versionCode = 1
		versionName = "1.0"
	}

	buildFeatures {
		dataBinding = true
	}

}

dependencies {

	api(project(":common_mvvm"))
	api(project(":common_rest_retrofit"))
	api(project(":common_repository"))
	api(project(":common_persistence_data_store"))
	api(project(":common_data_transformer_moshi"))
	api(project(":common_utility"))
	api(project(":common_views_recycler_view"))
	api(project(":common_views_mvvm"))
	api(project(":common_views_mvi"))
	api(project(":common_viewModel_dagger"))

	implementation(Dependencies.JetPack.AppCompat.standardLibrary)
	implementation(Dependencies.JetPack.Annotation.standardLibrary)
	implementation(Dependencies.JetPack.Navigation.ui)
	implementation(Dependencies.JetPack.Navigation.fragment)
	implementation(Dependencies.Google.Dagger.standardLibrary)
	implementation(Dependencies.Google.Dagger.android)
	implementation(Dependencies.JetPack.Legacy.v4Support)

	// Adding this because the module `:common_persistence_data_store` uses Core Library
	// of DataStore and does not contain any Android specific methods.
	implementation(Dependencies.JetPack.DataStore.standardLibrary)

	implementation(Dependencies.Square.Retrofit.moshiConverter)
	implementation(Dependencies.Square.OkHttp3.loggingInterceptor)
	implementation(Dependencies.Square.Picasso.standardLibrary)
	implementation(Dependencies.JakeWharton.PicassoDownloader.standardLibrary)
	implementation(Dependencies.Facebook.Shimmer.standardLibrary)
	implementation(Dependencies.SuperCharge.ShimmerLayout.standardLibrary)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)
	kapt(Dependencies.JetPack.Lifecycle.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
