package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope

import com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment

import sample.ritwik.app.databinding.FragmentWelcomeBinding

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.R

import sample.ritwik.app.mvvm.view.WelcomeView

/**
 * [Fragment] to showcase the Welcome screen.
 *
 * @author Ritwik Jamuar
 */
class WelcomeFragment : BaseMVVMFragment<MainViewModel, WelcomeView>(), WelcomeView {

	/*---------------------------------------- Components ----------------------------------------*/

	private var _uiStarted: Boolean = false

	private var _binding: FragmentWelcomeBinding? = null

	private val binding: FragmentWelcomeBinding
		get() = _binding!!

	/*------------------------------------ Fragment Callbacks ------------------------------------*/

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
		return binding.root
	}

	/*---------------------------------- WelcomeView Callbacks -----------------------------------*/

	override val ui: WelcomeView
		get() = this

	override val fragment: Fragment
		get() = this

	override val scope: LifecycleCoroutineScope
		get() = lifecycleScope

	override val viewModel: MainViewModel
		get() = _viewModel!!

	override var uiStarted: Boolean = _uiStarted

	override fun setUpViews() {
		binding.buttonCommon.setOnClickListener {
			viewModel.onCommonClicked()
		}
	}

	override fun cleanUpViews() {
		binding.buttonCommon.setOnClickListener(null)
		_binding = null
	}

}
