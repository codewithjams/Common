plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    implementation(Kotlin.coRoutines)
    implementation(DependencyInjection.daggerCore)
    implementation(DependencyInjection.daggerAndroid)

    api(Persistence.dataStorePreference)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
