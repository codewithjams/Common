package com.droidboi.common.utility.views.di

import android.content.Context

import com.droidboi.common.utility.views.helper.ViewUtils

import dagger.Module
import dagger.Provides

/**
 * [Module] for [ViewUtils].
 *
 * @author Ritwik Jamuar
 */
@Module
class ViewUtilsModule {

	/**
	 * Provides the instance of [ViewUtils].
	 *
	 * @param context Instance of Application's [Context].
	 * @return New Instance of [ViewUtils].
	 */
	@Provides
	fun providesViewUtils(context: Context): ViewUtils = ViewUtils(context)

}
