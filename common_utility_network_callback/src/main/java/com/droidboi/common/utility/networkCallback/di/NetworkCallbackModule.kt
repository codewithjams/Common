package com.droidboi.common.utility.networkCallback.di

import android.content.Context

import android.net.ConnectivityManager

import android.telephony.TelephonyManager

import com.droidboi.common.utility.networkCallback.helper.NetworkUtils

import dagger.Module
import dagger.Provides

/**
 * [Module] for [NetworkUtils].
 *
 * @author Ritwik Jamuar
 */
@Module
class NetworkCallbackModule {

	/**
	 * Provides the instance of [NetworkUtils].
	 *
	 * @param context Instance of Application's [Context].
	 * @return New Instance of [NetworkUtils].
	 */
	@Provides
	fun providesNetworkUtils(context: Context): NetworkUtils = NetworkUtils(
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
		context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
	)

}
