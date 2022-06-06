plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    implementation(Dependencies.Kotlin.coRoutines)
    implementation(Dependencies.Google.Dagger.standardLibrary)
    implementation(Dependencies.Google.Dagger.android)

    api(Dependencies.JetPack.DataStore.standardLibrary)

    kapt(Dependencies.Google.Dagger.androidProcessor)
    kapt(Dependencies.Google.Dagger.compiler)

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
