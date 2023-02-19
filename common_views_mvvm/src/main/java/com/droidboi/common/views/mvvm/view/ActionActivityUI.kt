package com.droidboi.common.views.mvvm.view

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.LifecycleOwner

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.utility.observeEvent

import com.droidboi.common.mvvm.view.ActionUI

import com.droidboi.common.mvvm.viewModel.ActionViewModel

/**
 * [ActionUI] designed for [AppCompatActivity].
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
 * @see ActionUI
 * @see com.droidboi.common.views.mvvm.activity.BaseMVVMActivity
 * @author Ritwik Jamuar
 */
interface ActionActivityUI<ViewModel : ActionViewModel<out ActionModel>> : ActionUI<ViewModel> {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * Reference of [AppCompatActivity] on which this [ActionUI] is implemented.
	 */
	val activity: AppCompatActivity

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

	override val lifecycleOwner: LifecycleOwner
		get() = activity

	override fun onCreate(lifecycleOwner: LifecycleOwner) {
		inject()
		performArgumentExtraction()
		super.onCreate(lifecycleOwner)
		initialize()
	}

	override fun onDestroy(lifecycleOwner: LifecycleOwner) {
		super.onDestroy(lifecycleOwner)
		performResourceCleanUp()
	}

	override fun attachObservers(owner: LifecycleOwner) {
		viewModel.actionEventLiveData.observeEvent(lifecycleOwner) { action ->
			handleAction(action)
		}
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
	 * Performs cleaning up the resources from [activity].
	 */
	private fun performResourceCleanUp() {
		cleanUp()
	}

}
