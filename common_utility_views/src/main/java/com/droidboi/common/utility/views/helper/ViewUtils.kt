package com.droidboi.common.utility.views.helper

import android.content.Context

import javax.inject.Inject

/**
 * Utility Class to provide some common functionality around Views.
 *
 * @param context Reference of [Context] to access all the UI related methods and constants.
 * @author Ritwik Jamuar
 */
class ViewUtils @Inject constructor(private val context: Context) {

	/*------------------------------------- Member Variables -------------------------------------*/

	/**
	 * Reference of [String] to store the value of Screen Density.
	 */
	val screenDensity: String by lazy { providesScreenDensity() }

	/*-------------------------------------- Private Methods -------------------------------------*/

	/**
	 * Uses [context] to access [android.util.DisplayMetrics] and provide the Screen Density.
	 *
	 * @return If there was some problem occurred during fetching the Screen Density from [context],
	 *   the value returned would be 420, else the value of Screen Density from
	 *   [android.util.DisplayMetrics].
	 */
	private fun providesScreenDensity(): String = try {
		"${context.resources.displayMetrics.density}"
	} catch (e: Exception) {
		"420"
	}

}
