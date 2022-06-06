/**
 * Encapsulating the version number of all the dependencies.
 *
 * @author Ritwik Jamuar
 */
object Versions {

    object Gradle {
        const val version = "7.2.1"
    }

    object Kotlin {
        const val standardLibrary = "1.6.21"
        const val coRoutines = "1.6.2"
    }

    object Android {
        const val gradlePlugin = Gradle.version
    }

    object JetPack {

        object Activity {
            const val standardLibrary = "1.4.0"
        }

        object Annotation {
            const val standardLibrary = "1.3.0"
        }

        object AppCompat {
            const val standardLibrary = "1.4.2"
        }

        object CardView {
            const val standardLibrary = "1.0.0"
        }

        object ConstraintLayout {
            const val standardLibrary = "2.1.4"
        }

        object Core {
            const val standardLibrary = "1.8.0"
        }

        object DataBinding {
            const val standardLibrary = gradle
        }

        object DataStore {
            const val standardLibrary = "1.0.0"
        }

        object Legacy {
            const val standardLibrary = "1.0.0"
        }

        object Lifecycle {
            const val standardLibrary = "2.4.1"
        }

        object MultiDex {
            const val standardLibrary = "2.0.1"
        }

        object Navigation {
            const val standardLibrary = "2.4.2"
        }

        object RecyclerView {
            const val standardLibrary = "1.2.1"
        }

        object Test {
            const val coreKTX = "1.4.0"
            const val jUnitExtensionKTX = "1.1.3"
            const val espresso = "3.4.0"
        }

    }

    object Google {

        object Dagger {
            const val standardLibrary = "2.42"
        }

        object Firebase {
            const val bom = "30.1.0"
            const val gradlePlugin = "2.9.0"
        }

        object GMS {
            const val standardLibrary = "4.3.10"
        }

        object Gson {
            const val standardLibrary = "2.8.9"
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
            const val standardLibrary = "2.7"
        }

        object Moshi {
            const val standardLibrary = "1.12.0"
        }

        object OkHttp3 {
            const val loggingInterceptor = "4.9.2"
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

    const val gradle = "7.2.1"

    const val kotlin = "1.5.30"
    const val kotlinCoRoutines = "1.4.3"

    const val androidXAppCompat = "1.3.1"
    const val androidXLegacyV4 = "1.0.0"
    const val annotation = "1.2.0"
    const val recyclerView = "1.2.1"
    const val googleMaterialDesign = "1.4.0"
    const val cardView = "1.0.0"
    const val constraintLayout = "2.1.0"
    const val facebookShimmer = "0.5.0"
    const val superChargeShimmer = "2.1.0"
    const val dagger = "2.42"
    const val coreKTX = "1.6.0"
    const val lifecycleViewModel = "2.3.1"
    const val lifecycleCommon = "2.4.0-alpha03"
    const val lifecycleExtension = "2.2.0"
    const val retrofit = "2.9.0"
    const val picasso = "2.71828"
    const val picassoDownloader = "1.1.0"
    const val loggingInterceptor = "4.9.0"
    const val dataStorePreference = "1.0.0"
    const val jetpackNavigation = "2.3.5"
    const val gson = "2.8.6"
    const val leakCanary = "2.7"

    const val jUnitVersion = "4.13.2"

    const val androidXJUnitVersion = "1.1.3"
    const val androidXEspresso = "3.4.0"

}
