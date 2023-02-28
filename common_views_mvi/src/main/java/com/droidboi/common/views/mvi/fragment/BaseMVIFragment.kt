package com.droidboi.common.views.mvi.fragment

import android.content.Context

import android.os.Bundle

import android.view.View

import androidx.fragment.app.Fragment

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.MVIViewModel

import com.droidboi.common.views.mvi.activity.BaseMVIActivity

import com.droidboi.common.views.mvi.view.StateFragmentView

/**
 * Abstract [Fragment] designed around MVI Design Pattern.
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
 * class ExampleFragment :
 *     BaseMVIFragment<ExampleState, ExampleViewModel, ExampleView>(), ExampleFragmentView {
 *
 *     ...
 *
 *     override val ui: ExampleFragmentView
 *         get() = this
 *
 *     override var mutableViewModel: ExampleViewModel? = null
 *
 *     override val fragment: Fragment
 *         get() = this
 *
 *     ...
 *
 * }
 * ```
 *
 * @param S A [State] of the view on the screen.
 * @param ViewModel [MVIViewModel] as the ViewModel of the [BaseMVIActivity]
 *   that hosts this [Fragment].
 * @param UI Any [StateFragmentView] that wraps up the View's methods and fields.
 * @author Ritwik Jamuar
 */
abstract class BaseMVIFragment<
		S : State,
		ViewModel : MVIViewModel<S, out Action>,
		UI : StateFragmentView<S, ViewModel>
		> : Fragment() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [UI] in order to automate calls for it.
	 */
	abstract val ui : UI

	/*---------------------------------- BaseFragment Callbacks ----------------------------------*/

	override fun onAttach(context: Context) {
		super.onAttach(context)
		ui.onFragmentAttached(context)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ui.onFragmentCreated()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		ui.onFragmentViewCreated()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		ui.onFragmentViewDestroyed()
	}

	override fun onDestroy() {
		super.onDestroy()
		ui.onFragmentDestroyed()
	}

	override fun onDetach() {
		super.onDetach()
		ui.onFragmentDetached()
	}

}
