plugins {
    id(Plugins.commonApplicationModulePlugin)
    id(Plugins.kotlinAnnotationProcessor)
}

android {

    defaultConfig {

        applicationId = "sample.ritwik.app"

        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures {

        dataBinding = true

    }

}

dependencies {

    implementation(project(":common"))
    implementation(AndroidCommon.legacySupportV4)

    kapt(DependencyInjection.daggerAndroidProcessor)
    kapt(DependencyInjection.daggerCompiler)
    kapt(JetpackLifecycle.extension)

    testImplementation(UnitTesting.jUnit)

    androidTestImplementation(InstrumentationTesting.androidXJunit)
    androidTestImplementation(InstrumentationTesting.androidXEspresso)

}
