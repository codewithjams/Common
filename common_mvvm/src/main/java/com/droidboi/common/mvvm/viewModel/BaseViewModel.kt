package com.droidboi.common.mvvm.viewModel

import androidx.annotation.MainThread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.droidboi.common.mvvm.model.BaseModel

import com.droidboi.common.mvvm.utility.ACTION_ERROR
import com.droidboi.common.mvvm.utility.ACTION_POP_UP
import com.droidboi.common.mvvm.utility.ACTION_PROGRESS_BAR
import com.droidboi.common.mvvm.utility.ACTION_UPDATE_UI

/**
 * Abstract [ViewModel] to contain the common methods related to Controlling View as well as Data.
 *
 * @param Model [BaseModel] as the Model of this [ViewModel].
 * @author Ritwik Jamuar
 */
abstract class BaseViewModel<Model : BaseModel> : ViewModel() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [Model] to access the Data held within this [BaseViewModel].
     */
    protected abstract val model: Model

    /*----------------------------------------- LiveData -----------------------------------------*/

    /**
     * [MutableLiveData] to propagate the changes of [Model] to it's Observers via [uiLiveData].
     */
    private lateinit var _uiLiveData: MutableLiveData<Model>

    /**
     * [LiveData] to propagate the changes in [model] to it's Observers.
     */
    val uiLiveData: LiveData<Model>
        get() = _uiLiveData

    /*------------------------------------ Initializer Block -------------------------------------*/

    init {
        initializeComponents()
    }

    /*-------------------------------------- Private Methods -------------------------------------*/

    /**
     * Initializes the components of this [BaseViewModel].
     */
    private fun initializeComponents() {
        initializeParentLiveData()
        initializeLiveData()
        initializeVariables()
    }

    /**
     * Initializes the [LiveData]s under this [BaseViewModel].
     */
    private fun initializeParentLiveData() {
        _uiLiveData = MutableLiveData()
    }

    /*------------------------------------- Protected Methods ------------------------------------*/

    /**
     * Notifies the updated [model] into UI that uses this [BaseViewModel] through [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     */
    @MainThread
    protected fun notifyUpdateOnUIData() {
        _uiLiveData.value = model
    }

    /**
     * Notifies the given [action] to the UI through [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     *
     * @param action [Int] denoting the Action we wish to notify.
     */
    @MainThread
    protected fun notifyActionOnUI(action: Int) {
        with(model) {
            this.action = action
        }
        notifyUpdateOnUIData()
    }

    /**
     * Triggers the Update in the UI Data.
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     */
    @MainThread
    protected fun triggerUpdateInUI() {
        with(model) {
            action = ACTION_UPDATE_UI
        }
        notifyUpdateOnUIData()
    }

    /**
     * Shows the Progress Bar in the UI.
     *
     *
     * From [model], the instance of [com.droidboi.common.data.ui.ProgressData] is modified and
     * then notified to the view using [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     *
     * @param cancelable [Boolean] to mark whether the Progress Dialog is cancelable in nature or
     *   not.
     */
    @MainThread
    protected fun showProgress(cancelable: Boolean = false) {

        // Update the existing 'model' with changes related to ProgressData.
        with(model) {

            action = ACTION_PROGRESS_BAR // Set the action for Progress Bar.

            with(progressData) {
                showProgress = true // Mark the Boolean flag to show Progress Bar as true.
                isCancelable = cancelable // Assign the flag 'isCancelable' as same as 'cancelable'.
            }

        }

        notifyUpdateOnUIData() // Notify the UI about changes in the 'model'.

    }

    /**
     * Hides the Progress Bar in the UI.
     *
     *
     * From [model], the instance of [com.droidboi.common.data.ui.ProgressData] is modified and
     * then notified to the view using [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     */
    @MainThread
    protected fun hideProgress() {

        // Update the existing 'model' with changes related to ProgressData.
        with(model) {

            action = ACTION_PROGRESS_BAR // Set the action for Progress Bar.

            progressData.showProgress =
                false // Mark the Boolean flag to show Progress Bar as false.

        }

        notifyUpdateOnUIData() // Notify the UI about changes in the 'model'.

    }

    /**
     * Shows the Error in the UI.
     *
     *
     * From [model], the instance of [com.droidboi.common.mvvm.data.ErrorData] is modified and then
     * notified to the view using [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     *
     * @param title [String] denoting the Title of the Error.
     * @param message [String] denoting the Message Description of the Error.
     */
    @MainThread
    protected fun showError(
        title: String = "Error",
        message: String = "Something went wrong, Please try again later."
    ) {

        // Halt the further execution if the 'title' or 'message' is empty.
        if (title.isEmpty() || message.isEmpty()) return

        // Update the existing 'model' with changes related to ErrorData.
        with(model) {

            action = ACTION_ERROR // Set the action for Error.

            with(errorData) {
                this.title = title // Set the Title as 'title'.
                this.description = message // Set the Description as 'description'.
            }

        }

        notifyUpdateOnUIData() // Notify the UI about changes in the 'model'.

    }

    /**
     * Shows the Pop-Up in the UI.
     *
     *
     * From [model], the instance of [com.droidboi.common.data.ui.PopUpData] is modified and then
     * notified to the view using [uiLiveData].
     *
     *
     * NOTE: This method should be executed from Main Thread, otherwise this method will throw
     * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in Background
     * Thread.
     *
     * @param message [String] denoting the informative text to be displayed.
     * @param isAutoClose [Boolean] flag to set whether the Pop-Up is auto-closing or not.
     * @param isShowAtBottom [Boolean] flag to set whether the Pop-Up is to be displayed at the
     *   bottom or not.
     * @param timeOutDuration [Long] denoting the Time Out Duration of Pop-Up. Set this value only
     *   if [isAutoClose] is set as true.
     */
    @MainThread
    protected fun showPopUpWindow(
        message: String,
        isAutoClose: Boolean = false,
        isShowAtBottom: Boolean = false,
        timeOutDuration: Long = 0L
    ) {

        // Halt the further execution if the 'message' is empty.
        if (message.isEmpty()) return

        // Update the existing 'model' with changes related to PopUpData.
        with(model) {

            action = ACTION_POP_UP // Set the action for Pop-Up.

            with(popUpData) {
                infoText = message // Set the Information Text as 'message'.
                autoClose = isAutoClose // Assign the flag 'autoClose' as same as 'isAutoClose'.
                showAtBottom =
                    isShowAtBottom // Assign the flag 'showAtBottom' as same as 'isShowAtBottom'.
                this.timeOutDuration =
                    timeOutDuration // Set the Time Out Duration as 'timeOutDuration'.
                isDisplayed = false // Mark the flag 'isDisplayed' as false.
            }

        }

        notifyUpdateOnUIData() // Notify the UI about changes in the 'model'.

    }

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Tells this [BaseViewModel] to initialize it's [LiveData]s.
     */
    protected abstract fun initializeLiveData()

    /**
     * Tells this [BaseViewModel] to initialize it's Member Variables.
     */
    protected abstract fun initializeVariables()

}
