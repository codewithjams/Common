package com.droidboi.common.views.mvvm.view

import android.os.Bundle

import androidx.fragment.app.Fragment

import androidx.lifecycle.LifecycleOwner

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.view.ActionUI

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * [ActionUI] designed for [Fragment].
 *
 *
 * NOTE:
 *
 *
 * This [ActionUI] is to be used in conjunction with
 * [com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment] as this abstract class performs
 * following internal operations:
 * 1. Extracting [ViewModel] from [ActionActivityUI].
 * 2. Managing Callbacks for when View is created or destroyed.
 *
 *
 * Usage:
 *
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
 * @see ActionUI
 * @see com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment
 * @author Ritwik Jamuar
 */
interface ActionFragmentUI<ViewModel : ActionViewModel<out ActionModel>> : ActionUI<ViewModel> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [Fragment] on which this [ActionUI] is implemented.
	 */
	val fragment: Fragment

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

	override val lifecycleOwner: LifecycleOwner
		get() = fragment.viewLifecycleOwner

	override fun onCreate(lifecycleOwner: LifecycleOwner) {

		performArgumentExtraction()

		// Not calling super.onCreate(lifecycleOwner) because it will call
		// the method `attachObserver(owner)` which subsequently access the abstract variable
		// `lifecycleOwner`.

		// Now, for Fragment, we are getting Fragment's View Lifecycle Owner
		// as the main LifecycleOwner for our ActionUI, and this View's Lifecycle Owner
		// is not available until the View is created.

		// And, at this stage, accessing View's Lifecycle Owner causes IllegalStateException
		// with below message:
		// Caused by: java.lang.IllegalStateException:
		// Can't access the Fragment View's LifecycleOwner when getView() is null
		// i.e., before onCreateView() or after onDestroyView()

		// So, now we are refraining to call `super.onCreate(lifecycleOwner)`.

	}

	override fun attachObservers(owner: LifecycleOwner) {
		viewModel.actionLiveData.observe(owner) { action ->
			handleAction(action)
		}
	}

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Handle the workflow when the view of [fragment] is created.
	 */
	fun onViewCreated() {

		// At this point, since the view is now created, it's safe to access `lifecycleOwner` now.
		attachObservers(lifecycleOwner)

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

}
