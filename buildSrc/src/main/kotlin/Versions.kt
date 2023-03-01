/**
 * Encapsulating the version number of all the dependencies.
 *
 * @author Ritwik Jamuar
 */
object Versions {

	object Gradle {
		const val version = "7.4.1"
	}

	object Kotlin {
		const val standardLibrary = "1.8.10"
		const val coRoutines = "1.6.4"
	}

	object Android {
		const val gradlePlugin = Gradle.version
	}

	object JetPack {

		object Activity {
			const val standardLibrary = "1.6.1"
		}

		object Annotation {
			const val standardLibrary = "1.6.0"
		}

		object AppCompat {
			const val standardLibrary = "1.6.1"
		}

		object CardView {
			const val standardLibrary = "1.0.0"
		}

		object Compose {
			const val composeKotlinCompilerExtensionVersion = "1.4.3"
			const val ui = "1.3.3"
			const val material = "1.3.1"
		}

		object ConstraintLayout {
			const val standardLibrary = "2.1.4"
		}

		object Core {
			const val standardLibrary = "1.9.0"
		}

		object DataBinding {
			const val standardLibrary = Gradle.version
		}

		object DataStore {
			const val standardLibrary = "1.0.0"
		}

		object Legacy {
			const val standardLibrary = "1.0.0"
		}

		object Lifecycle {
			const val standardLibrary = "2.5.1"
		}

		object MultiDex {
			const val standardLibrary = "2.0.1"
		}

		object Navigation {
			const val standardLibrary = "2.5.3"
		}

		object RecyclerView {
			const val standardLibrary = "1.2.1"
		}

		object Test {
			const val coreKTX = "1.5.0"
			const val jUnitExtensionKTX = "1.1.5"
			const val espresso = "3.5.1"
		}

	}

	object Google {

		object Dagger {
			const val standardLibrary = "2.45"
		}

		object Firebase {
			const val bom = "30.1.0"
			const val gradlePlugin = "2.9.0"
		}

		object GMS {
			const val standardLibrary = "4.3.10"
		}

		object Gson {
			const val standardLibrary = "2.10.1"
		}

		object MaterialDesign {
			const val standardLibrary = "1.6.1"
		}

		object Truth {
			const val standardLibrary = "1.1.3"
		}

	}

	object Square {

		object LeakCanary {
			const val standardLibrary = "2.10"
		}

		object Moshi {
			const val standardLibrary = "1.14.0"
		}

		object OkHttp3 {
			const val standardLibrary = "4.10.1"
			const val loggingInterceptor = "4.10.0"
			const val mockWebServer = "4.10.1"
		}

		object Picasso {
			const val standardLibrary = "2.8"
		}

		object Retrofit {
			const val standardLibrary = "2.9.0"
		}

	}

	object JUnit {
		const val standardLibrary = "4.13.2"
	}

	object JakeWharton {

		object PicassoDownloader {
			const val standardLibrary = "1.1.0"
		}

	}

	object Facebook {

		object Shimmer {
			const val standardLibrary = "0.5.0"
		}

	}

}
