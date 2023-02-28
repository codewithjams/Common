package com.droidboi.common.mvvm.view

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * View of the [ViewModel] onto which the Actions are propagated and this propagates its events
 * to the [ViewModel].
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
 * Create a new View Interface.
 * ```
 * interface ExampleView : ActionView<ExampleViewModel>
 * ```
 *
 * @param ViewModel Any [ActionViewModel] capable of propagating [Int] as Action Codes.
 * @author Ritwik Jamuar
 */
interface ActionView<ViewModel : ActionViewModel<out ActionModel>> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * [ViewModel] to handle the UI events of this [ActionView].
	 */
	val viewModel: ViewModel

	var uiStarted: Boolean

	/*------------------------------------- Abstract Methods -------------------------------------*/

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
	 * by this [ActionView].
	 */
	fun onAction(action: Int)

	/*----------------------------------- LifecycleUI Callbacks ----------------------------------*/

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Starts collecting the new [Int] as Action Codes from [viewModel].
	 *
	 *
	 * Usage:
	 * ```
	 * data class ExampleModel(): ActionModel
	 *
	 * class ExampleViewModel(private val model: ActionModel):
	 *     ViewModel(), ActionViewModel<ActionModel>
	 *
	 * interface ExampleView : ActionView<ExampleViewModel>
	 *
	 * class ExampleActivity : AppCompatActivity(), ExampleView {
	 *
	 *     override val viewModel: ExampleViewModel
	 *         get() = // Provide the ViewModel
	 *
	 *     override fun onCreate(savedInstanceState: Bundle?) {
	 *         lifecycleScope.launchWhenResumed {
	 *             // Do remember to call this method, otherwise
	 *             // the method onAction() would never invoke
	 *             collectActions()
	 *         }
	 *     }
	 *
	 *     override fun onAction(action: Int) {
	 *         // Handle Action Codes
	 *     }
	 *
	 * }
	 * ```
	 *
	 * @param duplicatesAllowed [Boolean] flag to configure if collecting already received values
	 *   is allowed. Default is `false`.
	 */
	suspend fun collectActions(duplicatesAllowed: Boolean = false) {
		viewModel.actionEventFlow.collect { event ->

			if (duplicatesAllowed) {
				handleAction(event.peekContent())
				return@collect
			}

			event.getContentIfNotHandled()?.let { action ->
				handleAction(action)
			}

		}
	}

	fun onUIStarted(overrideDefaultBehaviour: Boolean = false) {

		if (overrideDefaultBehaviour) {
			viewModel.onUIStarted()
			return
		}

		if (uiStarted)
			return

		viewModel.onUIStarted()

		uiStarted = true

	}

	/**
	 * Handles the [action] propagated by the [ViewModel].
	 *
	 * @param action [Int] as the identification of Action that shall be executed
	 * by this [ActionView].
	 */
	fun handleAction(action: Int) {
		onAction(action)
	}

}
