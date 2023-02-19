package com.droidboi.common.views.mvvm.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.viewModel.ActionViewModel

import com.droidboi.common.views.core.activity.BaseActivity

import com.droidboi.common.views.mvvm.view.ActionActivityUI

/**
 * Abstract [BaseActivity] designed around MVVM Design Pattern.
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
 * interface ExampleUI : ActionActivityUI<ExampleViewModel> {
 * }
 *
 * class ExampleActivity : BaseMVVMActivity<ExampleUI>(), ExampleUI {
 *
 *     override val ui: ExampleUI
 *         get() = this
 *
 *     override val activity: AppCompatActivity
 *         get() = this
 *
 *     override val viewModel: ExampleViewModel
 *         get() = // Provide the instance of ViewModel
 *
 *     override var uiStarted: Boolean = false
 *
 *     override fun inject() = Unit
 *     override fun extractArguments(intent: Intent) = Unit
 *     override fun initialize() = Unit
 *     override fun cleanUp() = Unit
 *
 * }
 * ```
 *
 * @author Ritwik Jamuar
 * @see BaseActivity
 */
abstract class BaseMVVMActivity<UI : ActionActivityUI<out ActionViewModel<out ActionModel>>> :
	AppCompatActivity() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [UI] in order to automate calls for it.
	 */
	protected abstract val ui : UI

	/*------------------------------------ Activity Callbacks ------------------------------------*/

	override fun onCreate(savedInstanceState: Bundle?) {

		// Add observer of Lifecycle of Activity as the instance of UI so that
		// UI can get appropriate callbacks on it's own.
		lifecycle.addObserver(ui)

		super.onCreate(savedInstanceState)

	}

	override fun onDestroy() {

		super.onDestroy()

		// Remove observer of Lifecycle of Fragment as the instance of UI so that UI don't receive
		// any more callbacks that can cause side-effects.
		lifecycle.removeObserver(ui)

	}

}
