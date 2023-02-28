plugins {
	id("java-library")
	id("org.jetbrains.kotlin.jvm")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

	api(project(":common_persistence"))
	api(project(":common_data_transformer"))

	api(Dependencies.JetPack.DataStore.core)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
