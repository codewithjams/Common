package sample.ritwik.common.ui.activity

import android.Manifest

import android.content.DialogInterface
import android.content.pm.PackageManager

import android.os.Bundle

import android.view.View

import androidx.annotation.LayoutRes

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso

import sample.ritwik.common.R

import sample.ritwik.common.data.ui.ErrorData
import sample.ritwik.common.data.ui.NetworkData
import sample.ritwik.common.data.ui.PopUpData

import sample.ritwik.common.mvvm.model.BaseModel

import sample.ritwik.common.mvvm.repository.BaseRepository

import sample.ritwik.common.mvvm.viewModel.BaseViewModel

import sample.ritwik.common.ui.miscellaneous.PopUpWindow

import sample.ritwik.common.utility.constant.*

import sample.ritwik.common.utility.helper.NetworkUtils
import sample.ritwik.common.utility.helper.PermissionUtils

/**
 * Abstract [AppCompatActivity] to contain all the common methods related to setting up the UI.
 *
 * @param Model [BaseModel] as the Model/UI Data of this [AppCompatActivity].
 * @param ViewModel [BaseViewModel] as the ViewModel of this [AppCompatActivity].
 * @param DataBinding [ViewDataBinding] representing the Data Binding Class of this [AppCompatActivity].
 * @author Ritwik Jamuar
 */
abstract class BaseActivity<
        Model : BaseModel,
        ViewModel : BaseViewModel<out BaseRepository, Model>,
        DataBinding : ViewDataBinding
        > : AppCompatActivity() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [DataBinding] to control the Views under it.
     */
    lateinit var binding: DataBinding

    /**
     * Reference of [ViewModel] to notify any event from here to it as well as observing any changes.
     */
    protected abstract val viewModel: ViewModel

    /**
     * Reference of [PopUpWindow] to control showing the Pop-Up in the UI.
     */
    private lateinit var popUpWindow: PopUpWindow

    /**
     * Reference of [NetworkUtils] to get updates on change in Network Connectivity.
     */
    protected abstract val networkUtils: NetworkUtils

    /**
     * Reference of [PermissionUtils] to check and request permissions.
     */
    private lateinit var permissionUtils: PermissionUtils

    /**
     * Reference of [Picasso] to load the images from a Web URL.
     */
    protected abstract val picasso: Picasso

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

    /**
     * [Observer] of [NetworkUtils.networkLiveData].
     */
    private val networkObserver = Observer<NetworkData> { data ->
        processUpdateOnNetwork(data)
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

        if (requestCode != GROUP_PERMISSION_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            return
        }

        if (grantResults.isNotEmpty() && permissions.isNotEmpty()) {

            val isGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED

            when(permissions[0]) {

                Manifest.permission.READ_PHONE_STATE -> {
                    if (isGranted) {
                        with(networkUtils) {
                            isReadingPhoneStatePermitted = true
                            registerCallbacks()
                        }
                    } else {
                        askPhoneStatePermission()
                    }
                }

                else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }

        } else {
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

    /**
     * Provides the Image Loading Library of this Application.
     *
     * @return Current Instance of [picasso].
     */
    fun getImageLoader(): Picasso = picasso

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
        binding = DataBindingUtil.setContentView(this, layoutRes())
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
        networkUtils.networkLiveData.observe(this, networkObserver)
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
     * Shows the [android.widget.PopupWindow] using [popUpWindow].
     *
     * @param popUpData Instance of [PopUpData] denoting the data of [PopUpWindow].
     */
    private fun showPopUpDialog(popUpData: PopUpData) {

        // Initializes the PopUpWindow if not.
        if (!::popUpWindow.isInitialized) {
            popUpWindow = PopUpWindow(this)
        }

        // Show the PopUpWindow.
        popUpWindow.show(popUpData, toolbarView())

        // Reset all the data once the PopUpWindow is shown in the UI.
        popUpData.resetAllData()

    }

    /**
     * Hides the [android.widget.PopupWindow] using [popUpWindow].
     */
    private fun dismissPopUpDialog() {

        // Halt the further execution if the popUpWindow is null.
        if (!::popUpWindow.isInitialized) return

        // Dismiss the PopUpWindow.
        popUpWindow.dismiss()

    }

    /**
     * Handles the changes in [NetworkData] propagated from [networkUtils].
     *
     * @param networkData Modified [NetworkData].
     */
    private fun processUpdateOnNetwork(networkData: NetworkData): Unit = when (networkData.action) {

        NETWORK_ACTION_NONE -> Unit

        NETWORK_ACTION_CHANGED -> viewModel.onNetworkChanged(
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

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Gets the [Int] denoting the layout of this [BaseActivity].
     *
     * @return [Int] as [LayoutRes].
     */
    @LayoutRes
    protected abstract fun layoutRes(): Int

    /**
     * Use [dagger.android.AndroidInjection] to inject the required dependencies
     * into this [BaseActivity].
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
     * Tells this [BaseActivity] to show the Error.
     */
    protected abstract fun showError(errorData: ErrorData)

    /**
     * Provides the Toolbar [View] of this [BaseActivity].
     *
     * @return [View] representing the Toolbar.
     */
    protected abstract fun toolbarView(): View?

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
