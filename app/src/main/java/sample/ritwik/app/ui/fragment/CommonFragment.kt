package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.View

import sample.ritwik.app.R

import sample.ritwik.app.databinding.FragmentCommonBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.ui.adapter.LibraryComponentAdapter

import sample.ritwik.common.ui.fragment.BaseFragment

/**
 * [BaseFragment] to showcase the components used in Common Library.
 *
 * @author Ritwik Jamuar
 */
class CommonFragment : BaseFragment<FragmentCommonBinding, MainModel, MainViewModel>() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [LibraryComponentAdapter] as the [androidx.recyclerview.widget.RecyclerView]'s
     * Adapter in [binding].
     */
    private var adapter: LibraryComponentAdapter? = null

    /*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override fun layoutRes(): Int = R.layout.fragment_common

    override fun extractArguments(arguments: Bundle) = Unit

    override fun initializeViews() {
        adapter = LibraryComponentAdapter()
        binding?.listComponents?.adapter = adapter
        viewModel?.fetchLibraryComponents()
    }

    override fun onAction(uiData: MainModel) = Unit

    override fun cleanUp() {
        adapter = null
        binding?.listComponents?.adapter = null
    }

    override fun showLoading() = binding?.placeholderShimmerContainer?.let { shimmer ->
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmerAnimation()
    } ?: Unit

    override fun hideLoading() = binding?.placeholderShimmerContainer?.let { shimmer ->
        shimmer.stopShimmerAnimation()
        shimmer.visibility = View.GONE
    } ?: Unit

    override fun onUIDataChanged(uiData: MainModel) = with(uiData) {
        adapter?.replaceList(libraryComponents) ?: Unit
    }

}
