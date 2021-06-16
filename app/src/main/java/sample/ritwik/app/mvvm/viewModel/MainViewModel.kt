package sample.ritwik.app.mvvm.viewModel

import androidx.lifecycle.viewModelScope

import com.droidboi.common.mvvm.viewModel.BaseViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

import sample.ritwik.app.utility.constant.NAVIGATE_TO_COMMON_FRAGMENT

import sample.ritwik.common.utility.constant.ACTION_UPDATE_UI

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
) : BaseViewModel<MainModel>() {

    /*--------------------------------- BaseViewModel Callbacks ----------------------------------*/

    override fun initializeLiveData() = Unit

    override fun initializeVariables() = Unit

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Handles the click of button 'More about Common Library'
     * from [sample.ritwik.app.ui.fragment.WelcomeFragment].
     */
    fun onCommonClicked() {
        notifyActionOnUI(NAVIGATE_TO_COMMON_FRAGMENT)
    }

    /**
     * Fetches the [List] of [LibraryComponent] from [repository] and publish the result to
     */
    fun fetchLibraryComponents() {

        // Load the data only if it is not populated before.
        if (!model.isComponentsPopulated()) {

            viewModelScope.launch {

                // Show the Progress.
                showProgress()

                // Mark a delay of 5 seconds.
                delay(5000)

                // Create a new List of LibraryComponent.
                val list: ArrayList<LibraryComponent> = ArrayList()

                repository.provideListOfLibraryComponents().collect { components ->
                    list.addAll(components)
                }

                // Hide the Progress.
                hideProgress()

                // Store the fetched list into 'model'.
                model.libraryComponents = list

                // Populate the UI of this fetched list by notifying the action to Update the UI.
                notifyActionOnUI(ACTION_UPDATE_UI)

            }

        } else {

            // Populate the UI with already fetched list by notifying the action to Update the UI..
            notifyActionOnUI(ACTION_UPDATE_UI)

        }

    }

}
