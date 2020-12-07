package sample.ritwik.common.utility.helper

import android.content.Context

import android.content.res.Resources

import android.os.Build

import androidx.annotation.ColorRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

import java.io.IOException
import java.io.InputStream

import javax.inject.Inject

/**
 * Utility Class to provide all types of Resources from Android OS for this application.
 *
 * @param context [Context] from which the resources will be accessed.
 * @author Ritwik Jamuar
 */
class ResourceUtils @Inject constructor(private val context: Context) {

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Provides the String Resources for the given [resourceID].
     *
     * @param resourceID [Int] as [StringRes] denoting the Resource ID of String.
     * @return [String] denoting the value of [resourceID].
     */
    fun getString(@StringRes resourceID: Int) = context.getString(resourceID)

    /**
     * Provides the String Resources for the given [resourceID].
     *
     * @param resourceID [Int] as [StringRes] denoting the Resource ID of String.
     * @param formatArguments Multiple Format Arguments of [Any] Data/Class Type.
     * @return [String] denoting the value of [resourceID].
     */
    fun getString(@StringRes resourceID: Int, vararg formatArguments: Any?): String =
        context.getString(resourceID, formatArguments)

    /**
     * Provides the Integer Resources for the given [resourceID].
     *
     * @param resourceID [Int] as [IntegerRes] denoting the Resource ID of Integer.
     * @return [Int] denoting the value of [resourceID].
     */
    fun getInteger(@IntegerRes resourceID: Int): Int = context.resources.getInteger(resourceID)

    /**
     * Provides the Color Resources for the given [resourceID].
     *
     * @param resourceID [Int] as [ColorRes] denoting the Resource ID of Color.
     * @return [Int] denoting the color value of [resourceID].
     */
    fun getColor(@ColorRes resourceID: Int, theme: Resources.Theme? = null) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(resourceID, theme)
        } else {
            context.resources.getColor(resourceID)
        }

    /**
     * Provides the supplied [fileName] as [InputStream].
     *
     * @param fileName [String] denoting the name of the file contained in 'assets' Resource Folder.
     * @return Instance of [InputStream].
     * @throws IOException This exception is thrown when the [fileName] supplied does not exist
     *   in the 'assets' Resource Folder, or there was some trouble opening the file.
     */
    @Throws(IOException::class)
    fun getAsset(fileName: String): InputStream = context.resources.assets.open(fileName)

}
