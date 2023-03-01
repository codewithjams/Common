/**
 * Encapsulates different Gradle Plugins used.
 *
 * @author Ritwik Jamuar
 */
object Plugins {

	/**
	 * Gradle Plugin for indicating a Gradle Project as an Android Application.
	 */
	val androidApplication by lazy { "com.android.application" }

	/**
	 * Gradle Plugin for indicating a Gradle Project as an Android Library.
	 */
	val androidLibrary by lazy { "com.android.library" }

	/**
	 * Gradle Plugin for indicating a Gradle Project as a Java Library.
	 */
	val javaLibrary by lazy { "java-library" }

	/**
	 * Gradle Plugin for facilitating Kotlin.
	 */
	val kotlin by lazy { "kotlin" }

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
	val androidApplicationModulePlugin by lazy { "android-application-module-plugin" }

	/**
	 * Custom Gradle Plugin containing the common build configuration
	 * for building an Android Library.
	 */
	val androidLibraryModulePlugin by lazy { "android-library-module-plugin" }

	/**
	 * Custom Gradle Plugin containing the common build configuration for building a Java Library.
	 */
	val javaLibraryModulePlugin by lazy { "java-library-module-plugin" }

}
