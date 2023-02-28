package com.droidboi.common.utility.resources.impl

import android.content.Context

import android.content.res.Resources

import android.os.Build

import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

import com.droidboi.common.utility.resources.ResourceUtils

import java.io.IOException
import java.io.InputStream

/**
 * [ResourceUtils] implemented using [Context]
 *
 * @param context [Context] from which the resources will be accessed.
 * @author Ritwik Jamuar
 */
class ContextResourceUtils(private val context: Context) : ResourceUtils {

	/*-------------------------------------- Public Methods --------------------------------------*/

	override fun getString(@StringRes resourceID: Int) = context.getString(resourceID)

	override fun getString(@StringRes resourceID: Int, vararg formatArguments: Any?): String =
		context.getString(resourceID, formatArguments)

	override fun getInteger(@IntegerRes resourceID: Int): Int =
		context.resources.getInteger(resourceID)

	override fun getColor(@ColorRes resourceID: Int, theme: Resources.Theme?) =
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			context.resources.getColor(resourceID, theme)
		} else {
			context.resources.getColor(resourceID)
		}

	@Throws(IOException::class)
	override fun getAsset(fileName: String): InputStream = context.resources.assets.open(fileName)

}
