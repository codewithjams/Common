plugins {
	id(Plugins.commonLibraryModulePlugin)
	id(Plugins.kotlinAnnotationProcessor)
}

android {
	buildFeatures {
		dataBinding = true
	}
}

dependencies {

	api(project(":common_mvvm"))
	api(project(":common_repository_retrofit"))
	api(project(":common_utility"))
	api(project(":common_views_recycler_view"))
	api(project(":common_views_mvvm"))

	api(Dependencies.Kotlin.coRoutines)
	api(Dependencies.JetPack.AppCompat.standardLibrary)
	api(Dependencies.JetPack.Annotation.standardLibrary)
	api(Dependencies.JetPack.Legacy.v4Support)
	api(Dependencies.JetPack.Navigation.ui)
	api(Dependencies.JetPack.Navigation.fragment)
	api(Dependencies.Google.Dagger.standardLibrary)
	api(Dependencies.Google.Dagger.android)
	api(Dependencies.Square.Retrofit.standardLibrary)
	api(Dependencies.Square.Retrofit.moshiConverter)
	api(Dependencies.Square.Retrofit.mock)
	api(Dependencies.Square.OkHttp3.loggingInterceptor)
	api(Dependencies.Square.Picasso.standardLibrary)
	api(Dependencies.JakeWharton.PicassoDownloader.standardLibrary)
	api(Dependencies.JetPack.CardView.standardLibrary)
	api(Dependencies.Google.MaterialDesign.standardLibrary)
	api(Dependencies.JetPack.ConstraintLayout.standardLibrary)
	api(Dependencies.Facebook.Shimmer.standardLibrary)
	api(Dependencies.SuperCharge.ShimmerLayout.standardLibrary)
	api(Dependencies.JetPack.DataStore.standardLibrary)
	api(Dependencies.Google.Gson.standardLibrary)

	debugApi(Dependencies.Square.LeakCanary.standardLibrary)

	kapt(Dependencies.Google.Dagger.androidProcessor)
	kapt(Dependencies.Google.Dagger.compiler)
	kapt(Dependencies.JetPack.Lifecycle.compiler)

	testImplementation(Dependencies.JUnit.standardLibrary)

	androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
	androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
