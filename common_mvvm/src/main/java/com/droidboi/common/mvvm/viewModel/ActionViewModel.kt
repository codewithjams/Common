package com.droidboi.common.mvvm.viewModel

import androidx.annotation.MainThread

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.utility.Event
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel contract based on Action Codes.
 *
 * @param Model Any [ActionModel] denoting the Model(Data Container) class.
 */
interface ActionViewModel<Model : ActionModel> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [Model] to hold the data of this [ActionViewModel].
	 */
	val model: Model

	/**
	 * Propagates any action via an [Event].
	 *
	 *
	 * NOTE: Use this [StateFlow] if you want to make sure the propagated Action stays unique
	 * in the closest propagations.
	 */
	val actionEventFlow: StateFlow<Event<Int>>

	/**
	 * [CoroutineScope] of this [ActionViewModel] to perform any concurrent activity.
	 */
	val scope: CoroutineScope

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Notifies an [action].
	 *
	 * @param action Any [Int] as the code of Action.
	 * @throws IllegalStateException Thrown when this method is executed from any other thread
	 * than [MainThread].
	 */
	@MainThread
	fun notifyAction(action: Int)

	/**
	 * Trigger for ViewModel by the UI that it is started.
	 */
	fun onUIStarted()

}
