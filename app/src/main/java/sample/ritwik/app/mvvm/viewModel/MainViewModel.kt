package sample.ritwik.app.mvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.droidboi.common.mvvm.utility.Event

import com.droidboi.common.mvvm.viewModel.ActionViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.repository.MainRepository

import javax.inject.Inject

const val ACTION_UPDATE_UI = 1001

/**
 * Constant [Integer] as an Action Code to show progress in the UI.
 */
const val ACTION_SHOW_PROGRESS = 1002

/**
 * Constant [Integer] as an Action Code to hide progress from the UI.
 */
const val ACTION_HIDE_PROGRESS = 1003

/**
 * Constant [Integer] denoting the action for [sample.ritwik.app.ui.activity.MainActivity]
 * to perform navigation to [sample.ritwik.app.ui.fragment.CommonFragment].
 */
const val NAVIGATE_TO_COMMON_FRAGMENT = 1004

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
) : ViewModel(), ActionViewModel<MainModel> {


	private val _actionLiveData = MutableLiveData<Int>()
	private val _actionEventLiveData = MutableLiveData<Event<Int>>()

	/*--------------------------------- BaseViewModel Callbacks ----------------------------------*/

	override val actionEventLiveData: LiveData<Event<Int>>
		get() = _actionEventLiveData

	override val actionLiveData: LiveData<Int>
		get() = _actionLiveData

	override fun notifyAction(action: Int) {
		_actionEventLiveData.value = Event(action)
		_actionLiveData.value = action
	}

	override fun onUIStarted() = Unit

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

	private fun updateUI() {
		notifyAction(ACTION_UPDATE_UI)
	}

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
