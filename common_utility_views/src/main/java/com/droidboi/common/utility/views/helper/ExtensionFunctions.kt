package com.droidboi.common.utility.views.helper

import android.app.Activity

import android.view.View

import android.view.inputmethod.InputMethodManager

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
