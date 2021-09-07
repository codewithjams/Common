plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    implementation(AndroidCommon.appCompat)
    implementation(DependencyInjection.daggerCore)
    implementation(DependencyInjection.daggerAndroid)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
