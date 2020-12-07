package sample.ritwik.common.di.module

import dagger.Module
import dagger.Provides

import okhttp3.logging.HttpLoggingInterceptor

import sample.ritwik.common.BuildConfig

import sample.ritwik.common.component.network.CacheInterceptor

/**
 * [Module] to provide different kinds of [okhttp3.Interceptor]s.
 *
 *
 * For now, these [okhttp3.Interceptor]s are provided:
 * 1. [CacheInterceptor]
 * 2. [HttpLoggingInterceptor]
 *
 * @author Ritwik Jamuar
 */
@Module
class InterceptorModule {

    /**
     * Provides the [CacheInterceptor].
     *
     * @return New Instance of [CacheInterceptor].
     */
    @Provides
    fun provideCacheInterceptor(): CacheInterceptor = CacheInterceptor()

    /**
     * Provides the [HttpLoggingInterceptor].
     *
     *
     * This [HttpLoggingInterceptor] can logs the network activity depending on whether the
     * Application's build environment is Debug or not.
     *
     * @return New Instance of [HttpLoggingInterceptor].
     */
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        android.util.Log.e("REST", message)
    }.apply {
        level = when {
            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }
    }

}
