plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    api(project(":common_data_ui"))

    api(JetpackLifecycle.viewModel)
    api(JetpackLifecycle.liveDataCore)
    api(JetpackLifecycle.runtime)
    api(JetpackLifecycle.commonJava8)
    api(JetpackLifecycle.common)

    implementation(DependencyInjection.daggerCore)
    implementation(DependencyInjection.daggerAndroid)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)
    kapt(JetpackLifecycle.extension)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
