package com.droidboi.common.utility.resources.di

import android.content.Context

import com.droidboi.common.utility.resources.ResourceUtils

import com.droidboi.common.utility.resources.impl.ResourceUtilsImpl

import dagger.Module
import dagger.Provides

/**
 * [Module] for [ResourceUtils].
 *
 * @author Ritwik Jamuar
 */
@Module
class ResourceModule {

	/**
	 * Provides the instance of [ResourceUtils].
	 *
	 * @param context Instance of Application's [Context].
	 * @return New Instance of [ResourceUtils].
	 */
	@Provides
	fun providesResourceUtils(context: Context): ResourceUtils = ResourceUtilsImpl(context)

}
