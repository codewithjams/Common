/**
 * Encapsulates the different dependencies used across this Android Project.
 *
 * @author Ritwik Jamuar
 */
object Dependencies {

	/**
	 * Encapsulates the Kotlin libraries.
	 */
	object Kotlin {

		private const val prefix = "org.jetbrains.kotlin"

		/**
		 * Kotlin Standard Library.
		 */
		const val standardLibrary = "$prefix:kotlin-stdlib:${Versions.Kotlin.standardLibrary}"

		/**
		 * Provides Co-Routines, which is a concurrency design pattern that you can use on Android
		 * to simplify code that executes asynchronously.
		 */
		const val coRoutines =
			"${prefix}x:kotlinx-coroutines-android:${Versions.Kotlin.coRoutines}"

		/**
		 * Provides core components of Co-Routines without any dependency on Android.
		 */
		const val coRoutinesCore =
			"${prefix}x:kotlinx-coroutines-core:${Versions.Kotlin.coRoutines}"

		/**
		 * Gradle Plugin for Kotlin.
		 */
		const val gradlePlugin = "$prefix:kotlin-gradle-plugin:${Versions.Kotlin.standardLibrary}"

	}

	/**
	 * Collection of Libraries exclusive to Android.
	 */
	object Android {

		/**
		 * Gradle Plugin for Android.
		 */
		const val gradlePlugin = "com.android.tools.build:gradle:${Versions.Android.gradlePlugin}"

	}

	/**
	 * Collection of [Jetpack](https://developer.android.com/jetpack/androidx/explorer?case=all)
	 * Libraries by Android.
	 */
	object JetPack {

		private const val commonPrefix = "androidx"

		/**
		 * [Activity](https://developer.android.com/jetpack/androidx/releases/activity):
		 * Access composable APIs built on top of Activity.
		 */
		object Activity {

			private const val prefix = "$commonPrefix.activity"

			/**
			 * Kotlin optimized variant of the standard library
			 * providing core components of [Activity].
			 */
			const val standardLibraryKTX =
				"$prefix:activity-ktx:${Versions.JetPack.Activity.standardLibrary}"

			/**
			 * Standard Library along with [Compose] specifics implementation of [Activity].
			 */
			const val compose =
				"$prefix:activity-compose:${Versions.JetPack.Activity.standardLibrary}"

		}

		/**
		 * [Annotation](https://developer.android.com/jetpack/androidx/releases/annotation):
		 * Expose metadata that helps tools and other developers understand your app's code.
		 */
		object Annotation {

			private const val prefix = "$commonPrefix.annotation"

			/**
			 * Standard Library providing core components of [Annotation].
			 */
			const val standardLibrary =
				"$prefix:annotation:${Versions.JetPack.Annotation.standardLibrary}"

		}

		/**
		 * [AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat):
		 * Allows access to new APIs on older API versions of the platform
		 * (many using Material Design).
		 */
		object AppCompat {

			private const val prefix = "$commonPrefix.appcompat"

			/**
			 * Standard Library providing core components of [AppCompat].
			 */
			const val standardLibrary =
				"$prefix:appcompat:${Versions.JetPack.AppCompat.standardLibrary}"

		}

		/**
		 * [CardView](https://developer.android.com/jetpack/androidx/releases/cardview):
		 * Implement the Material Design card pattern with round corners and drop shadows.
		 */
		object CardView {

			private const val prefix = "$commonPrefix.cardview"

			/**
			 * Standard Library providing core components of [CardView].
			 */
			const val standardLibrary =
				"$prefix:cardview:${Versions.JetPack.CardView.standardLibrary}"

		}

		/**
		 * [Compose](https://developer.android.com/jetpack/androidx/releases/compose):
		 * Define your UI programmatically with composable functions that describe its shape
		 * and data dependencies.
		 */
		object Compose {

			private const val composePrefix = "$commonPrefix.compose"

			/**
			 * [Compose UI](https://developer.android.com/jetpack/androidx/releases/compose-ui):
			 * Fundamental components of compose UI needed to interact with the device,
			 * including layout, drawing, and input.
			 */
			object UI {

