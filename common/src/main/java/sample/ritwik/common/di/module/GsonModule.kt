package sample.ritwik.common.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides

import sample.ritwik.common.di.AppScope

/**
 * [Module] for providing [Gson].
 */
@Module
class GsonModule {

    /**
     * Provides the [Gson].
     *
     * @return New Instance of [Gson].
     */
    @AppScope
    @Provides
    fun providesGson(): Gson = GsonBuilder().setPrettyPrinting().create()

}
