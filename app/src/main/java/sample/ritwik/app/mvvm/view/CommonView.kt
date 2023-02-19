package sample.ritwik.app.mvvm.view

import android.os.Bundle

import com.droidboi.common.views.mvvm.view.ActionFragmentUI

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.*

interface CommonView : ActionFragmentUI<MainViewModel> {

	fun updateUI(model: MainModel)
	fun toggleProgress(show: Boolean)

	override fun extractArguments(arguments: Bundle) = Unit

	override fun onAction(action: Int) {
		when(action) {
			ACTION_UPDATE_UI -> updateUI(viewModel.model)
			ACTION_SHOW_PROGRESS -> toggleProgress(true)
			ACTION_HIDE_PROGRESS -> toggleProgress(false)
			else -> Unit
		}
	}

}
