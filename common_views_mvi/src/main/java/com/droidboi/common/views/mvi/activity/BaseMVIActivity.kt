package com.droidboi.common.views.mvi.activity

import android.os.Bundle

import androidx.databinding.ViewDataBinding

import androidx.lifecycle.lifecycleScope

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.BaseMVIViewModel

import com.droidboi.common.views.core.activity.BaseActivity

import kotlinx.coroutines.flow.collect

/**
 * Abstract [BaseActivity] designed around MVI Design Pattern.
 *
 * @param S A [State] of the view on the screen.
 * @param ViewModel [BaseMVIViewModel] as the ViewModel of this [BaseActivity].
 * @param Binding Any Class representing the View/Data Binding Class of this [BaseActivity].
 * @author Ritwik Jamuar
 * @see BaseActivity
 */
abstract class BaseMVIActivity<S : State, ViewModel : BaseMVIViewModel<S, out Action>, Binding : ViewDataBinding> :
	BaseActivity<Binding>() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [ViewModel] to notify any event from here to it as well as
	 * observing any changes.
	 */
	abstract val viewModel: ViewModel

	/*------------------------------------ Activity Callbacks ------------------------------------*/

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
	 *
	 * @param state [S] as the [State] that has to be processed.
	 */
	protected open fun processViewState(state: S): Unit = Unit

}
