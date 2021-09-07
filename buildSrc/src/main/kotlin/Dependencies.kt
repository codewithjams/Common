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
