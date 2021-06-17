package com.droidboi.common.utility.di

import android.content.Context

import com.droidboi.common.utility.helper.ResourceUtils
import com.droidboi.common.utility.helper.ViewUtils

import com.droidboi.common.utility.networkCallback.di.NetworkCallbackModule

import com.droidboi.common.utility.permissions.di.PermissionModule

import dagger.Module
import dagger.Provides

/**
 * [Module] of all the Utility Classes.
 *
 *
 * This [Module] provides instances of these three Utility Classes:
 * 1. [com.droidboi.common.utility.networkCallback.helper.NetworkUtils]: Utility Class to provide
 * proper update on Network from [NetworkCallbackModule].
 * 3. [com.droidboi.common.utility.permissions.helper.PermissionUtils]: Utility Class to provide
 * all the methods related to Permission, from [PermissionModule].
 * 3. [ResourceUtils]: Utility Class to provide all types of Resources from Android OS
 * for this application.
 * 4. [ViewUtils]: Utility Class to provide some common functionality around Views.
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        NetworkCallbackModule::class,
        PermissionModule::class
    ]
)
class UtilityModule {

    /**
     * Provides the instance of [ResourceUtils].
     *
     * @param context Instance of Application's [Context].
     * @return New Instance of [ResourceUtils].
     */
    @Provides
    fun providesResourceUtils(context: Context): ResourceUtils = ResourceUtils(context)

    /**
     * Provides the instance of [ViewUtils].
     *
     * @param context Instance of Application's [Context].
     * @return New Instance of [ViewUtils].
     */
    @Provides
    fun providesViewUtils(context: Context): ViewUtils = ViewUtils(context)

}
