package com.droidboi.common.mvvm.viewModel

import androidx.annotation.MainThread

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.droidboi.common.mvvm.model.BaseModel

import com.droidboi.common.mvvm.utility.*

/**
 * Abstract [ViewModel] to contain the common methods related to Controlling View as well as Data
 * around MVVM Design Pattern.
 *
 * @param Model [BaseModel] as the Data Class that holds content of the view
 * propagating events to this [ViewModel].
 * @author Ritwik Jamuar
 */
abstract class BaseMVVMViewModel<Model : BaseModel> : ViewModel() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [Model] to access the Data held within this [BaseMVVMViewModel].
	 */
	abstract val model: Model

	/*----------------------------------------- LiveData -----------------------------------------*/

	/**
	 * [MutableLiveData] to propagate the action code to be executed in the UI.
	 */
	private val _actionLiveData: MutableLiveData<Int> by lazy { MutableLiveData(ACTION_NONE) }

	/**
	 * [LiveData] to propagate the Action Code to be executed in the UI.
	 */
	val actionLiveData: LiveData<Int>
		get() = _actionLiveData

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Updates the UI with new data.
	 *
	 *
	 * NOTE: This method should be executed from Main Thread, otherwise this method will throw
	 * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in
	 * Background Thread.
	 */
	@MainThread
	protected fun updateUI() {
		notifyAction(ACTION_UPDATE_UI)
	}

	/**
	 * Triggers the UI to exit.
	 *
	 *
	 * NOTE: This method should be executed from Main Thread, otherwise this method will throw
	 * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in
	 * Background Thread.
	 */
	@MainThread
	protected fun exitUI() {
		notifyAction(ACTION_EXIT_UI)
	}

	/**
	 * Notifies the given [action] to the UI through [actionLiveData].
	 *
	 *
	 * NOTE: This method should be executed from Main Thread, otherwise this method will throw
	 * [IllegalStateException] caused due to accessing the method [LiveData.setValue] in
	 * Background Thread.
	 *
	 * @param action [Int] denoting the Action we wish to notify.
	 */
	@MainThread
	protected fun notifyAction(action: Int) {
		_actionLiveData.value = action
	}

}
