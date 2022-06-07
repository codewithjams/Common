import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * [Plugin] containing the common build configuration of a Library Module [Project].
 *
 * @author Ritwik Jamuar
 */
class LibraryModulePlugin : Plugin<Project> {

	override fun apply(target: Project) {

		// Adding this Plugin configures the build-process of an Android Library.
		target.plugins.apply(Plugins.library)

		// Add this Plugin for supporting Kotlin with Android.
		target.plugins.apply(Plugins.kotlinAndroid)

		// Get the Android Extension from this project.
		val androidExtension = target.extensions.getByName("android")

		if (androidExtension is BaseExtension && androidExtension is LibraryExtension) {

			// Set all the build configurations for this Library Module.
			androidExtension.apply {

				compileSdk = Configuration.compileSDKVersion // Compile SDK Version.
				//buildToolsVersion = Configuration.buildToolsVersion // Build Tools Version.

				defaultConfig {

					targetSdk = Configuration.targetSDKVersion // Target SDK Version.
					minSdk = Configuration.minimumSDKVersion // Minimum SDK Version.

					// Instrumentation Runner.
					testInstrumentationRunner = Dependencies.JetPack.Test.instrumentationRunner

					consumerProguardFiles(ProguardFiles.consumer) // Consumer Proguard File.

				}

				buildTypes {

					getByName(BuildTypes.release) {

						proguardFiles(
								getDefaultProguardFile(ProguardFiles.optimize),
								ProguardFiles.default
						)

					}

				}

				// Configure Java Version throughout the compilation.
				compileOptions {

					sourceCompatibility = JavaVersion.VERSION_11
					targetCompatibility = JavaVersion.VERSION_11

				}

				// Configure Java Version for Kotlin.
				target.tasks.withType(KotlinCompile::class.java).configureEach {
					kotlinOptions {
						jvmTarget = Configuration.javaVersion
					}
				}

			}

		}

	}

}
