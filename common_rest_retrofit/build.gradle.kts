plugins {
	id("java-library")
	id("kotlin")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

	api(Dependencies.Square.Retrofit.standardLibrary)
	api(Dependencies.Square.Retrofit.mock)

	implementation(Dependencies.Kotlin.coRoutinesCore)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
