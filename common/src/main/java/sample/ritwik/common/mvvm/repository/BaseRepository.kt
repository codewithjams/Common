package sample.ritwik.common.mvvm.repository

import androidx.annotation.StringRes

import com.squareup.moshi.Moshi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

import retrofit2.HttpException

import sample.ritwik.common.component.persistence.DataStorePreference

import sample.ritwik.common.data.network.BaseErrorResponse

import sample.ritwik.common.utility.helper.ResourceUtils

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
    protected val mainScope: CoroutineScope by lazy { provideMainThreadScope() }

    /**
     * [CoroutineScope] running on Background Thread on which all subsequent Coroutines on child will run.
     */
    protected val ioScope: CoroutineScope by lazy { provideIOScope() }

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Handles the case when the [sample.ritwik.common.mvvm.viewModel.BaseViewModel] is cleared.
     */
    fun onCleared() = cancelAllJobs()

    /**
     * Converts the Error Body from [throwable] to the instance of [TypeError].
     *
     * @param TypeError Any Class that extends [BaseErrorResponse].
     * @param errorClass [Class] of [TypeError].
     * @param throwable [HttpException] which was thrown whenever any error occurred while fetching a REST API.
     * @return Instance of [TypeError] converted from [throwable].
     */
    fun <TypeError: BaseErrorResponse> convertToRESTErrorBody(
        errorClass: Class<TypeError>,
        throwable: HttpException
    ) : TypeError {

        // Using Reflection, create a new instance of TypeError.
        var body: TypeError = errorClass.newInstance()

        // Extract the JSON String out of 'throwable'.
        val errorResponseJSON: String = throwable.response()?.errorBody()?.string() ?: ""

        // Halt the further execution and return the instance of TypeError created using Reflection,
        // when the Response JSON is empty.
        if (errorResponseJSON.isEmpty()) return body

        // Try converting the Error Response using 'moshi'.
        try {
            moshi.adapter(errorClass).fromJson(errorResponseJSON)?.let { response: TypeError ->
                body = response
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Return the body.
        return body

    }

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
     * Provides the Instance for [mainScope].
     *
     * @return New Instance of [CoroutineScope] as [mainScope].
     */
    private fun provideMainThreadScope(): CoroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * Provides the Instance of [ioScope].
     *
     * @return New Instance of [CoroutineScope] as [ioScope].
     */
    private fun provideIOScope(): CoroutineScope = CoroutineScope(Dispatchers.IO)

    /**
     * Cancels all the [kotlinx.coroutines.Job] within [mainScope] and [ioScope].
     */
    private fun cancelAllJobs() {
        mainScope.cancel()
        ioScope.cancel()
    }

}
