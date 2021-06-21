package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sample.ritwik.app.databinding.FragmentWelcomeBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import com.droidboi.common.views.fragment.BaseFragment

/**
 * [BaseFragment] to showcase the Welcome screen.
 *
 * @author Ritwik Jamuar
 */
class WelcomeFragment : BaseFragment<MainModel, MainViewModel, FragmentWelcomeBinding>() {

    /*-------------------------------------- View Listeners --------------------------------------*/

    /**
     * [View.OnClickListener] to intercept click on button 'More Info about Common Library'.
     */
    private val commonClickListener = View.OnClickListener {
        viewModel?.onCommonClicked()
    }

    /*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding =
        FragmentWelcomeBinding.inflate(inflater, container, false)

    override fun provideViewRoot(binding: FragmentWelcomeBinding): View = binding.root

    override fun extractArguments(arguments: Bundle) = Unit

    override fun initializeViews() {
        binding?.buttonCommon?.setOnClickListener(commonClickListener)
    }

    override fun showLoading() = Unit

    override fun hideLoading() = Unit

    override fun onAction(uiData: MainModel) = Unit

    override fun onUIDataChanged(uiData: MainModel) = Unit

    override fun cleanUp() {
        binding?.buttonCommon?.setOnClickListener(null)
    }

}
