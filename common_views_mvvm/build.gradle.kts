plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    implementation(project(":common_mvvm"))
    implementation(project(":common_utility"))

    implementation(Dependencies.JetPack.AppCompat.standardLibrary)
    implementation(Dependencies.JetPack.Core.standardLibraryKTX)

    kapt(Dependencies.JetPack.Lifecycle.compiler)

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
