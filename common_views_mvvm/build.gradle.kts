plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    implementation(project(":common_mvvm"))
    implementation(project(":common_utility"))

    implementation(AndroidCommon.appCompat)
    implementation(AndroidCommon.coreKTX)

    kapt(JetpackLifecycle.extension)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
