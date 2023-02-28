package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope

import androidx.recyclerview.widget.LinearLayoutManager

import com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment

import com.droidboi.common.views.recyclerView.utility.addItems
import com.droidboi.common.views.recyclerView.utility.cleanUp
import com.droidboi.common.views.recyclerView.utility.initialize

import sample.ritwik.app.R

import sample.ritwik.app.databinding.FragmentCommonBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.view.CommonView

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.ui.adapter.LibraryComponentAdapter

/**
 * [Fragment] to showcase the components used in Common Library.
 *
 * @author Ritwik Jamuar
 */
class CommonFragment : BaseMVVMFragment<MainViewModel, CommonView>(), CommonView {

	/*---------------------------------------- Components ----------------------------------------*/

	private var _binding: FragmentCommonBinding? = null

	private val binding: FragmentCommonBinding
		get() = _binding!!

	/*------------------------------------ Fragment Callbacks ------------------------------------*/

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common, container, false)
		return binding.root
	}

    /*----------------------------------- CommonView Callbacks -----------------------------------*/

	override val ui: CommonView
		get() = this

	override val fragment: Fragment
		get() = this

	override val scope: LifecycleCoroutineScope
		get() = lifecycleScope

	override val viewModel: MainViewModel
		get() = _viewModel!!

	override var uiStarted: Boolean = false

	override fun setUpViews() {
		binding.listComponents.initialize(
			LibraryComponentAdapter(),
			LinearLayoutManager(context)
		)
	}

	override fun initialize() {
		viewModel.fetchLibraryComponents()
	}

	override fun cleanUpViews() {
		binding.listComponents.cleanUp()
		_binding = null
	}

    override fun updateUI(model: MainModel) {
		binding.listComponents.addItems(model.libraryComponents, true)
    }

	override fun toggleProgress(show: Boolean) {
		if (show) {
			showLoading()
		} else {
			hideLoading()
		}
	}

	/*------------------------------------- Private Methods --------------------------------------*/

    /**
     * Shows the Loading Animation.
     */
    private fun showLoading() = binding.placeholderShimmerContainer.let { shimmer ->
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    /**
     * Hides the Loading Animation.
     */
    private fun hideLoading() = binding.placeholderShimmerContainer.let { shimmer ->
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
    }

}
