plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    api(project(":common_lifecycle"))

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
