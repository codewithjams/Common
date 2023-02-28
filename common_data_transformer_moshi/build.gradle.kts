plugins {
	id("java-library")
	id("kotlin")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

	api(project(":common_data_transformer"))

	api(Dependencies.Square.Moshi.kotlin)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
