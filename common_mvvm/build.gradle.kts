plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    api(project(":common_data_ui"))

    api(Dependencies.JetPack.Lifecycle.viewModel)
    api(Dependencies.JetPack.Lifecycle.liveData)
    api(Dependencies.JetPack.Lifecycle.runTime)

    implementation(Dependencies.Kotlin.standardLibrary)
    implementation(Dependencies.Google.Dagger.standardLibrary)
    implementation(Dependencies.Google.Dagger.android)

    kapt(Dependencies.Google.Dagger.androidProcessor)
    kapt(Dependencies.Google.Dagger.compiler)
    kapt(Dependencies.JetPack.Lifecycle.compiler)

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
