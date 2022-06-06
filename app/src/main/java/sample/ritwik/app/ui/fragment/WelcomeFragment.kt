package sample.ritwik.app.ui.fragment

import sample.ritwik.app.databinding.FragmentWelcomeBinding

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import com.droidboi.common.views.fragment.BaseMVVMFragment

import sample.ritwik.app.R

/**
 * [BaseMVVMFragment] to showcase the Welcome screen.
 *
 * @author Ritwik Jamuar
 */
class WelcomeFragment : BaseMVVMFragment<MainViewModel, FragmentWelcomeBinding>() {

    /*---------------------------------- BaseFragment Callbacks ----------------------------------*/

    override val layoutRes: Int
        get() = R.layout.fragment_welcome

    override fun initializeViews() {
        binding.buttonCommon.setOnClickListener {
            viewModel.onCommonClicked()
        }
    }

    override fun cleanUp() {
        binding.buttonCommon.setOnClickListener(null)
        super.cleanUp()
    }

}
