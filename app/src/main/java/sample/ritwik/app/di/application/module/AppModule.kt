package sample.ritwik.app.di.application.module

import android.app.Application

import android.content.Context

import dagger.Module
import dagger.Provides

import sample.ritwik.app.application.App

import sample.ritwik.app.di.application.scope.AppScope

import javax.inject.Named

/**
 * [Module] for the whole application.
 *
 * @author Ritwik Jamuar
 */
@Module(
	includes = [
		RepositoryModule::class,
		PicassoModule::class,
		UtilityModule::class
	]
)
class AppModule {

	/**
	 * Provides the [Application] as a type-casted instance of [App].
	 *
	 * @param app Instance of [App].
	 * @return Instance of [app] as [Application].
	 */
	@Provides
	fun providesApplication(app: App): Application = app

	/**
	 * Provides the [Context] from [App].
	 *
	 * @param app Instance of [App].
	 * @return Instance of [Context] from [App.getApplicationContext].
	 */
	@AppScope
	@Provides
	fun providesApplicationContext(app: App): Context = app.applicationContext

	/**
	 * Provides the [String] as the name of Cache Directory for storing cache of REST API Calls.
	 *
	 * @return [String] denoting the name of Cache Directory.
	 */
	@Provides
	@AppScope
	@Named(CACHE_DIRECTORY_NAME_REST)
	fun providesCacheDirectoryNameForREST(): String = "cdjkcn"

	/**
	 * Provides the [Long] as the size of the Cache File for storing cache of REST API Calls.
	 *
	 * @return [Long] as the size of Cache File.
	 */
	@Provides
	@AppScope
	@Named(CACHE_FILE_SIZE_REST)
	fun providesCacheFileSizeForREST(): Long = 10 * 1024 * 1024

	/**
	 * Provides the [String] as the name of Cache Directory for storing cache of Images.
	 *
	 * @return [String] denoting the name of Cache Directory.
	 */
	@Provides
	@AppScope
	@Named(CACHE_DIRECTORY_NAME_IMAGE)
	fun providesCacheDirectoryNameForImage(): String = "djbvce"

	/**
	 * Provides the [Long] as the size of the Cache File for storing cache of Images.
	 *
	 * @return [Long] as the size of Cache File.
	 */
	@Provides
	@AppScope
	@Named(CACHE_FILE_SIZE_IMAGE)
	fun providesCacheFileSizeForImage(): Long = 100 * 1024 * 1024

	/**
	 * Provides the [String] as the Base URL for [retrofit2.Retrofit].
	 *
	 *
	 * Make sure to provide a URL with prefix 'http://' or 'https://',
	 * otherwise [sample.ritwik.app.di.application.module.RESTModule] would encounter
	 * [IllegalArgumentException].
	 *
	 * @return [String] denoting the Base URL.
	 */
	@Provides
	@AppScope
	@Named(BASE_URL)
	fun providesBaseURL(): String = "https://www.google.com"

	/**
	 * Provides the [String] as the File Name of Data Store.
	 *
	 * @return [String] denoting the File Name of Data Store.
	 */
	@Provides
	@AppScope
	@Named(DATA_STORE_FILE_NAME)
	fun providesDataStoreFileName(): String = "dkjns"

}
