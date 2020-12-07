package sample.ritwik.common.di.module

import android.content.Context

import dagger.Module
import dagger.Provides

import okhttp3.Cache

import sample.ritwik.common.utility.constant.CACHE_DIRECTORY_NAME
import sample.ritwik.common.utility.constant.CACHE_FILE_SIZE

import java.io.File

import javax.inject.Named

/**
 * [Module] for providing [Cache].
 *
 *
 * To provide [Cache], it depends on [File] that denotes to Cache Directory.
 *
 *
 * But, the Cache Directory differs from every Application.
 *
 *
 * So, we use [Named] Parameters with this key: [CACHE_DIRECTORY_NAME].
 *
 *
 * So, at outside [Module], we need two [Provides] + [Named] annotated methods in order to supply
 * the required values.
 *
 * @author Ritwik Jamuar
 */
@Module
class CacheModule {

    /**
     * Provides the [Cache].
     *
     * @param file Instance of [File] supplied from [providesCacheFile].
     * @return New Instance of [Cache].
     */
    @Provides
    fun providesCache(file: File): Cache = Cache(file, CACHE_FILE_SIZE)

    /**
     * Provides a [File] representing the [Cache] directory in the device for this Application.
     *
     * @param context Instance of Application's [Context].
     * @param cacheDirectoryName [String] denoting the name of the Cache Directory Name
     *   supplied from outside [Module].
     * @return New Instance of [File] denoting the [Cache] directory.
     */
    @Provides
    fun providesCacheFile(
        context: Context,
        @Named(CACHE_DIRECTORY_NAME) cacheDirectoryName: String
    ) = File(context.cacheDir, cacheDirectoryName)

}
