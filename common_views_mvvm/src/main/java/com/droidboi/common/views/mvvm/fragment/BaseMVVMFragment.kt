package com.droidboi.common.views.mvvm.fragment

import android.content.Context

import android.os.Bundle

import android.view.View

import androidx.fragment.app.Fragment

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.viewModel.ActionViewModel

import com.droidboi.common.views.mvvm.view.ActionActivityUI
import com.droidboi.common.views.mvvm.view.ActionFragmentUI

/**
 * Abstract [Fragment] implementing some methods of [UI] to automate many callbacks
 * in [ActionFragmentUI].
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
 * }
 *
 * class ExampleFragment : BaseMVVMFragment<ExampleViewModel, ExampleUI>(), ExampleUI {
 *
 *     override val ui: ExampleUI
 *         get() = this
 *
 *     override val fragment: Fragment
 *         get() = this
 *
 *     override val viewModel: ExampleViewModel
 *         get() = _viewModel
 *
 *     override var uiStarted: Boolean = false
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
 * @param UI Any [ActionFragmentUI] that wraps up the View's methods and fields.
 * @throws RuntimeException If the Activity of this [BaseMVVMFragment] is not an implementation of
 * [ActionActivityUI] sharing the same [ViewModel].
 * @author Ritwik Jamuar.
 */
abstract class BaseMVVMFragment<
		ViewModel : ActionViewModel<out ActionModel>,
		UI : ActionFragmentUI<ViewModel>
		> : Fragment() {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [UI] in order to automate calls for it.
	 */
	protected abstract val ui : UI

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Mutable reference of [ViewModel] so that the instance of [ViewModel] can be released
	 * based on appropriate callbacks of [Fragment].
	 *
	 *
	 * NOTE:
	 *
	 *
	 * This must be used by extending [BaseMVVMFragment] as follows:
	 * ```
	 * class ExampleFragment : BaseMVVMFragment<ExampleViewModel, ExampleUI>(), ExampleUI {
	 *
	 *     ...
	 *     ...
	 *
	 *     override val viewModel: ExampleViewModel
	 *         get() = _viewModel
	 *
	 *     ...
	 *     ...
	 *
	 * }
	 * ```
	 */
	protected var _viewModel: ViewModel? = null

	/*------------------------------------ Fragment Callbacks ------------------------------------*/

	override fun onAttach(context: Context) {

		super.onAttach(context)

		// At this point, since the context is not an instance of ActionActivityUI,
		// we would not be able to fetch the shared ViewModel from Activity.
		// Thus, we are crashing the application here.
		if (context !is ActionActivityUI<*>)
			throw RuntimeException("Activity not instance of ActionActivityUI")

		// Get the instance of shared ViewModel from Context as implementation of ActionActivityUI.
		@Suppress("UNCHECKED_CAST")
		_viewModel = (context as? ActionActivityUI<ViewModel>)?.viewModel
			// At this point, type casting the context has failed, so we crash the application
				// to notify that wrong ViewModel is being assigned.
			?: throw RuntimeException("Unable to cast to ActionActivityUI with given ViewModel")

		// Add observer of Lifecycle of Fragment as the instance of UI so that
		// UI can get appropriate callbacks on it's own.
		lifecycle.addObserver(ui)

	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		ui.onViewCreated()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		ui.onViewDestroyed()
	}

	override fun onDetach() {

		super.onDetach()

		_viewModel = null

		// Remove observer of Lifecycle of Fragment as the instance of UI so that UI don't receive
		// any more callbacks that can cause side-effects.
		lifecycle.removeObserver(ui)

	}

}
