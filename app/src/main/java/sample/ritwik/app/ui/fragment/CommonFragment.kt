package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sample.ritwik.app.databinding.FragmentCommonBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.ui.adapter.LibraryComponentAdapter

import com.droidboi.common.views.fragment.BaseFragment

/**
 * [BaseFragment] to showcase the components used in Common Library.
 *
 * @author Ritwik Jamuar
 */
class CommonFragment : BaseFragment<MainModel, MainViewModel, FragmentCommonBinding>() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [LibraryComponentAdapter] as the [androidx.recyclerview.widget.RecyclerView]'s
     * Adapter in [binding].
     */
    private var adapter: LibraryComponentAdapter? = null

    /*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommonBinding = FragmentCommonBinding.inflate(inflater, container, false)

    override fun provideViewRoot(binding: FragmentCommonBinding): View = binding.root

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
        shimmer.startShimmer()
    } ?: Unit

    override fun hideLoading() = binding?.placeholderShimmerContainer?.let { shimmer ->
        shimmer.stopShimmer()
        shimmer.visibility = View.GONE
    } ?: Unit

    override fun onUIDataChanged(uiData: MainModel) = with(uiData) {
        adapter?.replaceList(libraryComponents) ?: Unit
    }

}
