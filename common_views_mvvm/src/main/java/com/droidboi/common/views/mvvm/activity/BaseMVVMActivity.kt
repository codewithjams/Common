package com.droidboi.common.views.mvvm.activity

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.droidboi.common.mvvm.model.ActionModel

import com.droidboi.common.mvvm.viewModel.ActionViewModel

import com.droidboi.common.views.mvvm.view.ActionActivityUI

/**
 * Abstract [AppCompatActivity] designed around MVVM Design Pattern.
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
 * @see ActionActivityUI
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
		super.onCreate(savedInstanceState)
		ui.onActivityCreated()
	}

	override fun onStart() {
		super.onStart()
		ui.onUIStarted()
	}

	override fun onDestroy() {
		super.onDestroy()
		ui.onActivityDestroyed()
	}

}
