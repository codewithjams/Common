package sample.ritwik.app.ui.fragment

import android.os.Bundle

import android.view.View

import sample.ritwik.app.R

import sample.ritwik.app.databinding.FragmentWelcomeBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.common.ui.fragment.BaseFragment

/**
 * [BaseFragment] to showcase the Welcome screen.
 *
 * @author Ritwik Jamuar
 */
class WelcomeFragment : BaseFragment<FragmentWelcomeBinding, MainModel, MainViewModel>() {

    /*-------------------------------------- View Listeners --------------------------------------*/

    /**
     * [View.OnClickListener] to intercept click on button 'More Info about Common Library'.
     */
    private val commonClickListener = View.OnClickListener {
        viewModel?.onCommonClicked()
    }

    /*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override fun layoutRes(): Int = R.layout.fragment_welcome

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
