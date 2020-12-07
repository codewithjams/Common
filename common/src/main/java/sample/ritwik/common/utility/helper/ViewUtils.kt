package sample.ritwik.common.utility.helper

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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

/**
 * Hides the Soft-Input Keyboard from the [Activity].
 *
 * @param view [View] on which the Soft-Input Keyboard resides.
 */
fun Activity.hideSoftInput(view: View? = null) {
    try {
        with(getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager) {
            hideSoftInputFromWindow(
                if (view == null) {
                    currentFocus!!.windowToken
                } else {
                    view.windowToken
                },
                0
            )
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
