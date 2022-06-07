package com.droidboi.common.utility.di

import com.droidboi.common.utility.networkCallback.di.NetworkCallbackModule

import com.droidboi.common.utility.permissions.di.PermissionModule

import com.droidboi.common.utility.resources.di.ResourceModule

import com.droidboi.common.utility.views.di.ViewUtilsModule

import dagger.Module

/**
 * [Module] of all the Utility Classes.
 *
 *
 * This [Module] provides instances of these three Utility Classes:
 * 1. [com.droidboi.common.utility.networkCallback.helper.NetworkUtils]: Utility Class to provide
 * proper update on Network from [NetworkCallbackModule].
 * 3. [com.droidboi.common.utility.permissions.helper.PermissionUtils]: Utility Class to provide
 * all the methods related to Permission, from [PermissionModule].
 * 3. [com.droidboi.common.utility.resources.helper.ResourceUtils]: Utility Class to provide
 * all types of Resources from Android OS for this application, from [ResourceModule].
 * 4. [com.droidboi.common.utility.views.helper.ViewUtils]: Utility Class to provide
 * some common functionality around Views, from [ViewUtilsModule].
 *
 * @author Ritwik Jamuar
 */
@Module(
	includes = [
		NetworkCallbackModule::class,
		PermissionModule::class,
		ResourceModule::class,
		ViewUtilsModule::class
	]
)
class UtilityModule