				private const val prefix = "$composePrefix.ui"

				/**
				 * Standard Library providing core components of [UI].
				 */
				const val standardLibrary =
					"$prefix:ui:${Versions.JetPack.Compose.ui}"

				/**
				 * Provide different tools of [UI].
				 */
				const val tooling =
					"$prefix:ui-tooling:${Versions.JetPack.Compose.ui}"

				/**
				 * Provide preview tooling for [UI].
				 */
				const val toolingPreview =
					"$prefix:ui-tooling-preview:${Versions.JetPack.Compose.ui}"

				/**
				 * Provides Test rules and transitive dependencies for testing [UI] components.
				 */
				const val testManifest =
					"$prefix:ui-test-manifest:${Versions.JetPack.Compose.ui}"

				/**
				 * Provides components needed for `createAndroidComposeRule`,
				 * but not `createComposeRule`.
				 */
				const val testJUnit4 =
					"$prefix:ui-test-junit4:${Versions.JetPack.Compose.ui}"

			}

			/**
			 * [Compose Material](https://developer.android.com/jetpack/androidx/releases/compose-material):
			 * Build Jetpack Compose UIs with ready to use Material Design Components.
			 * This is the higher level entry point of Compose, designed to provide components
			 * that match those described at [www.material.io](www.material.io).
			 */
			object Material {

				private const val prefix = "$composePrefix.material"

				/**
				 * Standard Library providing core components of [Material].
				 */
				const val standardLibrary =
					"$prefix:material:${Versions.JetPack.Compose.material}"

			}

		}

		/**
		 * [ConstraintLayout](https://developer.android.com/jetpack/androidx/releases/constraintlayout):
		 * Positions and sizes widgets in a flexible way with relative positioning.
		 */
		object ConstraintLayout {

			private const val prefix = "$commonPrefix.constraintlayout"

			/**
			 * Standard Library providing core components of [ConstraintLayout].
			 */
			const val standardLibrary =
				"$prefix:constraintlayout:${Versions.JetPack.ConstraintLayout.standardLibrary}"

		}

		/**
		 * [Core](https://developer.android.com/jetpack/androidx/releases/core):
		 * Targets the latest platform features and APIs while also supporting older devices.
		 */
		object Core {

			private const val prefix = "$commonPrefix.core"

			/**
			 * Kotlin optimized variant of the standard library providing core components of [Core].
			 */
			const val standardLibraryKTX =
				"$prefix:core-ktx:${Versions.JetPack.Core.standardLibrary}"

		}

		/**
		 * [DataBinding](https://developer.android.com/jetpack/androidx/releases/databinding):
		 * Bind UI components in your layouts to data sources in your app
		 * using a declarative format.
		 */
		object DataBinding {

			private const val prefix = "$commonPrefix.databinding"

			/**
			 * Annotation Processor to facilitate generating View Bindings during compilation.
			 */
			@Deprecated(
				message = """
					As new updates have rolled, specifying the DataBinding Compiler
					is no longer required, since it is now bundled with Android's Gradle Plugin.
					For more information,
					please refer: https://developer.android.com/jetpack/androidx/releases/databinding
				""",
				level = DeprecationLevel.WARNING
			)
			const val compiler =
				"$prefix:databinding-compiler:${Versions.JetPack.DataBinding.standardLibrary}"

		}

		/**
		 * [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore):
		 * Store data asynchronously, consistently, and transactionally,
		 * overcoming some of the drawbacks of SharedPreferences
		 */
		object DataStore {

			private const val prefix = "$commonPrefix.datastore"

			/**
			 * Standard Library providing core components of [DataStore].
			 */
			const val standardLibrary =
				"$prefix:datastore-preferences:${Versions.JetPack.DataStore.standardLibrary}"

			/**
			 * Core Library without Android dependency of [DataStore].
			 */
			const val core =
				"$prefix:datastore-preferences-core:${Versions.JetPack.DataStore.standardLibrary}"

		}

