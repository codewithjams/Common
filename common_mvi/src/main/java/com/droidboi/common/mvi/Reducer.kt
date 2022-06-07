package com.droidboi.common.mvi

/**
 * The purpose of Reducer is to transform a given [State] to a new [State] for a given [Action].
 *
 *
 * This will give us clear and predictable state management, that ensures each state is associated
 * with some specific user intent or action.
 *
 *
 * Any implementation of this interface is marked as a Reducer.
 *
 * @param S A [State] of the view on the screen.
 * @param A An encapsulation of [Action] consisting of all kinds of Action that a user may perform.
 */
interface Reducer<S : State, A : Action> {

	/**
	 * For a given [currentState] and an [action] that user tookk, produce a new [State].
	 *
	 * @param currentState Current [State] of screen.
	 * @param action [Action] which is triggered from UI by the User.
	 * @return A new [S].
	 */
	fun reduce(currentState: S, action: A): S

}
