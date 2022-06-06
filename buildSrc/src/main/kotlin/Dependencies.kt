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
			 * [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
			 * is used to log HTTP request and response data.
			 */
			const val loggingInterceptor =
				"$prefix:logging-interceptor:${Versions.Square.OkHttp3.loggingInterceptor}"

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
				"com.facebook.shimmer:shimmer:${Versions.Facebook.Shimmer.standardLibrary}"

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
				"$commonPrefix:shimmerlayout:${Versions.superChargeShimmer}"

		}

	}

}

/**
 * Encapsulates the Kotlin libraries.
 *
 * @author Ritwik Jamuar
 */
object Kotlin {

    /**
     * Kotlin Standard Library.
     */
    val standardLibrary by lazy { "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}" }

    /**
     * Kotlin Co-Routines.
     */
    val coRoutines by lazy {
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoRoutines}"
    }

    /**
     * Kotlin Gradle Plugin.
     */
    val gradlePlugin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }

}

/**
 * Encapsulates the common libraries around Android.
 *
 * @author Ritwik Jamuar
 */
object AndroidCommon {

    /**
     * AndroidX AppCompat for facilitating common AppCompat classes.
     */
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.androidXAppCompat}" }

    /**
     * AndroidX Annotations for facilitating Annotations specific to Android.
     */
    val annotations by lazy { "androidx.annotation:annotation:${Versions.annotation}" }

    /**
     * AndroidX Core KTX for facilitating core libraries designed around Kotlin.
     */
    val coreKTX by lazy { "androidx.core:core-ktx:${Versions.coreKTX}" }

    /**
     * AndroidX Legacy Support v4 for facilitating Legacy classes and resources.
     */
    val legacySupportV4 by lazy { "androidx.legacy:legacy-support-v4:${Versions.androidXLegacyV4}" }

    /**
     * Android Build Gradle Plugin.
     */
    val gradlePlugin by lazy { "com.android.tools.build:gradle:${Versions.gradle}" }

}

/**
 * Encapsulates the libraries that facilitates the Dependency Injection.
 *
 * @author Ritwik Jamuar
 */
object DependencyInjection {

    /**
     * Google's Dagger Core Library.
     */
    val daggerCore by lazy { "com.google.dagger:dagger:${Versions.dagger}" }

    /**
     * Google's Dagger Android Library specific to Android components.
     */
    val daggerAndroid by lazy { "com.google.dagger:dagger-android:${Versions.dagger}" }

    /**
     * Annotation Processor of Google's Dagger Android for generating classes
     * with Android Components.
     */
    val daggerAndroidProcessor by lazy {
        "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    /**
     * Annotation Processor of Google's Dagger for generating general dependencies.
     */
    val daggerCompiler by lazy { "com.google.dagger:dagger-compiler:${Versions.dagger}" }

}

/**
 * Encapsulates the Jetpack Lifecycle libraries.
 *
 * @author Ritwik Jamuar
 */
object JetpackLifecycle {

    /**
     * AndroidX Lifecycle ViewModel for facilitating ViewModel.
     */
    val viewModel by lazy {
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewModel}"
    }

    /**
     * AndroidX Lifecycle Core Library for facilitating LiveData.
     */
    val liveDataCore by lazy {
        "androidx.lifecycle:lifecycle-livedata-core:${Versions.lifecycleViewModel}"
    }

    /**
     * AndroidX Lifecycle KTX Runtime Components.
     */
    val runtime by lazy {
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleViewModel}"
    }

    /**
     * AndroidX Lifecycle Common components around Java 8.
     */
    val commonJava8 by lazy {
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycleViewModel}"
    }

    /**
     * AndroidX Lifecycle Common components.
     */
    val common by lazy { "androidx.lifecycle:lifecycle-common:${Versions.lifecycleCommon}" }

    /**
     * Annotation Processor for extension around AndroidX Lifecycle.
     */
    val extension by lazy {
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtension}"
    }

}

/**
 * Encapsulates the libraries of Jetpack Navigation.
 *
 * @author Ritwik Jamuar
 */
object JetpackNavigation {

    /**
     * AndroidX Navigation UI for facilitating building of Navigation Graph.
     */
    val ui by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.jetpackNavigation}" }

    /**
     * AndroidX Navigation Fragment for facilitating rendering of Fragments as a destinations.
     */
    val fragment by lazy {
        "androidx.navigation:navigation-fragment-ktx:${Versions.jetpackNavigation}"
    }

}

/**
 * Encapsulates the libraries for facilitating the REST API Capabilities.
 *
 * @author Ritwik Jamuar
 */
