package com.droidboi.common.views.mvi.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.droidboi.common.mvi.Action
import com.droidboi.common.mvi.State

import com.droidboi.common.mvi.viewModel.MVIViewModel

import com.droidboi.common.views.mvi.view.StateActivityView

/**
 * Abstract [AppCompatActivity] designed around MVI Design Pattern to complement
 * any given [StateActivityView].
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
 *     ...
 *
 * }
 * ```
 *
 * @param UI Any [StateActivityView] acting as the UI abstraction of this [AppCompatActivity].
 * @author Ritwik Jamuar
 * @see StateActivityView
 */
abstract class BaseMVIActivity<
		UI : StateActivityView<out State, out MVIViewModel<out State, out Action>>
		> : AppCompatActivity() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [UI] in order to automate calls for it.
	 */
	abstract val ui: UI

	/*------------------------------------ Activity Callbacks ------------------------------------*/

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		ui.onActivityCreated()
	}

	override fun onStart() {
		super.onStart()
		ui.onActivityStarted()
	}

	override fun onDestroy() {
		super.onDestroy()
		ui.onActivityDestroyed()
	}

}
