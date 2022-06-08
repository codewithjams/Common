package com.droidboi.common.repository

import androidx.annotation.StringRes

import com.droidboi.common.data.network.BaseErrorResponse
import com.droidboi.common.data.network.ResultWrapper

import com.droidboi.common.persistence.dataStore.DataStorePreference

import com.droidboi.common.utility.resources.ResourceUtils

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi

import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

import retrofit2.HttpException

import java.io.IOException

import java.net.ConnectException

import java.nio.charset.StandardCharsets

import javax.net.ssl.SSLHandshakeException

import kotlin.coroutines.CoroutineContext

/**
 * Abstract Repository to contain the common set-up required to set-up a Repository.
 *
 * @author Ritwik Jamuar
 */
abstract class BaseRepository {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [DataStorePreference] which will be used to perform saving and
	 * retrieving data from Persistence Storage.
	 */
	protected abstract val dataStorePreference: DataStorePreference

	/**
	 * Reference of [Moshi] which will be used to serialize/de-serialize any Classes.
	 */
	protected abstract val moshi: Moshi

	/**
	 * Reference of [ResourceUtils] which will be used to fetch the resources existing
	 * within this Application.
	 */
	protected abstract val resourceUtils: ResourceUtils

	/**
	 * [CoroutineScope] running on Main Thread on which all subsequent Coroutines on child will run.
	 */
	protected val mainThreadScope: CoroutineScope by lazy { CoroutineScope(mainDispatcher) }

	/**
	 * [CoroutineContext] denoting the Context for Coroutine on Main Thread.
	 */
	protected val mainDispatcher: CoroutineContext = Dispatchers.Main

	/**
	 * [CoroutineScope] running on Background Thread on which all subsequent Coroutines on child will run.
	 */
	protected val ioThreadScope: CoroutineScope by lazy { CoroutineScope(ioDispatcher) }

	/**
	 * [CoroutineContext] denoting the Context for Coroutine on IO Thread.
	 */
	protected val ioDispatcher: CoroutineContext = Dispatchers.IO

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Using [resourceUtils], provide the [String] from [StringRes].
	 *
	 * @param resourceID [Int] as [StringRes] denoting the String Resource ID.
	 * @return [String] from the given [resourceID].
	 */
	fun getStringFromResource(@StringRes resourceID: Int): String =
		resourceUtils.getString(resourceID)

	/**
	 * Using [resourceUtils], provide the [String] from [StringRes].
	 *
	 * @param resourceID [Int] as [StringRes] denoting the String Resource ID.
	 * @param formatArguments Multiple Format Arguments of [Any] Data/Class Type.
	 * @return [String] from the given [resourceID].
	 */
	fun getStringFromResource(@StringRes resourceID: Int, vararg formatArguments: Any?) =
		resourceUtils.getString(resourceID, formatArguments)

	/*-------------------------------------- Private Methods -------------------------------------*/

