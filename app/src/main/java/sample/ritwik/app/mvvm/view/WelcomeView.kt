package sample.ritwik.app.mvvm.view

import android.os.Bundle

import com.droidboi.common.views.mvvm.view.ActionFragmentUI

import sample.ritwik.app.mvvm.viewModel.MainViewModel

interface WelcomeView : ActionFragmentUI<MainViewModel> {

	override fun extractArguments(arguments: Bundle) = Unit

	override fun initialize() = Unit

	override fun onAction(action: Int) = Unit

}
