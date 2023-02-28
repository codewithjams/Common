package sample.ritwik.app.di.application.module

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides

import sample.ritwik.app.di.application.scope.AppScope

/**
 * [Module] of [Moshi].
 *
 * @author Ritwik Jamuar
 */
@Module
class MoshiModule {

	/**
	 * Provides the new instance of [Moshi].
	 *
	 * @return New Instance of [Moshi].
	 */
	@AppScope
	@Provides
	fun providesMoshi(): Moshi = Moshi.Builder().build()

}
