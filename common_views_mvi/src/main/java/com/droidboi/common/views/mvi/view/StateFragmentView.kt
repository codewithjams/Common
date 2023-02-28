package com.droidboi.common.views.mvi.view

import android.content.Context

import android.os.Bundle

import androidx.fragment.app.Fragment

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.MVIViewModel

/**
 * View (hosted in a [Fragment]) of the [ViewModel] onto which
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
 * interface ExampleView : StateFragmentView<ExampleState, ExampleViewModel> {
 *     ...
 * }
 *
 * class ExampleFragment : Fragment(), ExampleFragmentView {
 *
 *     ...
 *
 *     override var mutableViewModel: ExampleViewModel? = null
 *
 *     override val fragment: Fragment
 *         get() = this
 *
 *     override fun onAttach(context: Context) {
 *         super.onAttach(context)
 *         onFragmentAttached(context)
 *     }
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         onFragmentCreated()
 *     }
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         onFragmentViewCreated()
 *     }
 *
 *     override fun onDestroyView() {
 *         super.onDestroyView()
 *         onFragmentViewDestroyed()
 *     }
 *
 *     override fun onDestroy() {
 *         super.onDestroy()
 *         onFragmentDestroyed()
 *     }
 *
 *     override fun onDetach() {
 *         super.onDetach()
 *         onFragmentDetached()
 *     }
 *
 *     ...
 *
 * }
 * ```
 *
 * @param S S A [State] of the view on the screen.
 * @param ViewModel [MVIViewModel] as the ViewModel of the [androidx.appcompat.app.AppCompatActivity]
 *   that hosts this [Fragment].
 * @author Ritwik Jamuar
 */
interface StateFragmentView<S : State, ViewModel : MVIViewModel<S, out Action>> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * [ViewModel] to handle the UI events of this [StateFragmentView].
	 */
	val viewModel: ViewModel
		get() = mutableViewModel!!

	/**
	 * Reference of [Fragment] on which this [StateFragmentView] is implemented.
	 */
	val fragment: Fragment

	/**
	 * Reference of [LifecycleCoroutineScope] to perform some executions
	 * under [lifecycleScope] of [fragment].
	 */
	val scope: LifecycleCoroutineScope
		get() = fragment.lifecycleScope

	/**
	 * Mutable reference of [ViewModel] that can be cleared from [fragment].
	 */
	var mutableViewModel: ViewModel?

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Extracts the arguments from this [fragment].
	 *
	 * @param arguments [Bundle] encapsulating all the arguments passed to this [fragment] UI.
	 */
	fun extractArguments(arguments: Bundle)

	/**
	 * Sets-up the components under this [fragment].
	 */
	fun initialize()

	/**
	 * Sets-up the view under this [fragment].
	 */
	fun initializeViews()

	/**
	 * Handle the change in [state].
	 *
	 * @param state New [S] as the changed [State].
	 */
	fun onStateChanged(state: S)

	/**
	 * Cleans-up the views under this [fragment].
	 */
	fun cleanUpViews()

	/**
	 * Sets-up the components under this [fragment].
	 */
	fun cleanUp()

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Handles the event when [fragment] is attached to [context].
	 *
	 * @param context [Context] of any [androidx.appcompat.app.AppCompatActivity]
	 *   that hosts this [Fragment].
	 */
	fun onFragmentAttached(context: Context) {
		extractViewModelFromView(context)
	}

	/**
	 * Handles the event when [fragment] is created.
	 */
	fun onFragmentCreated() {
		initialize()
		performArgumentExtraction()
	}

	/**
	 * Handles the event when [fragment]'s View is created.
	 */
	fun onFragmentViewCreated() {
		collectStatesFromVM()
		initializeViews()
	}

	/**
	 * Handles the event when [fragment]'s View is destroyed.
	 */
	fun onFragmentViewDestroyed() {
		cleanUpViews()
	}

	/**
	 * Handles the event when [fragment] is destroyed.
	 */
	fun onFragmentDestroyed() {
		cleanUp()
	}

	/**
	 * Handles the event when [fragment] is detached.
	 */
	fun onFragmentDetached() {
		cleanUpViewModelFromView()
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Extracts the [ViewModel] from [context].
	 *
	 * @param context [Context] of any [androidx.appcompat.app.AppCompatActivity]
	 *   that hosts this [Fragment].
	 * @throws RuntimeException This is thrown when the hosting
	 *   [androidx.appcompat.app.AppCompatActivity] is not an implementation of [StateActivityView]
	 *   or if unable to extract the [ViewModel] from the [StateActivityView].
	 */
	@Throws(RuntimeException::class)
	private fun extractViewModelFromView(context: Context) {

		// Halt the further execution by throwing exception stating that the hosting Activity
		// is not an implementation of StateActivityView.
		if (context !is StateActivityView<out State, out MVIViewModel<out State, out Action>>)
			throw RuntimeException("$context must be an extension of ${StateActivityView::class.java}")

		// At this point, context as Activity is indeed an implementation of StateActivityView.

		// Try to Type-Cast the context to StateActivityView.
		@Suppress("UNCHECKED_CAST")
		(context as? StateActivityView<S, ViewModel>)?.let { view ->
			this.mutableViewModel = view.viewModel
		}

		// Check if the viewModel is populated, and if not populated, halt the further execution
		// by throwing an exception stating the same.
		if (this.mutableViewModel == null)
			throw RuntimeException("Unable to extract ViewModel from $context")

	}

	/**
	 * Performs extracting arguments from [Bundle] sent when this [fragment] is created.
	 */
	private fun performArgumentExtraction() {
		fragment.arguments?.let { arguments ->
			extractArguments(arguments)
		}
	}

	/**
	 * Collects the [S] emitted from [ViewModel] as an some new state to be processed
	 * in this [StateFragmentView].
	 */
	private fun collectStatesFromVM() {
		scope.launchWhenResumed {
			viewModel.viewState.collect { state ->
				onStateChanged(state)
			}
		}
	}

	/**
	 * Clears the [ViewModel] from this [Fragment].
	 */
	private fun cleanUpViewModelFromView() {
		mutableViewModel = null
	}

}
