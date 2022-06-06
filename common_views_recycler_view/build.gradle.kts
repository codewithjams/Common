plugins {
    id(Plugins.commonLibraryModulePlugin)
}

dependencies {

    implementation(Dependencies.JetPack.RecyclerView.standardLibrary)

    testImplementation(Dependencies.JUnit.standardLibrary)

    androidTestImplementation(Dependencies.JetPack.Test.jUnitExtensionKTX)
    androidTestImplementation(Dependencies.JetPack.Test.espresso)

}