		/**
		 * [Legacy](https://developer.android.com/jetpack/androidx/releases/legacy):
		 * AndroidX Legacy Support v4 for facilitating Legacy classes and resources.
		 */
		@Deprecated(
			message = """
				This artifact and its classes are deprecated.
				Starting with Android 8, background check restrictions make this class
				no longer useful.
				Visit for more details:
				https://developer.android.com/jetpack/androidx/releases/legacy
			""",
			level = DeprecationLevel.WARNING
		)
		object Legacy {

			private const val prefix = "$commonPrefix.legacy"

			/**
			 * Provides classes and artifacts that support Android v4.
			 */
			const val v4Support =
				"$prefix:legacy-support-v4:${Versions.JetPack.Legacy.standardLibrary}"

		}

		/**
		 * [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle):
		 * Lifecycle-aware components perform actions in response to a change in the lifecycle
		 * status of another component, such as Activity and Fragment. These components helps you
		 * produce better-organized, and often lighter-weight code, that is easier to maintain.
		 */
		object Lifecycle {

			private const val prefix = "$commonPrefix.lifecycle"
			private const val version = Versions.JetPack.Lifecycle.standardLibrary

			/**
			 * Provides ViewModel which can survive configuration changes and provides events.
			 */
			const val viewModel = "$prefix:lifecycle-viewmodel-ktx:$version"

			/**
			 * Provides LiveData which facilitates passing values to UI
			 * while respecting UI's Lifecycle.
			 */
			const val liveData = "$prefix:lifecycle-livedata-ktx:$version"

			/**
			 * Provides Lifecycle methods without ViewModel and LiveData.
			 */
			const val runTime = "$prefix:lifecycle-runtime-ktx:$version"

			/**
			 * Use standard library designed for Java 8 for faster incremental builds.
			 */
			const val java8Common = "$prefix:lifecycle-common-java8:$version"

			/**
			 * Annotation Processor for handling lifecycle-aware components.
			 */
			const val compiler = "$prefix:lifecycle-compiler:$version"

		}

		/**
		 * [MultiDex](https://developer.android.com/jetpack/androidx/releases/multidex):
		 * Deploy application with multiple .dex files on pre-Android 5 devices.
		 */
		object MultiDex {

			private const val prefix = "$commonPrefix.multidex"

			/**
			 * Standard Library providing core components of [MultiDex].
			 */
			const val standardLibrary =
				"$prefix:multidex:${Versions.JetPack.MultiDex.standardLibrary}"

		}

		/**
		 * [Navigation](https://developer.android.com/jetpack/androidx/releases/navigation):
		 * Build and structure your in-app UI, handle deep links, and navigate between screens.
		 */
		object Navigation {

			private const val prefix = "$commonPrefix.navigation"
			private const val version = Versions.JetPack.Navigation.standardLibrary

			/**
			 * AndroidX Navigation UI for facilitating building of Navigation Graph.
			 */
			const val ui = "$prefix:navigation-ui-ktx:$version"

			/**
			 * AndroidX Navigation Fragment for facilitating rendering of Fragments as a destinations.
			 */
			const val fragment = "$prefix:navigation-fragment-ktx:$version"

			/**
			 * Annotation Processor to facilitate passing data between two destination in Navigation
			 * with type-safety.
			 */
			const val safeArgsGradlePlugin = "$prefix:navigation-safe-args-gradle-plugin:$version"

		}

		/**
		 * [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview):
		 * Display large sets of data in the UI while minimizing memory usage.
		 */
		object RecyclerView {

			private const val prefix = "$commonPrefix.recyclerview"

			/**
			 * Standard Library providing core components of [RecyclerView].
			 */
			const val standardLibrary =
				"$prefix:recyclerview:${Versions.JetPack.RecyclerView.standardLibrary}"

		}

		/**
		 * [Test](https://developer.android.com/jetpack/androidx/releases/test):
		 * Offers different tools required for performing Unit/Instrumentation Testing.
		 */
		object Test {

			private const val prefix = "$commonPrefix.test"

			/**
			 * Test Runner on which Instrumentation Testing runs upon.
			 */
			const val instrumentationRunner = "$prefix.runner.AndroidJUnitRunner"

