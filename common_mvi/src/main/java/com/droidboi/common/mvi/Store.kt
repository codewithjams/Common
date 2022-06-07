package com.droidboi.common.mvi

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A Store is a generic container of [State]s for a given screen.
 *
 * @param S A [State] of the view on the screen.
 * @param A An encapsulation of [Action] consisting of all kinds of Action that a user may perform.
 * @param initialState This is the initial [State] of the UI when it is first created.
 * @param reducer A system for taking in the current state, and a new action, and outputting the
 *   updated state.
 * @param middleWares This is a [List] of [MiddleWare] entities for handling various kinds of
 *   side-effects for [Action]s dispatched to this store.
 */
class Store<S : State, A : Action>(
	initialState: S,
	private val reducer: Reducer<S, A>,
	private val middleWares: List<MiddleWare<S, A>> = emptyList()
) {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * [MutableStateFlow] of [S] to manipulate the [State].
	 */
	private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)

	/**
	 * Immutable [StateFlow] of [S] to read the change in [State].
	 */
	val state: StateFlow<S>
		get() = _state

	/**
	 * Provides the current value of [State] from [_state].
	 */
	private val currentState: S
		get() = _state.value

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Dispatch an [action] to a [Reducer] and [MiddleWare]s to handle action accordingly.
	 *
	 * @param action [Action] that needs to be handled.
	 */
	suspend fun dispatch(action: A) {

		middleWares.forEach { middleWare ->
			middleWare.process(action, currentState, this)
		}

		_state.value = reducer.reduce(currentState, action)

	}

}
