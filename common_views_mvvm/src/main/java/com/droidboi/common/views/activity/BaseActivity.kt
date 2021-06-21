package com.droidboi.common.views.activity

import android.Manifest

import android.content.DialogInterface

import android.content.pm.PackageManager

import android.os.Bundle

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope

import com.droidboi.common.data.ui.PopUpData

import com.droidboi.common.mvvm.data.ErrorData

import com.droidboi.common.mvvm.model.BaseModel

import com.droidboi.common.mvvm.utility.*

import com.droidboi.common.mvvm.viewModel.BaseViewModel

import com.droidboi.common.utility.networkCallback.constant.NETWORK_ACTION_CHANGED
import com.droidboi.common.utility.networkCallback.constant.NETWORK_ACTION_NONE
import com.droidboi.common.utility.networkCallback.constant.NETWORK_PERMISSION_REQUIRED

import com.droidboi.common.utility.networkCallback.data.NetworkData
import com.droidboi.common.utility.networkCallback.data.NetworkType

import com.droidboi.common.utility.networkCallback.helper.NetworkUtils

import com.droidboi.common.utility.permissions.constant.GROUP_PERMISSION_CODE

import com.droidboi.common.utility.permissions.helper.PermissionUtils

import com.droidboi.common.views.R

import kotlinx.coroutines.flow.collect

/**
 * Abstract [AppCompatActivity] to contain all the common methods related to setting up the UI.
 *
 * @param Model [BaseModel] as the Model/UI Data of this [AppCompatActivity].
 * @param ViewModel [BaseViewModel] as the ViewModel of this [AppCompatActivity].
 * @param Binding Any Class representing the View/Data Binding Class
 *   of this [AppCompatActivity].
 * @author Ritwik Jamuar
 */
