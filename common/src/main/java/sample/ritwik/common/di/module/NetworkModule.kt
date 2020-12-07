package sample.ritwik.common.di.module

import dagger.Module
import dagger.Provides

import okhttp3.Cache
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import sample.ritwik.common.component.network.CacheInterceptor

import sample.ritwik.common.di.AppScope

/**
 * [Module] of [OkHttpClient].
 *
 *
 * To provide [OkHttpClient], it depends on following components:
 * 1. [Cache] from [CacheModule].
 * 2. [HttpLoggingInterceptor] from [InterceptorModule].
 * 3. [CacheInterceptor] from [InterceptorModule].
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        CacheModule::class,
        InterceptorModule::class
    ]
)
class NetworkModule {

    /**
     * Provides the instance of [OkHttpClient].
     *
     * @param cache Instance of [Cache] from [CacheModule].
     * @param loggingInterceptor Instance of [HttpLoggingInterceptor] from [InterceptorModule].
     * @param cacheInterceptor Instance of [CacheInterceptor] from [InterceptorModule].
     * @return New Instance of [OkHttpClient].
     */
    @AppScope
    @Provides
    fun providesOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        cacheInterceptor: CacheInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        cache(cache)
        addInterceptor(loggingInterceptor)
        addInterceptor(cacheInterceptor)
    }.build()

}
