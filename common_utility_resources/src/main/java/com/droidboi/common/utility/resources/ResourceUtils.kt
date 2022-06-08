package com.droidboi.common.utility.resources

import android.content.res.Resources

import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

import java.io.IOException
import java.io.InputStream

/**
 * This is a unified resource provider abstracted away the common methods of
 * gathering the resources.
 *
 *
 * Marks any implementation as a provider of various Android Resources.
 *
 * @author Ritwik Jamuar
 */
interface ResourceUtils {

	/**
	 * Provides the String Resources for the given [resourceID].
	 *
	 * @param resourceID [Int] as [StringRes] denoting the Resource ID of String.
	 * @return [String] denoting the value of [resourceID].
	 */
	fun getString(@StringRes resourceID: Int): String

	/**
	 * Provides the String Resources for the given [resourceID].
	 *
	 * @param resourceID [Int] as [StringRes] denoting the Resource ID of String.
	 * @param formatArguments Multiple Format Arguments of [Any] Data/Class Type.
	 * @return [String] denoting the value of [resourceID].
	 */
	fun getString(@StringRes resourceID: Int, vararg formatArguments: Any?): String

	/**
	 * Provides the Integer Resources for the given [resourceID].
	 *
	 * @param resourceID [Int] as [IntegerRes] denoting the Resource ID of Integer.
	 * @return [Int] denoting the value of [resourceID].
	 */
	fun getInteger(@IntegerRes resourceID: Int): Int

	/**
	 * Provides the Color Resources for the given [resourceID].
	 *
	 * @param resourceID [Int] as [ColorRes] denoting the Resource ID of Color.
	 * @return [Int] denoting the color value of [resourceID].
	 */
	fun getColor(@ColorRes resourceID: Int, theme: Resources.Theme? = null): Int

	/**
	 * Provides the supplied [fileName] as [InputStream].
	 *
	 * @param fileName [String] denoting the name of the file contained in 'assets' Resource Folder.
	 * @return Instance of [InputStream].
	 * @throws IOException This exception is thrown when the [fileName] supplied does not exist
	 *   in the 'assets' Resource Folder, or there was some trouble opening the file.
	 */
	@Throws(IOException::class)
	fun getAsset(fileName: String): InputStream

}
