package sample.ritwik.common.di.module

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides
import retrofit2.converter.moshi.MoshiConverterFactory

import sample.ritwik.common.di.AppScope

/**
 * [Module] of [Moshi].
 *
 * @author Ritwik Jamuar
 */
@Module
class MoshiModule {

    /**
     * Provides the new instance of [MoshiConverterFactory].
     *
     * @param moshi Instance of [Moshi] from [providesMoshi].
     * @return New Instance of [MoshiConverterFactory].
     */
    @AppScope
    @Provides
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
        MoshiConverterFactory.create(moshi)

    /**
     * Provides the new instance of [Moshi].
     *
     * @return New Instance of [Moshi].
     */
    @AppScope
    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()

}
