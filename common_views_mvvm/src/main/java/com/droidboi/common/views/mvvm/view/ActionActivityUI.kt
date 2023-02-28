package com.droidboi.common.views.mvvm.view

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.LifecycleCoroutineScope

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.view.ActionView

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * [ActionView] designed for [AppCompatActivity].
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
 * interface ExampleUI : ActionActivityUI<ExampleViewModel> {
 * }
 *
 * class ExampleActivity : AppCompatActivity(), ExampleUI {
 *
 *     override val activity: AppCompatActivity
 *         get() = this
 *
 *     override val viewModel: ExampleViewModel
 *         get() = // Provide the instance of ViewModel
 *
 *     override var uiStarted: Boolean = false
 *
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *
 *         // Attaching Observer is important as this will enable all other
 *         // callbacks of ExampleUI.
 *         lifecycle.addObserver(this)
 *
 *         super.onCreate(savedInstanceState)
 *
 *     }
 *
 *     override fun onDestroy() {
 *
 *         super.onDestroy()
 *
 *         // Removing Observer is also important as this will disable all callbacks
 *         // of ExampleUI to avoid any side-effects.
 *         lifecycle.removeObserver(this)
 *
 *     }
 *
 *     override fun inject() = Unit
 *     override fun extractArguments(intent: Intent) = Unit
 *     override fun initialize() = Unit
 *     override fun cleanUp() = Unit
 *
 * }
 * ```
 *
 * @param ViewModel Any [ActionViewModel] capable of propagating [Int] as Action Codes.
 * @see ActionView
 * @see com.droidboi.common.views.mvvm.activity.BaseMVVMActivity
 * @author Ritwik Jamuar
 */
interface ActionActivityUI<ViewModel : ActionViewModel<out ActionModel>> : ActionView<ViewModel> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [AppCompatActivity] on which this [ActionView] is implemented.
	 */
	val activity: AppCompatActivity

	/**
	 * [LifecycleCoroutineScope] to launch some task, whose lifecycle is attached to
	 * that of an [android.app.Activity].
	 */
	val scope: LifecycleCoroutineScope

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
	 * Cleans-up any allocated resources to avoid Memory Leaks.
	 */
	fun cleanUp()

	/*------------------------------------ ActionUI Callbacks ------------------------------------*/

	/*-------------------------------------- Public Methods --------------------------------------*/

	fun onActivityCreated() {
		inject()
		performArgumentExtraction()
		collectActionsFromVM()
		initialize()
	}

	fun onActivityStarted() {
		viewModel.onUIStarted()
	}

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
	 * Collects the [com.droidboi.common.mvvm.utility.Event] emitted from [ViewModel] as an some
	 * action to be performed in this [ActionView].
	 */
	private fun collectActionsFromVM() {
		scope.launchWhenResumed {
			collectActions()
		}
	}

	/**
	 * Performs cleaning up the resources from [activity].
	 */
	private fun performResourceCleanUp() {
		cleanUp()
	}

}
