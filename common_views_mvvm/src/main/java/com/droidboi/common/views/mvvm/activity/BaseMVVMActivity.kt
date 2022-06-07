package com.droidboi.common.views.mvvm.activity

import android.os.Bundle

import androidx.databinding.ViewDataBinding

import com.droidboi.common.mvvm.model.BaseModel

import com.droidboi.common.mvvm.utility.ACTION_EXIT_UI
import com.droidboi.common.mvvm.utility.ACTION_NONE
import com.droidboi.common.mvvm.utility.ACTION_UPDATE_UI

import com.droidboi.common.mvvm.viewModel.BaseMVVMViewModel

import com.droidboi.common.views.core.activity.BaseActivity

/**
 * Abstract [BaseActivity] designed around MVVM Design Pattern.
 *
 * @param ViewModel [BaseMVVMViewModel] as the ViewModel of this [BaseActivity].
 * @param Binding Any Class representing the View/Data Binding Class of this [BaseActivity].
 * @author Ritwik Jamuar
 * @see BaseActivity
 */
abstract class BaseMVVMActivity<ViewModel : BaseMVVMViewModel<out BaseModel>, Binding : ViewDataBinding> :
	BaseActivity<Binding>() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [ViewModel] to notify any event from here to it
	 * as well as observing any changes.
	 */
	abstract val viewModel: ViewModel

	/*------------------------------------ Activity Callbacks ------------------------------------*/

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		attachObservers()
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Attaches [androidx.lifecycle.Observer] of any [androidx.lifecycle.LiveData].
	 */
	protected open fun attachObservers() {
		viewModel.actionLiveData.observe(this) { data ->
			processAction(data)
		}
	}

	/**
	 * Processes generic actions propagated from [ViewModel].
	 */
	protected open fun processAction(action: Int) = when (action) {
		ACTION_NONE -> Unit
		ACTION_EXIT_UI -> finish()
		ACTION_UPDATE_UI -> updateUI()
		else -> onAction(action)
	}

	/**
	 * Updates the UI with new data.
	 */
	protected open fun updateUI() = Unit

	/**
	 * Handles any non-generic [action] propagated from the [ViewModel].
	 *
	 * @param action [Int] denoting the Code of the Action that shall be executed.
	 */
	protected open fun onAction(action: Int) = Unit

}
