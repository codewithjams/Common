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

    api(Kotlin.coRoutines)
    api(AndroidCommon.appCompat)
    api(AndroidCommon.annotations)
    api(AndroidCommon.legacySupportV4)
    api(JetpackNavigation.ui)
    api(JetpackNavigation.fragment)
    api(DependencyInjection.daggerCore)
    api(DependencyInjection.daggerAndroid)
    api(Network.retrofit)
    api(Network.retrofitMoshiConverter)
    api(Network.retrofitMock)
    api(Network.loggingInterceptor)
    api(Network.picasso)
    api(Network.picassoDownloader)
    api(UI.cardView)
    api(UI.materialDesign)
    api(UI.constraintLayout)
    api(UI.shimmerLayout)
    api(UI.shimmerLayoutSuperCharge)
    api(Persistence.dataStorePreference)
    api(Utility.gson)

    debugApi(Utility.leakCanary)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)
    kapt(JetpackLifecycle.extension)
    kapt(UI.dataBindingCompiler)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