abstract class BaseActivity<
        Model : BaseModel,
        ViewModel : BaseViewModel<Model>,
        Binding : Any
        > : AppCompatActivity() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [Binding] to control the Views under it.
     */
    protected lateinit var binding: Binding

    /**
     * Reference of [ViewModel] to notify any event from here to it as well as observing any changes.
     */
    protected val viewModel: ViewModel by lazy { provideViewModel() }

    /**
     * Reference of [NetworkUtils] to get updates on change in Network Connectivity.
     */
    protected abstract val networkUtils: NetworkUtils

    /**
     * Reference of [PermissionUtils] to check and request permissions.
     */
    private lateinit var permissionUtils: PermissionUtils

    /*-------------------------------------- View Listeners --------------------------------------*/

    /**
     * [DialogInterface.OnClickListener] for handling click on [AlertDialog] used for
     * asking permission to Read State of the Phone.
     */
    private val dialogClickListener = DialogInterface.OnClickListener { _, _ ->
        if (!permissionUtils.isPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {
            permissionUtils.requestPermissions(Manifest.permission.READ_PHONE_STATE)
        }
    }

    /*---------------------------------------- Observers -----------------------------------------*/

    /**
     * [Observer] of [BaseViewModel.uiLiveData].
     */
    private val uiObserver = Observer<Model> { data ->
        processUpdateOnUIData(data)
    }

    /*------------------------------------ Activity Callbacks ------------------------------------*/

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setUp()
        extractArguments()
        initialize()
        attachObservers()
    }

    override fun onStart() {
        super.onStart()
        networkUtils.registerCallbacks()
        dismissPopUpDialog()
    }

    override fun onPause() {
        super.onPause()
        dismissPopUpDialog()
    }

    override fun onDestroy() {
        super.onDestroy()
        networkUtils.unRegisterCallbacks()
        cleanUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        // Halt the further execution and execute this method from super class.
        // when the request code does not match the code found from PermissionUtils.
        if (requestCode != GROUP_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        // At this stage, the request code equals the 'GROUP_PERMISSION_CODE'.

        // Now check whether the Permissions Granted is empty or not.
        if (grantResults.isNotEmpty() && permissions.isNotEmpty()) {

            // At this stage, Permission is granted/declined by the User on Runtime.

            // So check whether the Permission is granted or denied and handle accordingly.
            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED

            when (permissions[0]) {

                // This is a required permission to be granted by User for getting update on
                // Network Connectivity through NetworkUtils.
                Manifest.permission.READ_PHONE_STATE -> onReadPhoneStatePermission(isGranted)

                // Since it's any other Permission, so let it be handled by the super class.
                else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }

        } else {

            // At this stage, either the Grant Results or Permissions are not populated.
            // So execute this method from super class.
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }

    }

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Provides the [ViewModel] of this [BaseActivity].
     *
     * @return Current Instance of [viewModel].
     */
    fun getVM(): ViewModel = viewModel

    /*-------------------------------------- Private Methods -------------------------------------*/

    /**
     * Performs setting-up all the components of this [android.app.Activity].
     */
    private fun setUp() {
        setUpView()
        setUpViewModel()
        setUpPermissionUtils()
        setUpNetworkUtils()
    }

    /**
     * Sets up the [binding].
     */
    private fun setUpView() {
        binding = provideBinding()
    }

    /**
     * Sets up the [viewModel].
     */
    private fun setUpViewModel() {
        viewModel.uiLiveData.observe(this, uiObserver)
    }

    /**
     * Sets up the [permissionUtils]
     */
    private fun setUpPermissionUtils() {
        permissionUtils = PermissionUtils(this)
    }

    /**
     * Sets up the [networkUtils].
     */
    private fun setUpNetworkUtils() {
        lifecycleScope.launchWhenStarted {
            networkUtils.networkFlow.collect { networkData ->
                processUpdateOnNetwork(networkData)
            }
        }
    }

    /**
     * Handles the change in [Model] propagated from [viewModel].
     *
     * @param uiData Modified [Model].
     */
    private fun processUpdateOnUIData(uiData: Model): Unit = when (uiData.action) {

        ACTION_PROGRESS_BAR -> with(uiData.progressData) {
            if (showProgress) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        ACTION_ERROR -> showError(uiData.errorData)

        ACTION_POP_UP -> showPopUpDialog(uiData.popUpData)

        ACTION_UPDATE_UI -> onUIDataChanged(uiData)

        ACTION_NONE -> Unit

        else -> onAction(uiData)

    }

    /**
     * Handles the changes in [NetworkData] propagated from [networkUtils].
     *
     * @param networkData Modified [NetworkData].
     */
    private fun processUpdateOnNetwork(networkData: NetworkData): Unit = when (networkData.action) {

        NETWORK_ACTION_NONE -> Unit

        NETWORK_ACTION_CHANGED -> onNetworkChanged(
            networkData.isNetworkAvailable,
            networkData.networkType
        )

        NETWORK_PERMISSION_REQUIRED -> askPhoneStatePermission()

        else -> Unit

    }

    /**
     * Asks User for permitting access to Phone State, which is conveyed to user
     * through [AlertDialog].
     */
    private fun askPhoneStatePermission() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage(R.string.no_phone_state_permission)
            .setPositiveButton(R.string.ok, dialogClickListener)
            .create()
    }

    /*------------------------------------- Protected Methods ------------------------------------*/

    /**
     * Handles the permission [Manifest.permission.READ_PHONE_STATE].
     *
     * @param isGranted [Boolean] Flag marking whether user has granted or denied the permission.
     */
    protected fun onReadPhoneStatePermission(isGranted: Boolean) {

        // If the permission to Read Phone State is not granted by the User, prompt User
        // for the Permission again and halt the further execution.
        if (!isGranted) {
            askPhoneStatePermission()
            return
        }

        // At this stage, the Permission to Read Phone State is granted by the User.
        // So, notify the NetworkUtils that Permission is granted and tell it to
        // register the callbacks.
        with(networkUtils) {
            markReadingPhoneStatePermission(true)
            registerCallbacks()
        }

    }

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Provides the [ViewModel] of this [AppCompatActivity].
     *
     * @return New Instance of [ViewModel].
     */
    protected abstract fun provideViewModel(): ViewModel

    /**
     * Provides the [Binding] for the [binding].
     *
     * @return New Instance of [Binding].
     */
    protected abstract fun provideBinding(): Binding

    /**
     * Use Dependency Injection to inject the required dependencies into this [BaseActivity].
     */
    protected abstract fun inject()

    /**
     * Tells this [BaseActivity] to show the Loading.
     */
    protected abstract fun showLoading()

    /**
     * Tells this [BaseActivity] to hide the Loading.
     */
    protected abstract fun hideLoading()

    /**
     * Shows the Pop-up Dialog.
     *
     * @param popUpData Instance of [PopUpData] denoting the data for the Pop-up Window.
     */
    protected abstract fun showPopUpDialog(popUpData: PopUpData)

    /**
     * Hides the Pop-up Dialog.
     */
    protected abstract fun dismissPopUpDialog()

    /**
     * Tells this [BaseActivity] to show the Error.
     */
    protected abstract fun showError(errorData: ErrorData)

    /**
     * Tells this [BaseActivity] to perform extracting Data from it's [Bundle] arguments
     * from the method [AppCompatActivity.getIntent].
     */
    protected abstract fun extractArguments()

    /**
     * Tells this [BaseActivity] to initialize it's components.
     */
    protected abstract fun initialize()

    /**
     * Tells this [BaseActivity] to attach [Observer] of any [androidx.lifecycle.LiveData].
     */
    protected abstract fun attachObservers()

    /**
     * Notifies this [BaseActivity] of any change in the State of the Network.
     *
     * @param isNetworkAvailable [Boolean] Flag marking whether the Network is available or not.
     * @param networkType Instance of [NetworkType] denoting the Type of Network connectivity
     *   based on the Network Connection.
     */
    protected abstract fun onNetworkChanged(isNetworkAvailable: Boolean, networkType: NetworkType)

    /**
     * Tells this [BaseActivity] that there is a change in UI Data.
     *
     * @param uiData Instance of [Model] changed from [viewModel].
     */
    protected abstract fun onUIDataChanged(uiData: Model)

    /**
     * Tells this [BaseActivity] that there is an action from [viewModel]
     * that needs to be addressed.
     *
     * @param uiData Instance of [Model] changed from [viewModel] with changed [BaseModel.action].
     */
    protected abstract fun onAction(uiData: Model)

    /**
     * Tells this [BaseActivity] to perform clean-up of it's resources to avoid Memory Leaks.
     */
    protected abstract fun cleanUp()

}
