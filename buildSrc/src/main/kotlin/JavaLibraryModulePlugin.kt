import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.api.plugins.JavaPluginExtension

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * [Plugin] containing the common build configuration of a Java Library [Project].
 *
 * @author Ritwik Jamuar
 */
class JavaLibraryModulePlugin : Plugin<Project> {

	override fun apply(target: Project) {

		// Add Plugins necessary for a Java Library.
		target.plugins.apply(Plugins.javaLibrary)
		target.plugins.apply(Plugins.kotlin)

		// Get the Java Extension from this project.
		val javaExtension = target.extensions.getByName("java")

		// If the extension matches the Java Plugin, then explicitly set the Java version.
		if (javaExtension is JavaPluginExtension) {
			javaExtension.sourceCompatibility = JavaVersion.VERSION_11
			javaExtension.targetCompatibility = JavaVersion.VERSION_11
		}

		// Get the compileKotlin task and set the Java version for Kotlin compiler.
		target.tasks.withType(KotlinCompile::class.java).configureEach {
			kotlinOptions {
				jvmTarget = Configuration.javaVersion
			}
		}

	}

}
