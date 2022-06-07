package com.droidboi.common.utility.views.helper

import android.app.Activity

import android.content.Context
import android.content.Intent

import android.content.res.Resources

import android.os.Build

import android.util.DisplayMetrics

import android.view.View

import android.view.inputmethod.InputMethodManager

import android.widget.Toast

import androidx.annotation.ColorRes

import androidx.appcompat.app.AppCompatActivity

import androidx.core.content.ContextCompat

import androidx.lifecycle.LifecycleCoroutineScope

import kotlinx.coroutines.delay

/**
 * Shows the Soft-Input Keyboard from the [Activity].
 *
 * @param scope [LifecycleCoroutineScope] to be used for implementing the [delay]
 *   while showing the Keyboard.
 * @param view The currently focused [View], which would like to receive Soft-Input Keyboard Input.
 * @param delay Time in milli-seconds marks the delay while showing the Keyboard.
 */
fun Activity.showSoftInput(scope: LifecycleCoroutineScope, view: View, delay: Long = 500L) {
	scope.launchWhenResumed {
		try {
			view.requestFocus()
			delay(delay)
			(getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let { manager ->
				with(manager) {
					showSoftInput(view, 0)
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
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

/**
 * Shows [Toast] in the UI.
 *
 * @param message [String] denoting the message to be displayed.
 * @param durationLength [Int] as either one of [Toast.LENGTH_SHORT] or [Toast.LENGTH_LONG] denoting
 *   the duration of [Toast] to be displayed.
 */
fun Context.showToast(message: String, durationLength: Int = Toast.LENGTH_SHORT) {

	val isAcceptableDurationLength =
		Toast.LENGTH_SHORT == durationLength || Toast.LENGTH_LONG == durationLength

	// Halt the further execution if the durationLength supplied is not in acceptable range,
	// or the message provided is empty.
	if (!isAcceptableDurationLength || message.isEmpty()) return

	Toast.makeText(this, message, durationLength).show()

}

/**
 * Fetches the [Int] as the Color Resource safely.
 *
 * @param colorID [Int] as [ColorRes] denoting the color to be fetched.
 * @param theme Instance of [Resources.Theme] which is used to style the color attributes.
 * @return A single color value in the form `0xAARRGGBB` if the resource is found,
 * otherwise the [ColorRes] value of [android.R.color.transparent].
 */
fun Context.getColorSafely(@ColorRes colorID: Int, theme: Resources.Theme? = null): Int = try {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		resources.getColor(colorID, theme)
	} else {
		ContextCompat.getColor(this, colorID)
	}
} catch (e: Resources.NotFoundException) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
		resources.getColor(android.R.color.transparent, theme)
	} else {
		ContextCompat.getColor(this, android.R.color.transparent)
	}
}

/**
 * From a [Boolean] value, decide the [View]'s visibility.
 *
 * @return If `true`, then [View.VISIBLE] else [View.GONE].
 */
fun Boolean.toViewVisibility(): Int = if (this) {
	View.VISIBLE
} else {
	View.GONE
}

/**
 * Converts a given [Float] as `dp` value of size to `px` value of size.
 *
 * @param context Instance of [Context] used to get the density of the screen, and converts [Float]
 *   as `dp` value to `px` value accordingly.
 * @return [Float] as the `px` value.
 */
fun Float.dpToPixel(context: Context): Float =
	this * context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_MEDIUM

/**
 * Performs navigation from the current [AppCompatActivity] to another [AppCompatActivity].
 *
 *
 * Examples:-
 * <br/><br/>
 * #### Use Case 1: Simply navigate to another Activity while finishing existing Activity.
 * ```
 * navigate(SomeActivity::class.java)
 * ```
 * <br/><br/>
 * #### Use Case 2: Navigate to another Activity with custom intent attributes.
 * ```
 * navigate(SomeActivity::class.java) { intent, activity ->
 *     intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
 *     intent.putExtra("Some Key", "Some Value")
 * }
 * ```
 *
 * @param T Any [AppCompatActivity] to which we wish to navigate.
 * @param classToNavigate Reference of [Class] of [T] denoting the Class of Activity
 *   to which we wish to navigate.
 * @param isCurrentActivityFinishing [Boolean] flag to mark whether the current [AppCompatActivity]
 *   is to be finished after the navigation or not.
 * @param intentModifier Lambda Expression to be used for modifying thhe intent,
 *   so that the changes are applied before performing navigation
 *   to destination [AppCompatActivity].
 */
fun <T : AppCompatActivity> AppCompatActivity.navigate(
	classToNavigate: Class<T>,
	isCurrentActivityFinishing: Boolean = false,
	intentModifier: ((Intent, AppCompatActivity) -> Unit)? = null
) {
	val intent = Intent(this, classToNavigate)
	intentModifier?.invoke(intent, this)
	startActivity(intent)
	if (isCurrentActivityFinishing) {
		finish()
	}
}
