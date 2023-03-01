plugins {
	id(Plugins.javaLibraryModulePlugin)
}

dependencies {

	api(project(":common_data_transformer"))

	api(Dependencies.Square.Moshi.kotlin)

	testImplementation(Dependencies.JUnit.standardLibrary)

}
