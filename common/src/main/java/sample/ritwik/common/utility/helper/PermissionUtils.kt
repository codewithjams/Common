package sample.ritwik.common.utility.helper

import android.app.Activity

import android.content.Context

import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat

import sample.ritwik.common.utility.constant.GROUP_PERMISSION_CODE

import javax.inject.Inject

/**
 * Utility Class to provide all the methods related to Permission.
 *
 * @param context [Activity]'s [Context] denoting the Request Code for a Group Permission Requested
 *   from the method [isPermissionGranted].
 * @author Ritwik Jamuar
 */
class PermissionUtils @Inject constructor(private val context: Context) {

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Checks whether a given [permission] is granted for this application or not.
     *
     * @param permission [String] denoting the Permission as one of the [String]s from
     *   [android.Manifest.permission].
     * @return true, if the method [ContextCompat.checkSelfPermission] for the given [permission]
     *   equals to [PackageManager.PERMISSION_GRANTED], else false.
     */
    fun isPermissionGranted(permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    /**
     * Requests the given [permissions].
     *
     *
     * Uses [ActivityCompat.requestPermissions] to request the given [permissions].
     *
     * @param permissions Multiple [String]s denoting the Permissions as [String] from
     *   [android.Manifest.permission].
     */
    fun requestPermissions(vararg permissions: String) = ActivityCompat.requestPermissions(
        context as Activity,
        permissions,
        GROUP_PERMISSION_CODE
    )

}
