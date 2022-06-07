package com.droidboi.common.views.mvvm.fragment

import android.content.Context

import android.os.Bundle

import android.view.View

import androidx.databinding.ViewDataBinding

import com.droidboi.common.mvvm.model.BaseModel

import com.droidboi.common.mvvm.utility.ACTION_NONE
import com.droidboi.common.mvvm.utility.ACTION_UPDATE_UI

import com.droidboi.common.mvvm.viewModel.BaseMVVMViewModel

import com.droidboi.common.views.mvvm.activity.BaseMVVMActivity

import com.droidboi.common.views.core.fragment.BaseFragment

/**
 * Abstract [BaseFragment] designed around MVVM Design Pattern.
 *
 * @param ViewModel [BaseMVVMViewModel] as the ViewModel of the [BaseMVVMActivity]
 *   that hosts this [BaseFragment].
 * @param Binding Any Class referencing the View/Data Binding class of this [BaseFragment].
 * @author Ritwik Jamuar.
 */
abstract class BaseMVVMFragment<ViewModel : BaseMVVMViewModel<out BaseModel>, Binding : ViewDataBinding> :
	BaseFragment<Binding>() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [ViewModel] that is used to manually clear it's instance in the event
	 * of Fragment's destruction so that Memory Leak can be avoided.
	 */
	private var _viewModel: ViewModel? = null

	/**
	 * Reference of [ViewModel] as the ViewModel of [BaseMVVMActivity]
	 * that hosts this [BaseMVVMFragment].
	 */
	protected val viewModel: ViewModel
		get() = _viewModel!!

	/*---------------------------------- BaseFragment Callbacks ----------------------------------*/

	@Suppress("UNCHECKED_CAST")
	override fun attachListeners(context: Context) {
		if (context is BaseMVVMActivity<out BaseMVVMViewModel<out BaseModel>, out ViewDataBinding>) {
			_viewModel = context.viewModel as ViewModel
			return
		}
		throw RuntimeException("$context must be an extension of ${BaseMVVMActivity::class.java}")
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		attachObservers()
	}

	override fun onDetach() {
		super.onDetach()
		_viewModel = null
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Attaches [androidx.lifecycle.Observer] of any [androidx.lifecycle.LiveData].
	 */
	protected open fun attachObservers() {
		viewModel.actionLiveData.observe(viewLifecycleOwner) { action ->
			processAction(action)
		}
	}

	/**
	 * Processes generic actions propagated from the [ViewModel].
	 *
	 * @param action [Int] denoting the code of the Action that shall be executed.
	 */
	protected open fun processAction(action: Int) = when (action) {
		ACTION_NONE -> Unit
		ACTION_UPDATE_UI -> updateUI()
		else -> onAction(action)
	}

	/**
	 * Updates the UI with new data.
	 */
	protected open fun updateUI() = Unit

	/**
	 * Handles anu non-generic [action] propagated from the [ViewModel].
	 *
	 * @param action [Int] denoting the code of the Action that shall be executed.
	 */
	protected open fun onAction(action: Int) = Unit

}
