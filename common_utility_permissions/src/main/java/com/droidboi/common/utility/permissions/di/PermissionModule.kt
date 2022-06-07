package com.droidboi.common.utility.permissions.di

import android.content.Context

import com.droidboi.common.utility.permissions.helper.PermissionUtils

import dagger.Module
import dagger.Provides

/**
 * [Module] for [PermissionUtils].
 *
 * @author Ritwik Jamuar
 */
@Module
class PermissionModule {

	/**
	 * Provides the instance of [PermissionUtils].
	 *
	 * @param context Instance of Application's [Context].
	 * @return New Instance of [PermissionUtils].
	 */
	@Provides
	fun providesPermissionUtils(context: Context): PermissionUtils = PermissionUtils(context)

}
