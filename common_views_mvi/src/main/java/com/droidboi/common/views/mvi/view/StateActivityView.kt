package com.droidboi.common.views.mvi.view

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.MVIViewModel

/**
 * View (hosted in an [AppCompatActivity]) of the [ViewModel] onto which
 * new changes in [S] are propagated.
 *
 *
 * Usage:
 * ```
 * data class ExampleState(...) : State
 *
 * sealed class ExampleAction : Action {
 *    ...
 * }
 *
 * class ExampleMiddleWare(..) : MiddleWare<ExampleState, ExampleAction> {
 *    ...
 * }
 *
 * class ExampleReducer(...) : Reducer<ExampleState, ExampleAction> {
 *    ...
 * }
 *
 * interface ExampleViewModel : MVIViewModel<ExampleState, ExampleAction> {
 *    ...
 * }
 *
 * class ExampleVMDelegate : ViewModel(), ExampleViewModel {
 *
 *     ...
 *
 *     override val store: Store<ExampleState, ExampleAction>
 *         get() = // Provide the instance of Store
 *
 *     override val scope: CoroutineScope
 *         get() = viewModelScope
 *
 *     ...
 *
 * }
 *
 * interface ExampleView : StateActivityView<ExampleState, ExampleViewModel> {
 *    ...
 * }
 *
 * class ExampleActivity : BaseMVIActivity<ExampleView>(), ExampleView {
 *
 *     ...
 *
 *     override val ui: ExampleView
 *         get() = this
 *
 *     override val viewModel: ExampleViewModel
 *         get() = // Provide the instance of ViewModel through Delegate
 *
 *     override val activity: AppCompatActivity
 *         get() = this
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         ui.onActivityCreated()
 *     }
 *
 *     override fun onStart() {
 *         super.onStart()
 *         ui.onActivityStarted()
 *     }
 *
 *     override fun onDestroy() {
 *         super.onDestroy()
 *         ui.onActivityDestroyed()
 *     }
 *
 *     ...
 *
 * }
 * ```
 *
 * @param S [State] of this [activity].
 * @param ViewModel [MVIViewModel] of this [activity] onto which the events of this [activity]
 *   is passed upon and receives the changes in form of changes in the [S].
 * @author Ritwik Jamuar
 */
interface StateActivityView<
		S : State,
		ViewModel : MVIViewModel<S, out Action>
		> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * [ViewModel] to handle the UI events of this [StateActivityView].
	 */
	val viewModel: ViewModel

	/**
	 * Reference of [AppCompatActivity] on which this [StateActivityView] is implemented.
	 */
	val activity: AppCompatActivity

	/**
	 * Reference of [LifecycleCoroutineScope] to perform some executions
	 * under [lifecycleScope] of [activity].
	 */
	val scope: LifecycleCoroutineScope
		get() = activity.lifecycleScope

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Injects components that may have been included in this [activity].
	 */
	fun inject()

	/**
	 * Extract the arguments from this [activity].
	 *
	 * @param intent [Intent] encapsulating all the arguments passed to this [activity] UI.
	 */
	fun extractArguments(intent: Intent)

	/**
	 * Initialize any component or start some execution.
	 */
	fun initialize()

	/**
	 * Handle the change in [state].
	 *
	 * @param state New [S] as the changed [State].
	 */
	fun onStateChanged(state: S)

	/**
	 * Cleans-up any allocated resources to avoid Memory Leaks.
	 */
	fun cleanUp()

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Handles the event when [activity] is created.
	 */
	fun onActivityCreated() {
		inject()
		performArgumentExtraction()
		collectStatesFromVM()
		initialize()
	}

	/**
	 * Handles the event when [activity] is started.
	 *
	 *
	 * NOTE:
	 * Override this method if you want to override
	 */
	fun onActivityStarted() {

		if (viewModel.uiStarted)
			return

		viewModel.onUIStarted()

		viewModel.uiStarted = true

	}

	/**
	 * Handles the event when [activity] is destroyed.
	 */
	fun onActivityDestroyed() {
		performResourceCleanUp()
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Performs extracting arguments from [Intent] sent when this [activity] is started.
	 */
	private fun performArgumentExtraction() {
		activity.intent?.let {
			extractArguments(it)
		}
	}

	/**
	 * Collects the [S] emitted from [ViewModel] as an some new state to be processed
	 * in this [StateActivityView].
	 */
	private fun collectStatesFromVM() {
		scope.launchWhenResumed {
			viewModel.viewState.collect { state ->
				onStateChanged(state)
			}
		}
	}

	/**
	 * Performs cleaning up the resources from [activity].
	 */
	private fun performResourceCleanUp() {
		cleanUp()
	}

}
