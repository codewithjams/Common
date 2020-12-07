package sample.ritwik.app.di.application.module

import android.app.Application

import android.content.Context

import dagger.Module
import dagger.Provides

import sample.ritwik.app.application.App

import sample.ritwik.common.di.AppScope

import sample.ritwik.common.di.module.CommonModule

import sample.ritwik.common.utility.constant.BASE_URL
import sample.ritwik.common.utility.constant.CACHE_DIRECTORY_NAME
import sample.ritwik.common.utility.constant.DATA_STORE_FILE_NAME

import javax.inject.Named

/**
 * [Module] for the whole application.
 *
 *
 * This [Module] is responsible for providing all the global dependants that this Application
 * may need.
 *
 *
 * This [Module] includes following [Module] annotated classes:
 * 1. [CommonModule]: To provide all the dependencies from 'common' Library.
 * 2. [RESTInterfaceModule]: To provide [sample.ritwik.app.component.network.RESTInterface].
 *
 *
 * Along with the dependencies on the above, this [Module] also provides following:
 * 1. Instance of [Application].
 * 2. Instance of Application's [Context].
 * 3. [String] as the name of Cache Directory,
 *   fulfilled to [sample.ritwik.common.di.module.CacheModule].
 * 4. [String] as the Base URL for [retrofit2.Retrofit],
 *   fulfilled to [sample.ritwik.common.di.module.RetrofitModule].
 * 5. [String] as the name of Data Store,
 *   fulfilled to [sample.ritwik.common.di.module.PersistenceModule].
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        CommonModule::class,
        RESTInterfaceModule::class
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
     * Provides the [String] as the name of Cache Directory.
     *
     * @return [String] denoting the name of Cache Directory.
     */
    @AppScope
    @Provides
    @Named(CACHE_DIRECTORY_NAME)
    fun providesCacheDirectoryName(): String = "cdjkcn"

    /**
     * Provides the [String] as the Base URL for [retrofit2.Retrofit].
     *
     *
     * Make sure to provide a URL with prefix 'http://' or 'https://',
     * otherwise [sample.ritwik.common.di.module.RetrofitModule] would encounter
     * [IllegalArgumentException].
     *
     * @return [String] denoting the Base URL.
     */
    @AppScope
    @Provides
    @Named(BASE_URL)
    fun providesBaseURL(): String = "https://www.google.com"

    /**
     * Provides the [String] as the File Name of Data Store.
     *
     * @return [String] denoting the File Name of Data Store.
     */
    @AppScope
    @Provides
    @Named(DATA_STORE_FILE_NAME)
    fun providesDataStoreFileName(): String = "dkjns"

}
