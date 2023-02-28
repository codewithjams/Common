package sample.ritwik.app.di.application.module

import android.content.Context

import com.droidboi.common.utility.networkCallback.NetworkConnectivityManager

import com.droidboi.common.utility.networkCallback.impl.NetworkConnectivityManagerImpl

import com.droidboi.common.utility.resources.ResourceUtils

import com.droidboi.common.utility.resources.impl.ContextResourceUtils

import dagger.Module
import dagger.Provides

import sample.ritwik.app.di.application.scope.AppScope

@Module
class UtilityModule {

	@Provides
	@AppScope
	fun providesResourceUtils(context: Context): ResourceUtils = ContextResourceUtils(context)

	@Provides
	@AppScope
	fun providesNetworkConnectivityManager(context: Context): NetworkConnectivityManager =
		NetworkConnectivityManagerImpl(context)

}
