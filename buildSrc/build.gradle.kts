import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
	`kotlin-dsl`
}

gradlePlugin {

	plugins {

		register("android-library-module-plugin") {
			id = "android-library-module-plugin"
			implementationClass = "LibraryModulePlugin"
		}

		register("android-application-module-plugin") {
			id = "android-application-module-plugin"
			implementationClass = "ApplicationModulePlugin"
		}

		register("java-library-module-plugin") {
			id = "java-library-module-plugin"
			implementationClass = "JavaLibraryModulePlugin"
		}

	}

}

repositories {
	google()
	mavenCentral()
}

dependencies {

	compileOnly(gradleApi())

	implementation("com.android.tools.build:gradle:7.4.1")
	implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
	implementation(kotlin("android-extensions"))

}
