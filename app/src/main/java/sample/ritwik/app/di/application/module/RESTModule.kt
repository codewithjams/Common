package sample.ritwik.app.di.application.module

import android.content.Context

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides

import okhttp3.Cache
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit

import retrofit2.converter.moshi.MoshiConverterFactory

import sample.ritwik.app.BuildConfig

import sample.ritwik.app.repository.rest.RESTInterface
import sample.ritwik.app.repository.rest.CacheInterceptor

import sample.ritwik.app.di.application.scope.AppScope

import java.io.File

import javax.inject.Named

/**
 * [Module] for providing [RESTInterface].
 *
 * @author Ritwik Jamuar
 */
@Module
class RESTModule {

	/**
	 * Provides the instance of [RESTInterface].
	 *
	 * @param retrofit Instance of [Retrofit].
	 *
	 * @return New Instance of [RESTInterface].
	 */
	@AppScope
	@Provides
	fun providesRESTInterface(retrofit: Retrofit): RESTInterface =
		retrofit.create(RESTInterface::class.java)

	/**
	 * Provides the instance of [Retrofit].
	 *
	 * @param client Instance of [OkHttpClient].
	 * @param moshiConverterFactory Instance of [MoshiConverterFactory].
	 * @param baseURL [String] denoting the Base URL End-Point.
	 *
	 * @return New Instance of [Retrofit].
	 */
	@AppScope
	@Provides
	fun providesRetrofit(
		@Named(COMPONENT_REST) client: OkHttpClient,
		@Named(COMPONENT_REST) moshiConverterFactory: MoshiConverterFactory,
		@Named(BASE_URL) baseURL: String
	): Retrofit = Retrofit.Builder().apply {
		client(client)
		addConverterFactory(moshiConverterFactory)
		baseUrl(baseURL)
	}.build()

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
	@Named(COMPONENT_REST)
	fun providesOkHttpClient(
		@Named(COMPONENT_REST) cache: Cache,
		@Named(COMPONENT_REST) loggingInterceptor: HttpLoggingInterceptor,
		@Named(COMPONENT_REST) cacheInterceptor: CacheInterceptor
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
	@Named(COMPONENT_REST)
	fun providesCache(
		@Named(COMPONENT_REST) file: File,
		@Named(CACHE_FILE_SIZE_REST) size: Long
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
	@Named(COMPONENT_REST)
	fun providesCacheFile(
		context: Context,
		@Named(CACHE_DIRECTORY_NAME_REST) cacheDirectoryName: String
	) = File(context.cacheDir, cacheDirectoryName)

	/**
	 * Provides the [CacheInterceptor].
	 *
	 * @return New Instance of [CacheInterceptor].
	 */
	@Provides
	@AppScope
	@Named(COMPONENT_REST)
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
	@Named(COMPONENT_REST)
	fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
		android.util.Log.e("REST", message)
	}.apply {
		level = when {
			BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
			else -> HttpLoggingInterceptor.Level.NONE
		}
	}

	/**
	 * Provides the new instance of [MoshiConverterFactory].
	 *
	 * @param moshi Instance of [Moshi].
	 *
	 * @return New Instance of [MoshiConverterFactory].
	 */
	@AppScope
	@Provides
	@Named(COMPONENT_REST)
	fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
		MoshiConverterFactory.create(moshi)

}