			/**
			 * Provides Kotlin support for testing Android Core elements.
			 */
			const val coreKTX = "$prefix:core-ktx:${Versions.JetPack.Test.coreKTX}"

			/**
			 * Provides JUnit Extension Functions with Kotlin Support.
			 */
			const val jUnitExtensionKTX =
				"$prefix.ext:junit-ktx:${Versions.JetPack.Test.jUnitExtensionKTX}"

			/**
			 * Enables to write UI Tests.
			 */
			const val espresso = "$prefix.espresso:espresso-core:${Versions.JetPack.Test.espresso}"

		}

	}

	/**
	 * Collection of Libraries offered by [Google](https://github.com/google).
	 */
	object Google {

		private const val commonPrefix = "com.google"

		/**
		 * [Dagger](https://github.com/google/dagger) is a compile-time framework
		 * for dependency injection. It uses no reflection or runtime bytecode generation,
		 * does all its analysis at compile-time, and generates plain Java source code.
		 */
		object Dagger {

			private const val prefix = "$commonPrefix.dagger"
			private const val version = Versions.Google.Dagger.standardLibrary

			/**
			 * Standard Library providing the core components of [Dagger].
			 */
			const val standardLibrary = "$prefix:dagger:$version"

			/**
			 * Provides helper classes and definitions specfic to Android.
			 */
			const val android = "$prefix:dagger-android:$version"

			/**
			 * Annotation Processor that generates classes in Dependency Tree
			 * defined by the annotations.
			 */
			const val compiler = "$prefix:dagger-compiler:$version"

			/**
			 * Annotation Processor specific to Android for supporting generation of classes
			 * specific to Android.
			 */
			const val androidProcessor = "$prefix:dagger-android-processor:$version"

		}

		/**
		 * [Firebase](https://firebase.google.com/docs) is a collection of tools that helps building
		 * Apps by providing some utilities.
		 */
		object Firebase {

			private const val prefix = "$commonPrefix.firebase"

			/**
			 * Firebase Bill of Materials so that we don't need to explicitly specify
			 * version of each sub-dependency again.
			 */
			const val bom = "$prefix:firebase-bom:${Versions.Google.Firebase.bom}"

			/**
			 * Build Serverless apps by storing and syncing JSON data between the users
			 * in near-realtime, on or offline, with strong user-based security.
			 */
			const val database = "$prefix:firebase-database-ktx"

			/**
			 * Monitor user acquisition and feature adoption metrics as you launch app
			 * with free and unlimited app analytics.
			 */
			const val analytics = "$prefix:firebase-analytics-ktx"

			/**
			 * Get clear, actionable insight into app issues with this powerful
			 * crash reporting solution for Android.
			 */
			object Crashlytics {

				/**
				 * Standard Library featuring [Crashlytics].
				 */
				const val standardLibrary =
					"$prefix:firebase-crashlytics-ktx"

				/**
				 * Gradle Plugin for Android.
				 */
				const val gradlePlugin =
					"$prefix:firebase-crashlytics-gradle:${Versions.Google.Firebase.gradlePlugin}"

			}

		}

		/**
		 * [Google Mobile Services](https://www.android.com/intl/en_in/gms/) is a
		 * collection of Google applications and APIs that help support
		 * functionality across devices.
		 */
		object GMS {

			private const val prefix = "$commonPrefix.gms"

			/**
			 * The `google-services` plugin has two main functions:
			 * 1. Process the `google-services.json` file and produce Android resources
			 * that can be used in your application's code.
			 * 2. Add dependencies for basic libraries required for the services you have enabled.
			 */
			const val gradlePlugin =
				"$prefix:google-services:${Versions.Google.GMS.standardLibrary}"

		}

		/**
		 * [Gson](https://github.com/google/gson) is a Java serialization/deserialization library
		 * to convert Java Objects into JSON and back.
		 */
		object Gson {

			private const val prefix = "$commonPrefix.code.gson"

			/**
			 * Provides the core components of [Gson].
			 */
			const val standardLibrary = "$prefix:gson:${Versions.Google.Gson.standardLibrary}"

		}

