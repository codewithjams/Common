import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

gradlePlugin {

    plugins {

        register("library-module-plugin") {
            id = "library-module-plugin"
            implementationClass = "LibraryModulePlugin"
        }

        register("application-module-plugin") {
            id = "application-module-plugin"
            implementationClass = "ApplicationModulePlugin"
        }

    }

}

repositories {
    google()
    mavenCentral()
}

dependencies {

    compileOnly(gradleApi())

    implementation("com.android.tools.build:gradle:7.0.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
    implementation(kotlin("android-extensions"))

}
