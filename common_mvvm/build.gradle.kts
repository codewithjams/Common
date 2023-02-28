plugins {
	id("java-library")
	id("kotlin")
}

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

dependencies {

	implementation(Dependencies.Kotlin.coRoutinesCore)
	implementation(Dependencies.JetPack.Annotation.standardLibrary)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
