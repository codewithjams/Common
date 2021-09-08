plugins {
    id(Plugins.commonLibraryModulePlugin)
}

dependencies {

    implementation(UI.recyclerView)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