	/**
	 * Cancels all the [kotlinx.coroutines.Job] within [mainThreadScope] and [ioThreadScope].
	 */
	private fun cancelAllJobs() {
		mainThreadScope.cancel()
		ioThreadScope.cancel()
	}

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
		handleErrorCode: (Int, TypeErrorResponse?) -> ResultWrapper.Error<TypeREST>
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
		handleErrorCode: (Int, TypeErrorResponse?) -> ResultWrapper.Error<TypeREST>
	): ResultWrapper.Error<TypeREST> = when (throwable) {
		is ConnectException -> ResultWrapper.Error.NetworkConnectionError()
		is SSLHandshakeException -> ResultWrapper.Error.SSLHandShakeError()
		is IOException -> ResultWrapper.Error.NetworkError()
		is HttpException -> handleHTTPException(throwable, errorResponseClass, handleErrorCode)
		is JsonDataException -> ResultWrapper.Error.RecoverableError(
			401,
			throwable.message ?: getStringFromResource(R.string.default_json_error_message)
		)
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
		handleErrorCode: (Int, TypeErrorResponse?) -> ResultWrapper.Error<TypeREST>
	): ResultWrapper.Error<TypeREST> = handleErrorCode.invoke(
		throwable.code(),
		convertJSONToClassInstance(
			errorResponseClass,
			extractJSONFromHTTPException(throwable)
		)
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

			is ResultWrapper.Error.NetworkConnectionError ->
				processError(getStringFromResource(R.string.default_failure_message))

			is ResultWrapper.Error.SSLHandShakeError ->
				processError(getStringFromResource(R.string.default_ssl_error_message))

			is ResultWrapper.Error.NetworkError ->
				processError(getStringFromResource(R.string.default_failure_message))

			is ResultWrapper.Error.RecoverableError ->
				processError(response.message, response.code)

			is ResultWrapper.Error.SessionExpired -> processError(code = -1)

			is ResultWrapper.Error.Other -> processError()

		}

	}.flowOn(Dispatchers.Main)

	/**
	 * Processes Error to be handled by the caller method in this [BaseRepository].
	 *
	 * @param code [Int] denoting the Error Code.
	 * @param description [String] denoting the description of the Error.
	 * @return [Nothing] so that execution is stopped to handle error.
	 */
	private fun processError(
		description: String = getStringFromResource(R.string.default_error_message),
		code: Int = 0
	): Nothing = throw if (code == 0) {
		Exception(description)
	} else {
		RESTAPIException(code, description)
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Extracts the JSON Body as [String] from the [HttpException].
	 *
	 * @param throwable Instance of [HttpException] from which JSON is to be extracted.
	 * @return [String] denoting the JSON extracted.
	 */
	protected fun extractJSONFromHTTPException(throwable: HttpException): String =
		throwable.response()?.errorBody()?.toString() ?: ""

	/**
	 * Converts a JSON String to the Object of [Type].
	 *
	 * @param Type Any Class.
	 * @param classType [Class] of type [Type].
	 * @param json [String] denoting the JSON Body we want to convert.
	 * @return New Instance of [Type].
	 */
	protected fun <Type> convertJSONToClassInstance(
		classType: Class<Type>,
		json: String
	): Type? = try {
		moshi.adapter(classType).fromJson(json)
	} catch (e: IOException) {
		null
	}

	/**
	 * Extracts a JSON File as a [String] stored under assets folder.
	 *
	 * @param fileName [String] as the name of the JSON File stored in the assets folder.
	 * @return If the JSON File is found, then a [String] as the JSON Body, else null.
	 */
	protected fun extractJSONFromAssets(fileName: String): String? = try {

		// Check whether the file name is valid or not,
		// by checking whether the file name is not empty and ends with '.json'.
		if (fileName.isEmpty() || !fileName.endsWith(".json")) {

			// At this point, the File Name provided is empty or does not ends with
			// '.json' extension, so we halt the further execution and return null.
			null

		} else {

			// At this point, the File Name provided is not empty and ends with '.json' extension,
			// so we start reading the file and extract it to a String.

			// Open the File from AssetManager.
			val stream = resourceUtils.getAsset(fileName)

			// Determine the size of the contents from the InputStream.
			val size: Int = stream.available()

			// Create an array of Bytes which stores the content of files in the form of bytes.
			val buffer = ByteArray(size)

			// Read all the contents of InputStream to the buffer created above.
			stream.read(buffer)

			// Close the InputStream.
			stream.close()

			// Create a new String using the buffer that was read.
			String(buffer, StandardCharsets.UTF_8)

		}

	} catch (e: IOException) {

		// At this point, something unexpected happened while opening the file.
		// So, we set the value of the JSON String as null, since the JSON itself was not extracted.
		null

	}

	/**
	 * Extracts an instance of [Type] from the JSON File stored under 'assets' folder.
	 *
	 * @param Type Any Class.
	 * @param classType [Class] of [Type].
	 * @param fileName [String] as the name of the JSON File.
	 * @return [Flow] of [Type] with value as the instance of [Type] ]if everything went well,
	 *   else null.
	 */
	protected fun <Type> extractClassInstanceFromAssets(
		classType: Class<Type>,
		fileName: String
	): Flow<Type?> = flow {
		emit(convertJSONToClassInstance(classType, extractJSONFromAssets(fileName) ?: ""))
	}.flowOn(ioDispatcher)

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
	 * @param handleSessionExpired Lambda Expression that notifies if there is a Session Expiry.
	 */
	protected fun <TypeREST, TypeErrorResponse : BaseErrorResponse> launchNetworkDataLoad(
		apiBlock: suspend () -> TypeREST,
		handleSuccess: () -> Unit,
		handleError: (Throwable) -> Unit,
		processData: suspend (Flow<TypeREST>) -> Unit,
		errorResponseClass: Class<TypeErrorResponse>,
		handleErrorCode: (Int, TypeErrorResponse?) -> ResultWrapper.Error<TypeREST>,
		handleSessionExpired: () -> Unit
	) {
		safeRESTAPICall(apiBlock, errorResponseClass, handleErrorCode).onEach { response ->
			processRESTAPIResponse(response, processData)
			mainThreadScope.launch {
				handleSuccess.invoke()
			}
		}.catch { throwable ->
			mainThreadScope.launch {
				when (throwable) {
					is RESTAPIException -> {
						if (throwable.code == -1) {
							handleSessionExpired.invoke()
						} else {
							handleError.invoke(throwable)
						}
					}
					else -> handleError.invoke(throwable)
				}
			}
		}.launchIn(ioThreadScope)
	}

}
