package com.droidboi.common.utility.networkCallback.di

import android.content.Context

import com.droidboi.common.utility.networkCallback.NetworkConnectivityManager

import com.droidboi.common.utility.networkCallback.impl.NetworkConnectivityManagerImpl

import dagger.Module
import dagger.Provides

/**
 * [Module] for [NetworkConnectivityManager].
 *
 * @author Ritwik Jamuar
 */
@Module
class NetworkCallbackModule {

	/**
	 * Provides the instance of [NetworkConnectivityManager].
	 *
	 * @param context Instance of Application's [Context].
	 * @return New Instance of [NetworkConnectivityManager].
	 */
	@Provides
	fun providesNetworkConnectivityManager(context: Context): NetworkConnectivityManager =
		NetworkConnectivityManagerImpl(context)

}
