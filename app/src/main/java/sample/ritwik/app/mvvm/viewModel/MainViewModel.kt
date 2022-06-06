package sample.ritwik.app.mvvm.viewModel

import androidx.lifecycle.viewModelScope

import com.droidboi.common.mvvm.viewModel.BaseMVVMViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

import sample.ritwik.app.utility.constant.ACTION_HIDE_PROGRESS
import sample.ritwik.app.utility.constant.ACTION_SHOW_PROGRESS
import sample.ritwik.app.utility.constant.NAVIGATE_TO_COMMON_FRAGMENT

import javax.inject.Inject

/**
 * ViewModel of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @param repository Instance of [MainRepository].
 * @param model Instance of [MainModel] for [model].
 * @author Ritwik Jamuar
 */
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    override val model: MainModel
) : BaseMVVMViewModel<MainModel>() {

    /*--------------------------------- BaseViewModel Callbacks ----------------------------------*/

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Handles the click of button 'More about Common Library'
     * from [sample.ritwik.app.ui.fragment.WelcomeFragment].
     */
    fun onCommonClicked() {
        navigateToCommon()
    }

    /**
     * Fetches the [List] of [LibraryComponent] from [repository] and publish the result to
     */
    fun fetchLibraryComponents() {

        // Load the data only if it is not populated before.
        if (!model.isComponentsPopulated()) {
            populate()
            return
        }

        updateUI() // Populate the UI with already fetched list.

    }

    /*------------------------------------- Private Methods --------------------------------------*/

    /**
     * Triggers UI to show progress.
     */
    private fun showProgress() {
        notifyAction(ACTION_SHOW_PROGRESS)
    }

    /**
     * Triggers UI to hide progress.
     */
    private fun hideProgress() {
        notifyAction(ACTION_HIDE_PROGRESS)
    }

    /**
     * Tells the UI to navigate to Common view.
     */
    private fun navigateToCommon() {
        notifyAction(NAVIGATE_TO_COMMON_FRAGMENT)
    }

    private fun populate() {
        viewModelScope.launch {
            showProgress() // Show the Progress.

            delay(5000) // Simulate REST API Call by delaying for 5 seconds.

            val list: ArrayList<LibraryComponent> = ArrayList()
            repository.provideListOfLibraryComponents().collect { components ->
                list.addAll(components)
            }

            hideProgress() // Hide the Progress.

            model.libraryComponents = list // Store the fetched list into 'model'.

            updateUI()
        }
    }

}
