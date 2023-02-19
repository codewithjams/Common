package com.droidboi.common.mvvm.viewModel

import androidx.annotation.MainThread

import androidx.lifecycle.LiveData

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.utility.Event

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
	 * NOTE: Use this [LiveData] if you want to make sure the propagated Action stays unique
	 * in the closest propagations.
	 */
	val actionEventLiveData: LiveData<Event<Int>>

	/**
	 * Propagates any action normally.
	 *
	 *
	 * NOTE: Use this [LiveData] if you want to receive all the actions irrespective of whether same
	 * Action gets propagated or not.
	 */
	val actionLiveData: LiveData<Int>

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