object Network {

    /**
     * Square's Retrofit facilitates REST API communication over the Network.
     */
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }

    /**
     * Square's Retrofit Moshi Converter that facilitates conversion of Kotlin Classes to JSON
     * and vice-versa using Moshi.
     */
    val retrofitMoshiConverter by lazy {
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    /**
     * Square's Retrofit Mock to facilitating mocking the REST API Calls.
     */
    val retrofitMock by lazy { "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}" }

    /**
     * Square's Picasso to render an Image from a given URL by fetching the Image over the Network.
     */
    val picasso by lazy { "com.squareup.picasso:picasso:${Versions.picasso}" }

    /**
     * Picasso Downloader by Jake Wharton to facilitate downloading of Image over Network.
     */
    val picassoDownloader by lazy {
        "com.jakewharton.picasso:picasso2-okhttp3-downloader:${Versions.picassoDownloader}"
    }

    /**
     * Square's OkHttp3 LoggingInterceptor to facilitating printing logs of Network Communication
     * between Client and Server.
     */
    val loggingInterceptor by lazy {
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    }

}

/**
 * Encapsulates the libraries for facilitating the Persistence Storage.
 *
 * @author Ritwik Jamuar
 */
object Persistence {

    /**
     * AndroidX DataStore as a modern approach and improvement on SharedPreferences.
     */
    val dataStorePreference by lazy {
        "androidx.datastore:datastore-preferences:${Versions.dataStorePreference}"
    }

}

/**
 * Encapsulates the libraries for building a better User Interfaces.
 *
 * @author Ritwik Jamuar
 */
object UI {

    /**
     * Google's Material Design components.
     */
    val materialDesign by lazy {
        "com.google.android.material:material:${Versions.googleMaterialDesign}"
    }

    /**
     * AndroidX CardView for rendering a Card in the UI.
     */
    val cardView by lazy { "androidx.cardview:cardview:${Versions.cardView}" }

    /**
     * AndroidX ConstraintLayout for positioning various items relatively in a flat hierarchical way
     * for faster rendering of UI.
     */
    val constraintLayout by lazy {
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    }

    /**
     * Shimmer Layout from Facebook, an improvement upon [shimmerLayoutSuperCharge].
     */
    val shimmerLayout by lazy { "com.facebook.shimmer:shimmer:${Versions.facebookShimmer}" }

    /**
     * Shimmer Layout from SuperCharge.io for facilitating the rendering of Shimmer Effect.
     */
    val shimmerLayoutSuperCharge by lazy {
        "io.supercharge:shimmerlayout:${Versions.superChargeShimmer}"
    }

    /**
     * AndroidX RecyclerView for facilitating rendering of List of Items.
     */
    val recyclerView by lazy { "androidx.recyclerview:recyclerview:${Versions.recyclerView}" }

    /**
     * Annotation Processor for AndroidX DataBinding to facilitate building the ViewBinding classes
     * for the Android Layouts.
     */
    val dataBindingCompiler by lazy {
        "androidx.databinding:databinding-compiler:${Versions.gradle}"
    }

}

/**
 * Encapsulates the utilitarian libraries.
 *
 * @author Ritwik Jamuar
 */
object Utility {

    /**
     * Google's GSON for facilitating conversion between Java Objects to JSON and vice-versa.
     */
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }

    /**
     * Square's Leak Canary helps in detecting Memory Leaks within an Application.
     */
    val leakCanary by lazy { "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}" }

}

/**
 * Encapsulates the list of Libraries that helps with Unit Testing in Android.
 *
 * @author Ritwik Jamuar
 */
object UnitTesting {

    /**
     * JUnit for performing Unit Test around the components.
     */
    val jUnit by lazy { "junit:junit:${Versions.jUnitVersion}" }

}

/**
 * Encapsulates the list of Libraries that helps with Instrumentation Testing in Android.
 *
 * @author Ritwik Jamuar
 */
object InstrumentationTesting {

    /**
     * Runner of Instrumentation Testing on which the Instrumentation Testing runs upon.
     */
    val runner by lazy { "androidx.test.runner.AndroidJUnitRunner" }

    /**
     * AndroidX Variant of JUnit for Unit Testing within the Instrumentation Test.
     */
    val androidXJunit by lazy { "androidx.test.ext:junit:${Versions.androidXJUnitVersion}" }

    /**
     * AndroidX Espresso for mocking UI Components.
     */
    val androidXEspresso by lazy {
        "androidx.test.espresso:espresso-core:${Versions.androidXEspresso}"
    }

}
