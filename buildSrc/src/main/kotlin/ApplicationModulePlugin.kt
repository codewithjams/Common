import com.android.build.gradle.BaseExtension
import com.android.build.gradle.AppExtension

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * [Plugin] containing the common build configuration of an Application Module [Project].
 *
 * @author Ritwik Jamuar
 */
class ApplicationModulePlugin : Plugin<Project> {

	override fun apply(target: Project) {

		// Adding this Plugin configures the build-process of an Android Application.
		target.plugins.apply(Plugins.application)

		// Add this Plugin for supporting Kotlin with Android.
		target.plugins.apply(Plugins.kotlinAndroid)

		// Get the Android Extension from this project.
		val androidExtensions = target.extensions.getByName("android")

		if(androidExtensions is BaseExtension && androidExtensions is AppExtension) {

			// Set all the build configurations for this Application Module.
			androidExtensions.apply {

				compileSdkVersion(Configuration.compileSDKVersion) // Compile SDK Version.
				//buildToolsVersion = Configuration.buildToolsVersion // Build Tools Version.

				defaultConfig {

					targetSdk = Configuration.targetSDKVersion // Target SDK Version.
					minSdk = Configuration.minimumSDKVersion // Minimum SDK Version.

					// Instrumentation Runner.
					testInstrumentationRunner = Dependencies.JetPack.Test.instrumentationRunner

				}

				buildTypes {

					getByName(BuildTypes.release) {

						isMinifyEnabled = true // Obfuscate the code.
						isShrinkResources = true // Shrink the Resource Files.

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
