package sample.ritwik.app.ui.fragment

import android.view.View

import com.droidboi.common.views.mvvm.fragment.BaseMVVMFragment

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

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [LibraryComponentAdapter] that shall be used to manually clear
     * it's instance in the event of Fragment [cleanUp] so that Memory Leaks can be avoided.
	 */
	private var _adapter: LibraryComponentAdapter? = null

    /**
     * Reference of [LibraryComponentAdapter] to update the List of Libraries in the view.
     */
	private val adapter: LibraryComponentAdapter
		get() = _adapter!!

	/*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override val layoutRes: Int
        get() = R.layout.fragment_common

	override fun initializeViews() {
		_adapter = LibraryComponentAdapter()
		binding.listComponents.adapter = _adapter
		viewModel.fetchLibraryComponents()
	}

    override fun cleanUp() {
        _adapter = null
        binding.listComponents.adapter = null
        super.cleanUp()
    }

    /*-------------------------------- BaseMVVMFragment Callbacks --------------------------------*/

    override fun updateUI() {
        adapter.replaceList(viewModel.model.libraryComponents)
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
