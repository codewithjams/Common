plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    api(project(":common_utility_network_callback"))
    api(project(":common_utility_permissions"))
    api(project(":common_utility_resources"))
    api(project(":common_utility_views"))

    implementation(DependencyInjection.daggerCore)
    implementation(DependencyInjection.daggerAndroid)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
