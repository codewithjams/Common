package com.droidboi.common.rest.retrofit

import com.droidboi.common.rest.retrofit.exceptions.NoErrorBodyException
import com.droidboi.common.rest.retrofit.exceptions.NoResponseException

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.*

import okhttp3.ResponseBody

import retrofit2.HttpException

import retrofit2.Response

import java.io.IOException

import java.net.ConnectException

import kotlin.coroutines.CoroutineContext

/**
 * Abstraction with amalgamation of all the important methods, definitions and callbacks required to
 * perform a REST API Call designed around [retrofit2.Retrofit].
 *
 * @author Ritwik Jamuar
 */
interface RetrofitRESTCaller {

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Performs the REST API Call in asynchronous manner, where the REST API Call events can be
	 * captured via [Flow] of [ResultWrapper] returned from this method.
	 *
	 * @param TypeREST Any class representing the Response Body of the REST API Call.
	 * @param TypeProcessed Any Class representing the Body format of final result.
	 * @param TypeError Any [RESTError] indicating the class as an Error Body.
	 *
	 * @param apiBlock Instance of [APIBlock] which provides the source of REST API Call.
	 * @param process Instance of [Process] which performs conversion of raw response
	 *   to polished one.
	 * @param errorConverter Instance of [ErrorConverter] which performs transforming the
	 *   Error Body from [ResponseBody] to actual class instance of [TypeError].
	 * @param errorHandler Instance of [HTTPErrorHandler] which performs handling the
	 *   REST API Call failure and provide appropriate [RESTErrorStatus].
	 * @param dispatcher [CoroutineContext] assigning the Thread onto which the REST API Call
	 *   must performed upon. Default Value is [Dispatchers.IO].
	 *
	 * @return [Flow] of [ResultWrapper] of [TypeProcessed].
	 */
	fun <TypeREST, TypeProcessed, TypeError : RESTError> performRESTCall(
		apiBlock: APIBlock<TypeREST>,
		process: Process<TypeREST, TypeProcessed>,
		errorConverter: ErrorConverter<TypeError>,
		errorHandler: HTTPErrorHandler<TypeError>,
		dispatcher: CoroutineContext = Dispatchers.IO
	): Flow<ResultWrapper<TypeProcessed>> {

		val loadingFlow = flow<ResultWrapper<TypeProcessed>> {
			emit(ResultWrapper.Loading)
		}

		val restCallFlow = flow {
			emit(apiBlock.perform())
		}.map { response ->
			handleRESTResponse(response, process, errorConverter, errorHandler)
		}.catch { throwable ->
			emit(handleRESTFailure(throwable, errorConverter, errorHandler))
		}

		return flowOf(loadingFlow, restCallFlow).flattenConcat().flowOn(dispatcher)

	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Performs handling of REST API Call when the Response is received.
	 *
	 * @param TypeREST Any class representing the Response Body of the REST API Call.
	 * @param TypeProcessed Any Class representing the Body format of final result.
	 * @param TypeError Any [RESTError] indicating the class as an Error Body.
	 *
	 * @param response Instance of [Response] encapsulating the [TypeREST].
	 * @param process Instance of [Process] which performs conversion of raw response
	 *   to polished one.
	 * @param errorConverter Instance of [ErrorConverter] which performs transforming the
	 *   Error Body from [ResponseBody] to actual class instance of [TypeError].
	 * @param errorHandler Instance of [HTTPErrorHandler] which performs handling the
	 *   REST API Call failure and provide appropriate [RESTErrorStatus].
	 *
	 * @return If everything went well, then [ResultWrapper.Success]
	 *   otherwise one of the sub-type [ResultWrapper.Error] depending on the error.
	 */
	private suspend fun <TypeREST, TypeProcessed, TypeError : RESTError> handleRESTResponse(
		response: Response<TypeREST>?,
		process: Process<TypeREST, TypeProcessed>,
		errorConverter: ErrorConverter<TypeError>,
		errorHandler: HTTPErrorHandler<TypeError>
	): ResultWrapper<TypeProcessed> {

		if (response == null)
			return ResultWrapper.Error.Unknown(NoResponseException())

		val fetchedResponse: TypeREST = response.body()
			?: return handleHTTPError(
				response.errorBody(),
				response.code(),
				errorConverter,
				errorHandler
			)

		return ResultWrapper.Success(process.process(fetchedResponse))

	}

	/**
	 * Performs handling of REST API Call when there's a failure.
	 *
	 * @param TypeProcessed Any Class representing the Body format of final result.
	 * @param TypeError Any [RESTError] indicating the class as an Error Body.
	 *
	 * @param throwable Instance of [Throwable] denoting the cause of failure.
	 * @param errorConverter Instance of [ErrorConverter] which performs transforming the
	 *   Error Body from [ResponseBody] to actual class instance of [TypeError].
	 * @param errorHandler Instance of [HTTPErrorHandler] which performs handling the
	 *   REST API Call failure and provide appropriate [RESTErrorStatus].
	 *
	 * @return [ResultWrapper.Error] of [TypeProcessed] with one of the various Error States
	 *   of a REST API Call.
	 */
	private suspend fun <TypeProcessed, TypeError : RESTError> handleRESTFailure(
		throwable: Throwable,
		errorConverter: ErrorConverter<TypeError>,
		errorHandler: HTTPErrorHandler<TypeError>
	): ResultWrapper<TypeProcessed> {
		when (throwable) {
			is ConnectException -> return ResultWrapper.Error.NetworkErrorConnection
			is IOException -> return ResultWrapper.Error.NetworkErrorIO(throwable)
			is HttpException -> {
				try {

					val errorResponse: Response<*> = throwable.response()
						?: return ResultWrapper.Error.Unknown(NoErrorBodyException())

					return handleHTTPError(
						errorResponse.errorBody(),
						throwable.code(),
						errorConverter,
						errorHandler
					)

				} catch (t: Throwable) {
					return ResultWrapper.Error.Unknown(t)
				}
			}
			else -> return ResultWrapper.Error.Unknown(throwable)
		}
	}

	/**
	 * Handles the case when the REST API Call has failed.
	 *
	 * @param TypeProcessed Any Class representing the Body format of final result.
	 * @param TypeError Any [RESTError] indicating the class as an Error Body.
	 *
	 * @param errorResponseBody Instance of [ResponseBody] masquerading as Error Body.
	 * @param httpStatusCode [Int] denoting the HTTP Status Code when the REST API Call failed.
	 * @param errorConverter Instance of [ErrorConverter] which performs transforming the
	 *   Error Body from [ResponseBody] to actual class instance of [TypeError].
	 * @param errorHandler Instance of [HTTPErrorHandler] which performs handling the
	 *   REST API Call failure and provide appropriate [RESTErrorStatus].
	 *
	 * @return [ResultWrapper] of [TypeProcessed] with one of the various Error States
	 * of a REST API Call.
	 */
	private suspend fun <TypeProcessed, TypeError : RESTError> handleHTTPError(
		errorResponseBody: ResponseBody?,
		httpStatusCode: Int,
		errorConverter: ErrorConverter<TypeError>,
		errorHandler: HTTPErrorHandler<TypeError>
	): ResultWrapper<TypeProcessed> {

		// Since there's no Error Body to determine the REST Error Status from,
		// we halt the further execution and simply return the Result as Unknown
		// with cause being No Error Body.
		if (errorResponseBody == null)
			return ResultWrapper.Error.Unknown(NoErrorBodyException())

		val errorBody: TypeError =
			errorConverter.convert(errorResponseBody) ?:

			// If for some reason conversion failed or simply no Error Body found,
			// we simply return the Result as Unknown, with cause being No Error Body.
			return ResultWrapper.Error.Unknown(NoErrorBodyException())

		return when (val status: RESTErrorStatus = errorHandler.handle(errorBody, httpStatusCode)) {
			is RESTErrorStatus.SessionExpired -> ResultWrapper.Error.SessionExpired
			is RESTErrorStatus.NoErrorBody -> ResultWrapper.Error.Unknown(NoErrorBodyException())
			is RESTErrorStatus.Recoverable -> ResultWrapper.Error.Recoverable(
				status.statusCodeHTTP,
				status.errorCode,
				status.errorDescription
			)
		}

	}

	/*---------------------------------------- Interfaces ----------------------------------------*/

	/**
	 * SAM interface to facilitate providing the source of REST API Call.
	 *
	 *
	 * Usage:
	 * ```
	 * interface ExampleRESTService {
	 *
	 *     @GET("{{some_url}}")
	 *     Response<ExampleResponse> getSomething(...)
	 *
	 * }
	 *
	 * class ExampleRepository : RESTCaller {
	 *
	 *     private val restService: ExampleRESTService
	 *         get() = ...
	 *
	 *     private val ioThreadScope: CoroutineScope by lazy {
	 *         CoroutineScope(Dispatchers.IO)
	 *     }
	 *
	 *     fun fetchSomething() {
	 *         ioThreadScope.launch {
	 *             performRESTCall(
	 *                 {
	 *                     restService.getSomething(...)
	 *                 },
	 *                 ...,
	 *                 ...,
	 *                 ...,
	 *                 ...
	 *             ).collect { result ->
	 *                 // Do some thing with Result
	 *             }
	 *         }
	 *     }
	 *
	 * }
	 * ```
	 *
	 * @param TypeREST Any Class representing the Response Body of the REST API Call.
	 * @see performRESTCall
	 * @author Ritwik Jamuar
	 */
	fun interface APIBlock<TypeREST> {

		/**
		 * Invokes when the REST API Call needs to be performed.
		 *
		 * @return [Response] of [TypeREST] denoting the Response of REST API Call.
		 */
		suspend fun perform(): Response<TypeREST>

	}

	/**
	 * SAM interface to facilitate processing the raw response to polished one.
	 *
	 *
	 * Usage:
	 * ```
	 * class ExampleRepository : RESTCaller {
	 *
	 *     private val ioThreadScope: CoroutineScope by lazy {
	 *         CoroutineScope(Dispatchers.IO)
	 *     }
	 *
	 *     fun fetchSomething() {
	 *         ioThreadScope.launch {
	 *             performRESTCall(
	 *                 ...,
	 *                 { response ->
	 *                     // Transform the `response` from REST API Call to your own wrapper
	 *                 },
	 *                 ...,
	 *                 ...,
	 *                 ...
	 *             ).collect { result ->
	 *                 // Do some thing with Result
	 *             }
	 *         }
	 *     }
	 *
	 * }
	 * ```
	 *
	 * @param TypeREST Any Class representing the Response Body of the REST API Call.
	 * @param TypeProcessed Any Class representing the Body we wish to convert [TypeREST] to.
	 * @see performRESTCall
	 * @author Ritwik Jamuar
	 */
	fun interface Process<TypeREST, TypeProcessed> {

		/**
		 * Invokes when the REST API Call is successful and we have the fetched raw response.
		 *
		 * @param response Instance of [TypeREST] from which we wish to convert.
		 * @return Instance of [TypeProcessed] representing the conversion.
		 */
		suspend fun process(response: TypeREST): TypeProcessed

	}

	/**
	 * SAM interface to facilitate consumer with converting [ResponseBody]
	 * masquerading  as Error Body to [TypeError].
	 *
	 *
	 * Usage:
	 * ```
	 * data class ErrorBody(...): RESTError
	 *
	 * class ExampleRepository : RESTCaller {
	 *
	 *     private val ioThreadScope: CoroutineScope by lazy {
	 *         CoroutineScope(Dispatchers.IO)
	 *     }
	 *
	 *     fun fetchSomething() {
	 *         ioThreadScope.launch {
	 *             performRESTCall(
	 *                 ...,
	 *                 ...,
	 *                 { responseBody ->
	 *                     // Convert this `ResponseBody` to an instance of `ErrorBody`.
	 *                 },
	 *                 ...,
	 *                 ...
	 *             ).collect { result ->
	 *                 // Do some thing with Result
	 *             }
	 *         }
	 *     }
	 *
	 * }
	 * ```
	 *
	 * @param TypeError Any [RESTError] indicating any class as Error Body.
	 * @see performRESTCall
	 * @author Ritwik Jamuar
	 */
	fun interface ErrorConverter<TypeError : RESTError> {

		/**
		 * Invokes when the REST API Call has failed and the [ResponseBody] needs to be converted.
		 *
		 * @param errorBody Instance of [ResponseBody] from which we wish to retrieve the Error Body
		 * @return Instance of [TypeError] as the Error Body.
		 */
		suspend fun convert(errorBody: ResponseBody): TypeError?

	}

	/**
	 * SAM interface to facilitate consumer with handling the HTTP Error Codes.
	 *
	 *
	 * Usage:
	 * ```
	 * class ExampleRepository : RESTCaller {
	 *
	 *     private val ioThreadScope: CoroutineScope by lazy {
	 *         CoroutineScope(Dispatchers.IO)
	 *     }
	 *
	 *     fun fetchSomething() {
	 *         ioThreadScope.launch {
	 *             performRESTCall(
	 *                 ...,
	 *                 ...,
	 *                 ...,
	 *                 { errorResponse, httpStatusCode ->
	 *                     // Based on errorResponse and httpStatusCode, return appropriate
	 *                     // RESTErrorStatus
	 *                 },
	 *                 ...
	 *             ).collect { result ->
	 *                 // Do some thing with Result
	 *             }
	 *         }
	 *     }
	 *
	 * }
	 * ```
	 *
	 * @param TypeError Any [RESTError] indicating any class as Error Body.
	 * @see performRESTCall
	 * @author Ritwik Jamuar
	 */
	fun interface HTTPErrorHandler<TypeError : RESTError> {

		/**
		 * Invokes when the REST API Call has failed and we've got an Error Body
		 * to address the Error.
		 *
		 * @param errorResponse Instance of [TypeError] indicating the Error Body.
		 * @param httpStatusCode [Int] denoting the HTTP Status Code when the REST API Call failed.
		 * @return Instance of [RESTErrorStatus] to indicate the nature of REST API Failure.
		 */
		suspend fun handle(errorResponse: TypeError, httpStatusCode: Int): RESTErrorStatus

	}

}
