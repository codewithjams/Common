package com.droidboi.common.utility.permissions.helper

import android.app.Activity

import android.content.pm.PackageManager

import android.os.Build

import androidx.activity.result.contract.ActivityResultContracts

import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat

import com.droidboi.common.utility.permissions.constant.GROUP_PERMISSION_CODE

import javax.inject.Inject

/**
 * Utility Class to provide all the methods related to Permission.
 *
 * @param activity [Activity] for which this utility is dedicated to.
 * @author Ritwik Jamuar
 */
class PermissionUtils @Inject constructor(private val activity: AppCompatActivity) {

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Checks whether a given [permission] is granted for this application or not.
	 *
	 * @param permission [String] denoting the Permission as one of the [String]s from
	 *   [android.Manifest.permission].
	 * @return true, if the method [ActivityCompat.checkSelfPermission] for the given [permission]
	 *   equals to [PackageManager.PERMISSION_GRANTED], else false.
	 */
	fun isPermissionGranted(permission: String): Boolean =
		ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

	/**
	 * Requests the given [permissions].
	 *
	 *
	 * Example use case: -
	 * ```
	 * // Within an activity
	 * val permissionUtils = PermissionUtils(this)
	 *
	 * permissionUtils.requestPermissions(
	 *     Manifest.permission.CAMERA,
	 *     Manifest.permission.ACCESS_COARSE_LOCATION,
	 *     Manifest.permission.ACCESS_FINE_LOCATION
	 * )
	 *
	 * // Override the method: onRequestPermissionsResult() of AppCompatActivity.
	 * // For this, use GROUP_PERMISSION_CODE available
	 * override fun onRequestPermissionsResult(
	 *     requestCode: Int,
	 *     permissions: Array<String>,
	 *     grantResults: IntArray
	 * ) {
	 *     when(requestCode) {
	 *         GROUP_PERMISSION_CODE -> {
	 *             // If request is cancelled, the result arrays are empty.
	 *              if (
	 *                  (grantResults.isNotEmpty() &&
	 *                  grantResults[0] == PackageManager.PERMISSION_GRANTED)
	 *              ) {
	 *                  // Permission is granted. Continue the action or workflow
	 *                  // in your app.
	 *              } else {
	 *                  // Explain to the user that the feature is unavailable because
	 *                  // the features requires a permission that
	 *                  // the user has denied.
	 *                  // At the same time, respect the user's decision.
	 *                  // Don't link to system settings in an effort
	 *                  // to convince the user to change their decision.
	 *              }
	 *         }
	 *
	 *         // Add other 'when' lines to check for other permissions
	 *         // this app might request.
	 *         else -> {
	 *             // Ignore all other requests.
	 *         }
	 *     }
	 * }
	 *
	 * ```
	 *
	 * @param permissions Multiple [String]s denoting the Permissions as [String] from
	 *   [android.Manifest.permission].
	 * @see GROUP_PERMISSION_CODE
	 */
	fun requestPermissions(vararg permissions: String) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			activity.requestPermissions(permissions, GROUP_PERMISSION_CODE)
		} else {
			ActivityCompat.requestPermissions(activity, permissions, GROUP_PERMISSION_CODE)
		}
	}

	/**
	 * Request a [permission].
	 *
	 *
	 * Example use case: -
	 * ```
	 * // Within an activity.
	 * val permissionUtils = PermissionUtils(this)
	 *
	 * permissionUtils.requestPermission(Manifest.permission.CAMERA) { isGranted ->
	 *     if(isGranted) {
	 *         // Permission is granted. Continue the action or workflow in your app.
	 *     } else {
	 *         // Explain to the user that the feature is unavailable because
	 *         // the features requires a permission that
	 *         // the user has denied.
	 *         // At the same time, respect the user's decision.
	 *         // Don't link to system settings in an effort
	 *         // to convince the user to change their decision.
	 *     }
	 * }
	 * ```
	 *
	 * @param permission [String] from [android.Manifest.permission] denoting the Permission
	 *   user is requesting to user.
	 * @param callback Lambda Expression to dispatch result whether the Permission was given by user
	 *   or not.
	 */
	fun requestPermission(
		permission: String,
		callback: (Boolean) -> Unit
	) {
		activity.registerForActivityResult(
			ActivityResultContracts.RequestPermission()
		) { isGranted ->
			callback.invoke(isGranted)
		}.launch(permission)
	}

}
