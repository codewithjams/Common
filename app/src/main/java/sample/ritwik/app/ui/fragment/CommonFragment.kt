package sample.ritwik.app.ui.fragment

import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager

import com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment

import com.droidboi.common.views.recyclerView.utility.addItems
import com.droidboi.common.views.recyclerView.utility.cleanUp
import com.droidboi.common.views.recyclerView.utility.initialize

import sample.ritwik.app.R

import sample.ritwik.app.databinding.FragmentCommonBinding

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.ui.adapter.LibraryComponentAdapter

import sample.ritwik.app.utility.constant.ACTION_HIDE_PROGRESS
import sample.ritwik.app.utility.constant.ACTION_SHOW_PROGRESS

/**
 * [BaseMVVMFragment] to showcase the components used in Common Library.
 *
 * @author Ritwik Jamuar
 */
class CommonFragment : BaseMVVMFragment<MainViewModel, FragmentCommonBinding>() {

	/*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override val layoutRes: Int
        get() = R.layout.fragment_common

	override fun initializeViews() {
		binding.listComponents.initialize(
			LibraryComponentAdapter(),
			LinearLayoutManager(context)
		)
		viewModel.fetchLibraryComponents()
	}

    override fun cleanUp() {
        binding.listComponents.cleanUp()
        super.cleanUp()
    }

    /*-------------------------------- BaseMVVMFragment Callbacks --------------------------------*/

    override fun updateUI() {
		binding.listComponents.addItems(viewModel.model.libraryComponents, true)
    }

	override fun onAction(action: Int) = when (action) {
		ACTION_SHOW_PROGRESS -> showLoading()
		ACTION_HIDE_PROGRESS -> hideLoading()
		else -> Unit
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
