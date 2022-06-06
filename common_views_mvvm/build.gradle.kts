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

    api(project(":common_views_core"))
    api(project(":common_mvvm"))

    implementation(Dependencies.JetPack.AppCompat.standardLibrary)
    implementation(Dependencies.JetPack.Core.standardLibraryKTX)

    kapt(Dependencies.JetPack.Lifecycle.compiler)

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
