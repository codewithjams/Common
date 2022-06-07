package com.droidboi.common.views.mvi.fragment

import android.content.Context

import android.os.Bundle

import androidx.databinding.ViewDataBinding

import androidx.lifecycle.lifecycleScope

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.BaseMVIViewModel

import com.droidboi.common.views.core.fragment.BaseFragment

import com.droidboi.common.views.mvi.activity.BaseMVIActivity

import kotlinx.coroutines.flow.collect

/**
 * Abstract [BaseFragment] designed around MVI Design Pattern.
 *
 * @param S A [State] of the view on the screen.
 * @param ViewModel [BaseMVIViewModel] as the ViewModel of the [BaseMVIActivity]
 *   that hosts this [BaseFragment].
 * @param Binding Any class representing the View/Data Binding Class of this [BaseFragment].
 * @author Ritwik Jamuar
 */
abstract class BaseMVIFragment<S : State, ViewModel : BaseMVIViewModel<S, out Action>, Binding : ViewDataBinding> :
	BaseFragment<Binding>() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [ViewModel] that is used to manually clear it's instance in the event
	 * of Fragment's destruction so that Memory Leak can be avoided.
	 */
	private var _viewModel: ViewModel? = null

	/**
	 * Reference of [ViewModel] to access the ViewModel of the [BaseMVIActivity]
	 * that hosts this [BaseFragment].
	 */
	private val viewModel: ViewModel
		get() = _viewModel!!

	/*---------------------------------- BaseFragment Callbacks ----------------------------------*/

	@Suppress("UNCHECKED_CAST")
	override fun attachListeners(context: Context) {
		super.attachListeners(context)
		if (context is BaseMVIActivity<out State, out BaseMVIViewModel<out State, out Action>, out ViewDataBinding>) {
			_viewModel = context.viewModel as ViewModel
			return
		}
		throw RuntimeException("$context must be an extension of ${BaseMVIActivity::class.java}")
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		collectViewStates()
	}

	/*-------------------------------------- Private Methods -------------------------------------*/

	/**
	 * Starts collecting the [State]s propagated by [ViewModel].
	 */
	private fun collectViewStates() {
		lifecycleScope.launchWhenResumed {
			viewModel.viewState.collect { state ->
				processViewState(state)
			}
		}
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Processes the given [state] propagated from [ViewModel] to handle this state accordingly
	 * in the UI.
	 */
	protected open fun processViewState(state: S): Unit = Unit

}
