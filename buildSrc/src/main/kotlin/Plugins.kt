/**
 * Encapsulates different Gradle Plugins used.
 *
 * @author Ritwik Jamuar
 */
object Plugins {

	/**
	 * Gradle Plugin for indicating a Gradle Project as an Android Application.
	 */
	val application by lazy { "com.android.application" }

	/**
	 * Gradle Plugin for indicating a Gradle Project as an Android Library.
	 */
	val library by lazy { "com.android.library" }

	/**
	 * Gradle Plugin for facilitating Kotlin options for Android module.
	 */
	val kotlinAndroid by lazy { "kotlin-android" }

	/**
	 * Gradle Plugin for facilitating Annotation Processing in kotlin.
	 */
	val kotlinAnnotationProcessor by lazy { "kotlin-kapt" }

	/**
	 * Custom Gradle Plugin containing the common build configuration
	 * for building an Android Application.
	 */
	val commonApplicationModulePlugin by lazy { "application-module-plugin" }

	/**
	 * Custom Gradle Plugin containing the common build configuration
	 * for building an Android Library.
	 */
	val commonLibraryModulePlugin by lazy { "library-module-plugin" }

}
