package sample.ritwik.app.di.application.module

import android.content.Context

import com.jakewharton.picasso.OkHttp3Downloader

import com.squareup.picasso.Picasso

import dagger.Module
import dagger.Provides

import okhttp3.Cache
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import sample.ritwik.app.BuildConfig

import sample.ritwik.app.di.application.scope.AppScope

import sample.ritwik.app.repository.rest.CacheInterceptor

import java.io.File

import javax.inject.Named

/**
 * [Module] of [Picasso].
 *
 *
 * Since Retrofit and Picasso both uses [OkHttpClient] internally for REST Communication, so we can
 * share the [OkHttpClient] with both.
 *
 * @author Ritwik Jamuar
 */
@Module
class PicassoModule {

	/**
	 * Provides the instance of [Picasso],
	 *
	 * @param context [Context] from Application.
	 * @param downloader Instance of [OkHttp3Downloader].
	 *
	 * @return New Instance of [Picasso].
	 */
	@AppScope
	@Provides
	fun providesPicasso (
		context : Context,
		@Named(COMPONENT_IMAGE) downloader : OkHttp3Downloader
	) : Picasso = Picasso.Builder(context).apply {
		downloader(downloader) // Set the OkHttp3Downloader
	}.build()

	/**
	 * Provides the instance of [OkHttp3Downloader].
	 *
	 * @param client Instance of [OkHttpClient].
	 *
	 * @return New Instance of [OkHttp3Downloader].
	 */
	@AppScope
	@Provides
	@Named(COMPONENT_IMAGE)
	fun providesOkHttp3Downloader(
		@Named(COMPONENT_IMAGE) client: OkHttpClient
	): OkHttp3Downloader = OkHttp3Downloader(client)

	/**
	 * Provides the instance of [OkHttpClient].
	 *
	 * @param cache Instance of [Cache].
	 * @param loggingInterceptor Instance of [HttpLoggingInterceptor].
	 * @param cacheInterceptor Instance of [CacheInterceptor].
	 *
	 * @return New Instance of [OkHttpClient].
	 */
	@AppScope
	@Provides
	@Named(COMPONENT_IMAGE)
	fun providesOkHttpClient(
		@Named(COMPONENT_IMAGE) cache: Cache,
		@Named(COMPONENT_IMAGE) loggingInterceptor: HttpLoggingInterceptor,
		@Named(COMPONENT_IMAGE) cacheInterceptor: CacheInterceptor
	): OkHttpClient = OkHttpClient.Builder().apply {
		cache(cache)
		addInterceptor(loggingInterceptor)
		addInterceptor(cacheInterceptor)
	}.build()

	/**
	 * Provides the [Cache].
	 *
	 * @param file Instance of [File] supplied from [providesCacheFile].
	 *
	 * @return New Instance of [Cache].
	 */
	@Provides
	@AppScope
	@Named(COMPONENT_IMAGE)
	fun providesCache(
		@Named(COMPONENT_IMAGE) file: File,
		@Named(CACHE_FILE_SIZE_IMAGE) size: Long
	): Cache = Cache(file, size)

	/**
	 * Provides a [File] representing the [Cache] directory in the device for this Application.
	 *
	 * @param context Instance of Application's [Context].
	 * @param cacheDirectoryName [String] denoting the name of the Cache Directory Name
	 *   supplied from outside [Module].
	 *
	 * @return New Instance of [File] denoting the [Cache] directory.
	 */
	@Provides
	@AppScope
	@Named(COMPONENT_IMAGE)
	fun providesCacheFile(
		context: Context,
		@Named(CACHE_DIRECTORY_NAME_IMAGE) cacheDirectoryName: String
	) = File(context.cacheDir, cacheDirectoryName)

	/**
	 * Provides the [CacheInterceptor].
	 *
	 * @return New Instance of [CacheInterceptor].
	 */
	@Provides
	@AppScope
	@Named(COMPONENT_IMAGE)
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
	@AppScope
	@Named(COMPONENT_IMAGE)
	fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
		android.util.Log.e("Image", message)
	}.apply {
		level = when {
			BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
			else -> HttpLoggingInterceptor.Level.NONE
		}
	}

}
