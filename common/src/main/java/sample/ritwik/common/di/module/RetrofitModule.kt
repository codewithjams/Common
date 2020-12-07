package sample.ritwik.common.di.module

import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient

import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory

import sample.ritwik.common.di.AppScope

import sample.ritwik.common.utility.constant.BASE_URL

import javax.inject.Named

/**
 * [Module] for providing [Retrofit].
 *
 *
 * To provide [Retrofit], it depends on some components, including a value for Base URL,
 * which differs from every application.
 *
 *
 * So we use [Named] Parameter with the key [BASE_URL].
 *
 *
 * So, at outside [Module], we need a [Provides] + [Named] annotated method
 * in order to supply the Base URL.
 *
 *
 * Along with that, these components are required by [Retrofit]:
 * 1. [OkHttpClient] from [NetworkModule].
 * 2. [MoshiConverterFactory] from [MoshiModule].
 *
 *
 * Since Retrofit and Picasso both uses [OkHttpClient] internally for REST Communication, so we can
 * share the [OkHttpClient] with both.
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        MoshiModule::class,
        NetworkModule::class
    ]
)
class RetrofitModule {

    /**
     * Provides the instance of [Retrofit].
     *
     * @param client Instance of [OkHttpClient] from [NetworkModule].
     * @param moshiConverterFactory Instance of [MoshiConverterFactory] from [MoshiModule].
     * @param baseURL [String] denoting the Base URL End-Point.
     * @return New Instance of [Retrofit].
     */
    @AppScope
    @Provides
    fun providesRetrofit(
        client: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
        @Named(BASE_URL) baseURL: String
    ): Retrofit = Retrofit.Builder().apply {
        client(client)
        addConverterFactory(moshiConverterFactory)
        baseUrl(baseURL)
    }.build()

}