		/**
		 * [Material Design](https://material.io/design) is a design system created by Google
		 * to help teams build high-quality digital experiences for Android, iOS, Flutter,
		 * and the web.
		 */
		object MaterialDesign {

			private const val prefix = "$commonPrefix.android.material"

			/**
			 * Provides the core components of [MaterialDesign].
			 */
			const val standardLibrary =
				"$prefix:material:${Versions.Google.MaterialDesign.standardLibrary}"

		}

		/**
		 * [Truth](https://truth.dev/) is a library for performing assertions in tests.
		 */
		object Truth {

			private const val prefix = "$commonPrefix.truth"

			/**
			 * Provides the core components of [Truth].
			 */
			const val standardLibrary =
				"$prefix:truth:${Versions.Google.Truth.standardLibrary}"

		}


	}

	/**
	 * Collection of Libraries offered by [Square](https://square.github.io).
	 */
	object Square {

		private const val commonPrefix = "com.squareup"

		/**
		 * [LeakCanary](https://square.github.io/leakcanary/) is a memory leak detection library
		 * for Android.
		 */
		object LeakCanary {

			private const val prefix = "$commonPrefix.leakcanary"

			/**
			 * Provides the core components of [LeakCanary].
			 *
			 * Use this library with `debugImplementation` so that it's source binaries doesn't
			 * gets added in Release variant of build.
			 */
			const val standardLibrary =
				"$prefix:leakcanary-android:${Versions.Square.LeakCanary.standardLibrary}"

		}

		/**
		 * [Moshi](https://github.com/square/moshi) is a modern JSON library
		 * for Android, Java and Kotlin.
		 */
		object Moshi {

			private const val prefix = "$commonPrefix.moshi"

			/**
			 * Provides the core components of [Moshi].
			 */
			const val standardLibrary =
				"$prefix:moshi:${Versions.Square.Moshi.standardLibrary}"

			/**
			 * Kotlin specific implementation of [Moshi] which understands
			 * Kotlin’s non-nullable types and default parameter values.
			 */
			const val kotlin =
				"$prefix:moshi-kotlin:${Versions.Square.Moshi.standardLibrary}"

			/**
			 * It generates a small and fast adapter for each of your Kotlin classes
			 * at compile-time.
			 */
			const val codeGen =
				"$prefix:moshi-kotlin-codegen:${Versions.Square.Moshi.standardLibrary}"

		}

		/**
		 * [OkHttp3](https://github.com/square/okhttp) is an HTTP client that’s efficient
		 * by default:
		 * 1. HTTP/2 support allows all requests to the same host to share a socket.
		 * 2. Connection pooling reduces request latency (if HTTP/2 isn’t available)
		 * 3. Transparent GZIP shrinks download sizes
		 * 4. Response caching avoids the network completely for repeat requests.
		 */
		object OkHttp3 {

			private const val prefix = "$commonPrefix.okhttp3"

			/**
			 * [OkHttp3](https://square.github.io/okhttp/): Standard Library as HTTP client.
			 */
			const val standardLibrary =
				"$prefix:okhttp:${Versions.Square.OkHttp3.standardLibrary}"

			/**
			 * [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
			 * is used to log HTTP request and response data.
			 */
			const val loggingInterceptor =
				"$prefix:logging-interceptor:${Versions.Square.OkHttp3.loggingInterceptor}"

			/**
			 * [MockWebServer](https://square.github.io/okhttp/#mockwebserver): Mocks a Web Server.
			 */
			const val mockWebServer =
				"$prefix:mockwebserver:${Versions.Square.OkHttp3.mockWebServer}"

		}

		/**
		 * [Picasso](https://github.com/square/picasso) is a powerful image downloading
		 * and caching library for Android.
		 */
		object Picasso {

			private const val prefix = "$commonPrefix.picasso"

			/**
			 * Standard Library to provide core components of [Picasso].
			 */
			const val standardLibrary =
				"$prefix:picasso:${Versions.Square.Picasso.standardLibrary}"

		}

