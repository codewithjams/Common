package com.droidboi.common.mvi

/**
 * A MiddleWare, by definition, is any class that deals with the side-effects of [Action].
 *
 *
 * Example of side-effects include Logging, triggering Network Calls, IO Operations and such,
 * in essence, anything tat doesn't falls under the domain of [Reducer].
 *
 *
 * Any implementation of this interface is marked as a MiddleWare.
 *
 * @param S A [State] of the view on the screen.
 * @param A An encapsulation of [Action] consisting of all kinds of Action that a user may perform.
 */
interface MiddleWare<S : State, A : Action> {

	/**
	 * Process the given [action] and [currentState] and determines if we need to perform
	 * any side-effects, or trigger a new action.
	 *
	 * @param action [Action] which is triggered from the UI by the User.
	 * @param currentState Current [State] of the UI.
	 * @param store Use [Store] to propagate a new [Action] if required.
	 */
	suspend fun process(action: A, currentState: S, store: Store<S, A>)

	/**
	 * Propagates an [action] via [store].
	 *
	 * @param action [Action] which is triggered from the user.
	 * @param store Use [Store] to propagate a new [Action].
	 */
	suspend fun propagateAction(store: Store<S, A>, action: A) = store.dispatch(action)

}
