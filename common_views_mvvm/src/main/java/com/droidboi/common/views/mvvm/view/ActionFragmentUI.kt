package com.droidboi.common.views.mvvm.view

import android.os.Bundle

import androidx.fragment.app.Fragment

import androidx.lifecycle.LifecycleCoroutineScope

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.view.ActionView

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * [ActionView] designed for [Fragment].
 *
 *
 * Usage:
 * ```
 * data class ExampleModel(): ActionModel {
 * }
 *
 * interface ExampleViewModel: ActionViewModel<ExampleModel> {
 *     ...
 *     ...
 * }
 *
 * interface ExampleUI : ActionFragmentUI<ExampleViewModel> {
 *     ...
 *     ...
 * }
 *
 * class ExampleFragment : Fragment(), ExampleUI {
 *
 *     private var _viewModel: ExampleViewModel? = null
 *
 *     override val fragment: Fragment
 *         get() = this
 *
 *     override val viewModel: ExampleViewModel
 *         get() = _viewModel
 *
 *     override var uiStarted: Boolean = false
 *
 *     override fun onAttach(context: Context) {
 *
 *         super.onAttach(context)
 *
 *         // Write code here to instantiate the _viewModel fetched from context
 *         // as AppCompatActivity
 *         _viewModel = ...
 *
 *         // Attaching Observer is important as this will enable all other
 *         // callbacks of ExampleUI.
 *         lifecycle.addObserver(this)
 *
 *     }
 *
 *     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
 *         super.onViewCreated(view, savedInstanceState)
 *         ui.onViewCreated()
 *     }
 *
 *     override fun onDestroyView() {
 *         super.onDestroyView()
 *         ui.onViewDestroyed()
 *     }
 *
 *     override fun onDetach() {
 *
 *         super.onDetach()
 *
 *         _viewModel = null
 *
 *         // Removing Observer is also important as this will disable all callbacks
 *         // of ExampleUI to avoid any side-effects.
 *         lifecycle.removeObserver(this)
 *
 *     }
 *
 *     override fun extractArguments(arguments: Bundle) = Unit
 *     override fun setUpViews() = Unit
 *     override fun initialize() = Unit
 *     override fun cleanUpViews() = Unit
 *
 * }
 * ```
 *
 * @param ViewModel Any [ActionViewModel] capable of propagating [Int] as Action Codes.
 * @see ActionView
 * @see com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment
 * @author Ritwik Jamuar
 */
interface ActionFragmentUI<ViewModel : ActionViewModel<out ActionModel>> : ActionView<ViewModel> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [Fragment] on which this [ActionView] is implemented.
	 */
	val fragment: Fragment

	/**
	 * [LifecycleCoroutineScope] to launch some task, whose lifecycle is attached to
	 * that of an [androidx.fragment.app.Fragment].
	 */
	val scope: LifecycleCoroutineScope

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Extracts the arguments from this [fragment].
	 *
	 * @param arguments [Bundle] encapsulating all the arguments passed to this [fragment] UI.
	 */
	fun extractArguments(arguments: Bundle)

	/**
	 * Sets-up the view under this [fragment].
	 */
	fun setUpViews()

	/**
	 * Cleans-up the views under this [fragment].
	 */
	fun cleanUpViews()

	/**
	 * Initializes any components or start some execution.
	 */
	fun initialize()

	/*------------------------------------ ActionUI Callbacks ------------------------------------*/

	/*-------------------------------------- Public Methods --------------------------------------*/

	fun onFragmentCreated() {
		performArgumentExtraction()
	}

	/**
	 * Handle the workflow when the view of [fragment] is created.
	 */
	fun onViewCreated() {

		// At this point, since the view is now created, it's safe to access `lifecycleOwner` now.
		collectActionsFromVM()

		setUpViews()
		initialize()

	}

	/**
	 * Handle the workflow when the view of [fragment] is destroyed.
	 */
	fun onViewDestroyed() {
		cleanUpViews()
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Performs extracting [Bundle] arguments from [fragment].
	 */
	private fun performArgumentExtraction() {
		fragment.arguments?.let { arguments ->
			extractArguments(arguments)
		}
	}

	/**
	 * Collects the [com.droidboi.common.mvvm.utility.Event] emitted from [ViewModel] as an some
	 * action to be performed in this [ActionView].
	 */
	private fun collectActionsFromVM() {
		scope.launchWhenResumed {
			collectActions()
		}
	}

}
