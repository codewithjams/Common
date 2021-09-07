plugins {
    id(Plugins.commonLibraryModulePlugin)
}

dependencies {

    implementation(AndroidCommon.appCompat)
    implementation(UI.materialDesign)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
