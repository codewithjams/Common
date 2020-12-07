package sample.ritwik.app.mvvm.viewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

import sample.ritwik.app.utility.constant.NAVIGATE_TO_COMMON_FRAGMENT

import sample.ritwik.common.mvvm.viewModel.BaseViewModel

import sample.ritwik.common.utility.constant.ACTION_UPDATE_UI

import java.lang.Exception

import javax.inject.Inject

/**
 * ViewModel of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @param repository Instance of [MainRepository] for [repository].
 * @param model Instance of [MainModel] for [model].
 * @author Ritwik Jamuar
 */
class MainViewModel @Inject constructor(
    override val repository: MainRepository,
    override val model: MainModel
) : BaseViewModel<MainRepository, MainModel>() {

    /*--------------------------------- BaseViewModel Callbacks ----------------------------------*/

    override fun initializeLiveData() = Unit

    override fun initializeVariables() = Unit

    override fun onSessionExpired() = Unit

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

            // Show the Progress.
            showProgress(true)

            // Switch to IO Thread.
            ioThreadScope.launch {

                // Mark a delay of 5 seconds.
                delay(5000)

                // Create a new List of LibraryComponent.
                val list: ArrayList<LibraryComponent> = ArrayList()

                try {
                    // Populate all the components fulfilled from repository.
                    list.addAll(repository.provideListOfLibraryComponents())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Switch back to Main Thread.
                mainThreadScope.launch {

                    // Hide the Progress.
                    hideProgress()

                    // Store the fetched list into 'model'.
                    model.libraryComponents = list

                    // Populate the UI of this fetched list by notifying the action to Update the UI.
                    notifyActionOnUI(ACTION_UPDATE_UI)

                }

            }
        } else {

            // Populate the UI with already fetched list by notifying the action to Update the UI..
            notifyActionOnUI(ACTION_UPDATE_UI)

        }

    }

}
