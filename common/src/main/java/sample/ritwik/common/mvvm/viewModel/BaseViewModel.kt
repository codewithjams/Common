package sample.ritwik.common.mvvm.viewModel

import androidx.annotation.MainThread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import retrofit2.HttpException

import sample.ritwik.common.R

import sample.ritwik.common.data.network.BaseErrorResponse
import sample.ritwik.common.data.network.NetworkType
import sample.ritwik.common.data.network.ResultWrapper

import sample.ritwik.common.mvvm.model.BaseModel

import sample.ritwik.common.mvvm.repository.BaseRepository

import sample.ritwik.common.utility.constant.*

import sample.ritwik.common.utility.helper.RESTAPIException

import java.io.IOException

import java.net.ConnectException

import javax.net.ssl.SSLHandshakeException

/**
 * Abstract [ViewModel] to contain the common methods related to Controlling View as well as Data.
 *
 * @param Repository [BaseRepository] as the Repository of this [ViewModel].
 * @param Model [BaseModel] as the Model of this [ViewModel].
 * @author Ritwik Jamuar
 */
abstract class BaseViewModel<Repository : BaseRepository, Model : BaseModel> : ViewModel() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [Repository] to access the methods related to Data Gathering.
     */
    protected abstract val repository: Repository

    /**
     * Reference of [Model] to access the Data held within this [BaseViewModel].
     */
    protected abstract val model: Model

    /**
     * [CoroutineScope] with [Dispatchers.IO] for performing any operation under IO Thread.
     */
    protected val ioThreadScope: CoroutineScope by lazy { provideIOScope() }

    /**
     * [CoroutineScope] with [Dispatchers.Main] for performing any operation under Main Thread.
     */
    protected val mainThreadScope: CoroutineScope by lazy { provideMainThreadScope() }

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

    /*----------------------------------- ViewModel Callbacks ------------------------------------*/

    override fun onCleared() {
        super.onCleared()
        repository.onCleared() // Tell the repository to terminate all it's work and clear any allocated resources.
    }

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Handles the case when there is a change in the Network Connection.
     *
     * @param isNetworkActive [Boolean] flag that tells whether the Network Connection is active
     *   or not.
     * @param networkType Instance of [NetworkType] denoting the type of active Network Connection.
     */
    fun onNetworkChanged(isNetworkActive: Boolean, networkType: NetworkType) {
        // TODO : Figure out how to handle change in Network.
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

    /**
     * Provides the Instance for [mainThreadScope].
     *
     * @return New Instance of [CoroutineScope] as [mainThreadScope].
     */
    private fun provideMainThreadScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Provides the Instance of [ioThreadScope].
     *
     * @return New Instance of [CoroutineScope] as [ioThreadScope].
     */
    private fun provideIOScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * Performs the RESP API Call in a Thread Safe Manner.
     *
     * @param TypeREST Any Class which holds the response of [apiBlock].
     * @param TypeErrorResponse Any Class which extends [BaseErrorResponse], acts as the
     *   Error Response Body.
     * @param apiBlock Lambda Expression that performs the REST API Call.
     * @param errorResponseClass [Class] of [TypeErrorResponse].
     * @param handleErrorCode Lambda Expression that processes the Error Code before
     *   executing 'handleError'.
     */
    private fun <TypeREST, TypeErrorResponse : BaseErrorResponse> safeRESTAPICall(
        apiBlock: suspend () -> TypeREST,
        errorResponseClass: Class<TypeErrorResponse>,
        handleErrorCode: (Int, TypeErrorResponse) -> ResultWrapper.Error<TypeREST>
    ): Flow<ResultWrapper<TypeREST>> = flow<ResultWrapper<TypeREST>> {
        emit(ResultWrapper.Success(apiBlock.invoke()))
    }.catch { cause: Throwable ->
        emit(handleException(cause, errorResponseClass, handleErrorCode))
    }

    /**
     * Handles the different types of Exceptions that may have caused during
     * performing REST API Call.
     *
     * @param TypeREST Any Class which holds the response of REST API Call.
     * @param TypeErrorResponse Any Class which extends [BaseErrorResponse], acts as the
     *   Error Response Body.
     * @param throwable Instance of [Throwable] denoting the Exception Super-Class which contains
     *   more details about the error cause.
     * @param errorResponseClass [Class] of [TypeErrorResponse].
     * @param handleErrorCode Lambda Expression that processes the Error Code before
     *   executing 'handleError'.
     */
    private fun <TypeREST, TypeErrorResponse : BaseErrorResponse> handleException(
        throwable: Throwable,
        errorResponseClass: Class<TypeErrorResponse>,
        handleErrorCode: (Int, TypeErrorResponse) -> ResultWrapper.Error<TypeREST>
    ): ResultWrapper.Error<TypeREST> = when (throwable) {
        is ConnectException -> ResultWrapper.Error.NetworkConnectionError()
        is SSLHandshakeException -> ResultWrapper.Error.SSLHandShakeError()
        is IOException -> ResultWrapper.Error.NetworkError()
        is HttpException -> handleHTTPException(throwable, errorResponseClass, handleErrorCode)
        else -> ResultWrapper.Error.Other()
    }

    /**
     * Handles the [HttpException] caused by performing REST API Call.
     *
     * @param TypeREST Any Class which is the response body of REST API Call.
     * @param TypeErrorResponse Any Class which extends [BaseErrorResponse], acts as the
     *   Error Response Body.
     * @param throwable Instance of [HttpException] containing more details about the error cause
     *   including Error Body sent from REST API Server.
     * @param errorResponseClass [Class] of [TypeErrorResponse].
     * @param handleErrorCode Lambda Expression that processes the Error Code before
     *   executing 'handleError'.
     */
    private fun <TypeREST, TypeErrorResponse : BaseErrorResponse> handleHTTPException(
        throwable: HttpException,
        errorResponseClass: Class<TypeErrorResponse>,
        handleErrorCode: (Int, TypeErrorResponse) -> ResultWrapper.Error<TypeREST>
    ): ResultWrapper.Error<TypeREST> = handleErrorCode.invoke(
        throwable.code(),
        repository.convertToRESTErrorBody(errorResponseClass, throwable)
    )

    /**
     * Processes the response of REST API Call.
     *
     * @param TypeREST Any Class which is the response body of REST API Call.
     * @param response Instance of [ResultWrapper] of type [TypeREST] which encapsulates
     *   the Response Body of REST API Call.
     * @param processData Lambda Expression that processes that [TypeREST] after successful
     *   REST API Response.
     */
    private suspend fun <TypeREST> processRESTAPIResponse(
        response: ResultWrapper<TypeREST>,
        processData: suspend (Flow<TypeREST>) -> Unit
    ) = processData.invoke(processRESTAPIResponse(response))

    /**
     * Processes the response returned by the REST API Request by [safeRESTAPICall]
     * by handling [ResultWrapper.Success] or [ResultWrapper.Error] scenarios
     * with the help of [Flow]
     *
     * @param TypeREST Any Class which is the response body of REST API Call.
     * @param response Instance of [ResultWrapper] containing the result of REST API Call.
     * @return [Flow] of [TypeREST].
     */
    private fun <TypeREST> processRESTAPIResponse(
        response: ResultWrapper<TypeREST>
    ): Flow<TypeREST> = flow {

        when (response) {

            is ResultWrapper.Success -> emit(response.data)

            is ResultWrapper.Error.NetworkConnectionError -> processError(
                repository.getStringFromResource(
                    R.string.default_failure_message
                )
            )

            is ResultWrapper.Error.SSLHandShakeError -> processError(
                repository.getStringFromResource(
                    R.string.default_ssl_error_message
                )
            )

            is ResultWrapper.Error.NetworkError -> processError(repository.getStringFromResource(R.string.default_failure_message))

            is ResultWrapper.Error.RecoverableError -> processError(response.message, response.code)

            is ResultWrapper.Error.SessionExpired -> onSessionExpired()

            is ResultWrapper.Error.Other -> processError()

        }

    }.flowOn(Dispatchers.Main)

    /**
     * Processes Error to be handled by the caller method in this [BaseViewModel].
     *
     * @param code [Int] denoting the Error Code.
     * @param description [String] denoting the description of the Error.
     * @return [Nothing] so that execution is stopped to handle error.
     */
    private fun processError(
        description: String = repository.getStringFromResource(R.string.default_error_message),
        code: Int = 0
    ): Nothing = throw if (code == 0) {
        Exception(description)
    } else {
        RESTAPIException(code, description)
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
     * Notifies the given [action] to the [sample.ritwik.common.ui.activity.BaseActivity]
     * through [uiLiveData].
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
        model.action = action
        notifyUpdateOnUIData()
    }

    /**
     * Shows the Progress Bar in the UI.
     *
     *
     * From [model], the instance of [sample.ritwik.common.data.ui.ProgressData] is modified and
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
     * From [model], the instance of [sample.ritwik.common.data.ui.ProgressData] is modified and
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
     * From [model], the instance of [sample.ritwik.common.data.ui.ErrorData] is modified and then
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
        title: String = repository.getStringFromResource(R.string.default_error_tile),
        message: String = repository.getStringFromResource(R.string.default_error_message)
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
     * From [model], the instance of [sample.ritwik.common.data.ui.PopUpData] is modified and then
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
     * Performs the Resource Access from Network in the thread-safe manner using [Flow] and
     * Lambda Expressions for each type of trigger.
     *
     * @param TypeREST Any Class which holds the response of [apiBlock].
     * @param TypeErrorResponse Any Class which extends [BaseErrorResponse], acts as the
     *   Error Response Body.
     * @param apiBlock Lambda Expression that performs the REST API Call.
     * @param handleSuccess Lambda Expression that executes after successful REST API Response.
     * @param handleError Lambda Expression that executes after an error in REST API Response.
     * @param processData Lambda Expression that processes that [TypeREST] after successful
     *   REST API Response and invokes before [handleSuccess].
     * @param errorResponseClass [Class] of [TypeErrorResponse].
     * @param handleErrorCode Lambda Expression that processes the Error Code before
     *   executing [handleError].
     */
    protected fun <TypeREST, TypeErrorResponse : BaseErrorResponse> launchNetworkDataLoad(
        apiBlock: suspend () -> TypeREST,
        handleSuccess: () -> Unit,
        handleError: (Throwable) -> Unit,
        processData: suspend (Flow<TypeREST>) -> Unit,
        errorResponseClass: Class<TypeErrorResponse>,
        handleErrorCode: (Int, TypeErrorResponse) -> ResultWrapper.Error<TypeREST>
    ) {
        safeRESTAPICall(apiBlock, errorResponseClass, handleErrorCode).onEach { response ->
            processRESTAPIResponse(response, processData)
            mainThreadScope.launch {
                handleSuccess.invoke()
            }
        }.catch { throwable ->
            mainThreadScope.launch {
                handleError.invoke(throwable)
            }
        }.launchIn(ioThreadScope)
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

    /**
     * Tells this [BaseViewModel] to handle the Session Expiry.
     */
    protected abstract fun onSessionExpired()

}
