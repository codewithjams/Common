package sample.ritwik.app.mvvm.view

import android.content.Intent

import com.droidboi.common.views.mvvm.view.ActionActivityUI

import sample.ritwik.app.mvvm.viewModel.*

interface MainView : ActionActivityUI<MainViewModel> {

	fun setUpViews()

	fun cleanUpViews()

	fun navigateToCommonFragment()

	override fun onAction(action: Int) {
		when(action) {
			NAVIGATE_TO_COMMON_FRAGMENT -> navigateToCommonFragment()
			else -> Unit
		}
	}

	override fun extractArguments(intent: Intent) = Unit

	override fun initialize() {
		setUpViews()
	}

	override fun cleanUp() {
		cleanUpViews()
	}

}
