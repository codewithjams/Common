package com.droidboi.common.utility.permissions.di

import androidx.appcompat.app.AppCompatActivity

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
	 * @param activity Reference of [AppCompatActivity] on which this utility is dedicated to.
	 * @return New Instance of [PermissionUtils].
	 */
	@Provides
	fun providesPermissionUtils(activity: AppCompatActivity): PermissionUtils =
		PermissionUtils(activity)

}
