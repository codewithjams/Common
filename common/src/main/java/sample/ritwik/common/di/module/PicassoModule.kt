package sample.ritwik.common.di.module

import android.content.Context

import com.jakewharton.picasso.OkHttp3Downloader

import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient

import sample.ritwik.common.di.AppScope

/**
 * [Module] of [Picasso].
 *
 *
 * Since Retrofit and Picasso both uses [OkHttpClient] internally for REST Communication, so we can
 * share the [OkHttpClient] with both.
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        NetworkModule::class
    ]
)
class PicassoModule {

    /**
     * Provides the instance of [Picasso],
     *
     * @param context [Context] from Application.
     * @param downloader Instance of [OkHttp3Downloader] from [providesOkHttp3Downloader].
     * @return New Instance of [Picasso].
     */
    @AppScope
    @Provides
    fun providesPicasso (
        context : Context,
        downloader : OkHttp3Downloader
    ) : Picasso = Picasso.Builder(context).apply {
        downloader(downloader) // Set the OkHttp3Downloader
    }.build()

    /**
     * Provides the instance of [OkHttp3Downloader].
     *
     * @param client Instance of [OkHttpClient] from [NetworkModule].
     * @return New Instance of [OkHttp3Downloader].
     */
    @AppScope
    @Provides
    fun providesOkHttp3Downloader(client: OkHttpClient): OkHttp3Downloader =
        OkHttp3Downloader(client)

}
