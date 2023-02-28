package com.droidboi.common.mvi.viewModel

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State
import com.droidboi.common.mvi.Store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.StateFlow

import kotlin.coroutines.CoroutineContext

/**
 * ViewModel designed around MVI Design Pattern.
 *
 * @param S A [State] of the view on the screen.
 * @param A An encapsulation of [Action] consisting of all kinds of Action that a user may perform.
 *
 * @author Ritwik Jamuar
 */
interface MVIViewModel<S : State, A : Action> {

	/**
	 * Reference of [Store] as our State Container, which is used to dispatch some [Action].
	 */
	val store: Store<S, A>

	/**
	 * Provides the Current [State] of the view from the [store].
	 */
	val viewState: StateFlow<S>
		get() = store.state

	/**
	 * Reference of [CoroutineScope] of this [MVIViewModel] to perform Multi-Threading.
	 */
	val scope: CoroutineScope

	/**
	 * Mutable Reference of [Boolean] flag that marks whether the UI has been started or not.
	 */
	var uiStarted: Boolean

	/*-------------------------------------- Public Methods --------------------------------------*/

	fun onUIStarted() {
	}

	/**
	 * Dispatches a given [action] to the [store] for processing.
	 *
	 *
	 * Since the [Store.dispatch] method is a suspending function, it must be executed
	 * from a CoRoutine Scope. We leverage [scope] here.
	 *
	 * @param action [Action] that needs to be dispatchhed to the [Store].
	 * @param dispatcher [CoroutineContext] denoting the Thread on which the given [action]
	 *   is to be performed. Default value is [Dispatchers.IO].
	 */
	fun dispatchActionToStore(action: A, dispatcher: CoroutineContext = Dispatchers.IO) {
		scope.launch(dispatcher) {
			store.dispatch(action)
		}
	}

}