		/**
		 * [Retrofit](https://github.com/square/retrofit) is a type safe HTTP client
		 * for Android and Java.
		 */
		object Retrofit {

			private const val prefix = "$commonPrefix.retrofit2"

			/**
			 * Standard Library to providing core components of [Retrofit].
			 */
			const val standardLibrary =
				"$prefix:retrofit:${Versions.Square.Retrofit.standardLibrary}"

			/**
			 * [Mock Web Server](https://github.com/square/retrofit/tree/master/retrofit-mock)
			 * for mocking HTTP responses from a server, and simulating network behaviour.
			 */
			const val mock =
				"$prefix:retrofit-mock:${Versions.Square.Retrofit.standardLibrary}"

			/**
			 * [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi):
			 * A Converter which uses [Moshi] for serialization to and from JSON.
			 */
			const val moshiConverter =
				"$prefix:converter-moshi:${Versions.Square.Retrofit.standardLibrary}"

			/**
			 * [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson):
			 * A Converter which uses [Google.Gson] for serialization to and from JSON.
			 */
			const val gsonConverter =
				"$prefix:converter-gson:${Versions.Square.Retrofit.standardLibrary}"

		}

	}

	/**
	 * [JUnit](https://github.com/junit-team/junit4): A simple framework to write repeatable tests.
	 * It is an instance of the xUnit architecture for unit testing frameworks.
	 */
	object JUnit {

		private const val commonPrefix = "junit"

		/**
		 * Standard Library providing core components of [JUnit].
		 */
		const val standardLibrary = "$commonPrefix:junit:${Versions.JUnit.standardLibrary}"

	}

	/**
	 * Open-Source Libraries provided by [Jake Wharton](https://github.com/JakeWharton).
	 */
	object JakeWharton {

		private const val commonPrefix = "com.jakewharton"

		/**
		 * [Picasso to OkHttp3 Downloader](https://github.com/JakeWharton/picasso2-okhttp3-downloader):
		 * A OkHttp 3 downloader implementation for Picasso 2.
		 */
		@Deprecated(
			message = """
				Post Picasso version 2.5.2, there is a replacement officially by Picasso itself.
				`com.squareup.picasso.OkHttp3Downloader`.
			""",
			level = DeprecationLevel.WARNING
		)
		object PicassoDownloader {

			private const val prefix = "$commonPrefix.picasso"

			/**
			 * Standard Library providing core components of [PicassoDownloader].
			 */
			const val standardLibrary =
				"$prefix:picasso2-okhttp3-downloader:${Versions.JakeWharton.PicassoDownloader.standardLibrary}"

		}

	}

	/**
	 * Collection of Libraries offered by [Facebook](https://github.com/facebook).
	 */
	object Facebook {

		const val commonPrefix = "com.facebook"

		/**
		 * [Shimmer](https://github.com/facebook/shimmer-android):
		 * It is an Android library that provides an easy way to add a shimmer effect to any view
		 * in your Android app.
		 */
		object Shimmer {

			private const val prefix = "$commonPrefix.shimmer"

			/**
			 * Standard Library providing core components of [Shimmer].
			 */
			const val standardLibrary =
				"$prefix:shimmer:${Versions.Facebook.Shimmer.standardLibrary}"

		}

	}

	/**
	 * Collection of Libraries offered by [Supercharge](https://github.com/team-supercharge).
	 */
	object SuperCharge {

		private const val commonPrefix = "io.supercharge"

		/**
		 * [ShimmerLayout](https://github.com/team-supercharge/ShimmerLayout):
		 * ShimmerLayout can be used to add shimmer effect to your Android application.
		 * Beside memory efficiency even animating a big layout,
		 * you can customize the behaviour and look of the animation.
		 */
		@Deprecated(
			message = """
				This tool is now deprecated.
				Please switch to Shimmer for Android or any other shimmer effect solution.
			""",
			level = DeprecationLevel.WARNING
		)
		object ShimmerLayout {

			/**
			 * Standard Library providing core components of [ShimmerLayout].
			 */
			const val standardLibrary =
				"$commonPrefix:shimmerlayout:2.1.0"

		}

	}

}
