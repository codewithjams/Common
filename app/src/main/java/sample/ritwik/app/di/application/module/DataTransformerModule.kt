package sample.ritwik.app.di.application.module

import com.droidboi.common.dataTransformer.DataTransformer

import com.droidboi.common.dataTransformer.moshi.MoshiDataTransformer

import com.squareup.moshi.Moshi

import dagger.Module
import dagger.Provides

import sample.ritwik.app.di.application.scope.AppScope

@Module(
	includes = [
		MoshiModule::class
	]
)
class DataTransformerModule {

	@Provides
	@AppScope
	fun providesDataTransformer(moshi: Moshi): DataTransformer = MoshiDataTransformer(moshi)

}
