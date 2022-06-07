package com.droidboi.common.mvi.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State
import com.droidboi.common.mvi.Store

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

/**
 * Abstract [ViewModel] to contain the common methods related to controlling the View as well as
 * Data around MVI Design Pattern.
 *
 * @param S A [State] of the view on the screen.
 * @param A An encapsulation of [Action] consisting of all kinds of Action that a user may perform.
 */
abstract class BaseMVIViewModel<S : State, A : Action> : ViewModel() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [Store] as our State Container, which is used to dispatch some [Action].
	 */
	protected abstract val store: Store<S, A>

	/**
	 * Provides the Current [State] of the view from the [store].
	 */
	val viewState: StateFlow<S>
		get() = store.state

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Dispatches a given [action] to the [store] for processing.
	 *
	 *
	 * Since the [Store.dispatch] method is a suspending function, it must be executed
	 * from a CoRoutine Scope. We leverage [viewModelScope] here.
	 *
	 * @param action [Action] that needs to be dispatchhed to the [Store].
	 */
	protected open fun dispatchActionToStore(action: A) {
		viewModelScope.launch(Dispatchers.IO) {
			store.dispatch(action)
		}
	}

}
