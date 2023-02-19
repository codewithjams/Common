package com.droidboi.common.mvvm.view

import androidx.lifecycle.LifecycleOwner

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * [LifecycleUI] that triggers Action propagated from the [ViewModel].
 *
 *
 * How to use?:
 *
 *
 * Be ready with the required MVVM components first.
 * ```
 * data class ExampleModel(): ActionModel
 *
 * class ExampleViewModel(private val model: ActionModel): ViewModel(), ActionViewModel<ActionModel>
 * ```
 *
 *
 * Create a new UI Interface.
 * ```
 * interface ExampleUI : ActionUI<ExampleViewModel>
 * ```
 *
 * If the UI interface is used with an [android.app.Activity], then add these lines along the method
 * [android.app.Activity.onCreate] and [android.app.Activity.onDestroy]:
 * ```
 * class ExampleActivity : AppCompatActivity(), ExampleUI {
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         lifecycle.addObserver(this)
 *         super.onCreate(savedInstanceState)
 *     }
 *
 *     override fun onDestroy() {
 *         super.onDestroy()
 *         lifecycle.removeObserver(this)
 *     }
 *
 * }
 * ```
 *
 *
 * If the UI interface is used with a Fragment, then add these lines under below methods
 * of Fragment:
 * ```
 * class ExampleFragment : Fragment(), ExampleUI {
 *
 *     override fun onAttach(context: Context) {
 *         lifecycle.addObserver(this)
 *         super.onAttach(context)
 *     }
 *
 *     override fun onDetach() {
 *         super.onDetach()
 *         lifecycle.removeObserver(this)
 *     }
 *
 * }
 * ```
 *
 * @param ViewModel Any [ActionViewModel] capable of propagating [Int] as Action Codes.
 * @author Ritwik Jamuar
 */
interface ActionUI<ViewModel : ActionViewModel<out ActionModel>> : LifecycleUI {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * [ViewModel] to handle the UI events from this [LifecycleUI].
	 */
	val viewModel: ViewModel

	/**
	 * [LifecycleOwner] of UI.
	 */
	val lifecycleOwner: LifecycleOwner

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Tells this [LifecycleUI] to attach observers for the [androidx.lifecycle.LiveData]
	 * included in [ViewModel] as well as in other places.
	 *
	 *
	 * Usage:
	 *
	 *
	 * With [ActionViewModel.actionEventLiveData]:
	 * ```
	 * override fun attachObservers(owner: LifecycleOwner) {
	 *     viewModel.actionEventLiveData.observe(this) { event ->
	 *         event.getContentIfNotHandled()?.let { action ->
	 *             handleAction(action)
	 *         }
	 *     }
	 * }
	 * ```
	 *
	 *
	 * With [ActionViewModel.actionLiveData]:
	 * ```
	 * override fun attachObservers(owner: LifecycleOwner) {
	 *     viewModel.actionLiveData.observe(this) { action ->
	 *         handleAction(action)
	 *     }
	 * }
	 * ```
	 *
	 * @param owner Instance of [LifecycleOwner] that owns this [LifecycleUI].
	 * @see ActionViewModel.actionEventLiveData
	 * @see ActionViewModel.actionLiveData
	 */
	fun attachObservers(owner: LifecycleOwner)

	/**
	 * Execute the Action triggered by the [ViewModel].
	 *
	 *
	 * Usage:
	 * ```
	 * override fun onAction(action: Int) {
	 *     when(action) {
	 *         SOME_ACTION_CODE_1 -> executeSomeAction1()
	 *         SOME_ACTION_CODE_2 -> executeSomeAction2()
	 *         ... -> ...
	 *         else -> Unit
	 *     }
	 * }
	 * ```
	 *
	 * @param action [Int] as the identification of Action that shall be executed
	 * by this [LifecycleUI].
	 */
	fun onAction(action: Int)

	/*----------------------------------- LifecycleUI Callbacks ----------------------------------*/

	override fun onCreate(lifecycleOwner: LifecycleOwner) {
		attachObservers(lifecycleOwner)
	}

	override fun onStart(lifecycleOwner: LifecycleOwner) {
		viewModel.onUIStarted()
	}

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Handles the [action] propagated by the [ViewModel].
	 *
	 * @param action [Int] as the identification of Action that shall be executed
	 * by this [LifecycleUI].
	 */
	fun handleAction(action: Int) {
		onAction(action)
	}

}
