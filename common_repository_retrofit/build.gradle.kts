plugins {
    id(Plugins.commonLibraryModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

dependencies {

    api(project(":common_data_network"))
    api(project(":common_utility_resources"))
    api(project(":common_persistence"))

    api(Network.retrofit)
    api(Network.retrofitMoshiConverter)

    implementation(Kotlin.coRoutines)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
