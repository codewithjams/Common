package sample.ritwik.app.mvvm.vmDelegate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.droidboi.common.mvvm.utility.Event

import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow

import sample.ritwik.app.mvvm.model.MainModel
import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.repository.ApplicationRepository

import sample.ritwik.app.repository.impl.ApplicationRepositoryImpl

import javax.inject.Inject

/**
 * ViewModel of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @param repository Instance of [ApplicationRepositoryImpl].
 * @param model Instance of [MainModel] for [model].
 * @author Ritwik Jamuar
 */
class MainViewModelDelegate @Inject constructor(
	override val model: MainModel,
	override val repository: ApplicationRepository
) : ViewModel(), MainViewModel {

	/*---------------------------------------- Components ----------------------------------------*/

	private val _actionEventFlow = MutableStateFlow(Event(0))

	/*-------------------------------- ActionViewModel Callbacks ---------------------------------*/

	override val actionEventFlow: StateFlow<Event<Int>>
		get() = _actionEventFlow

	override fun notifyAction(action: Int) {
		_actionEventFlow.value = Event(action)
	}

	override val scope: CoroutineScope
		get() = viewModelScope

	override fun onUIStarted() = Unit

}
