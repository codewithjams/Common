package sample.ritwik.app.ui.activity

import androidx.databinding.DataBindingUtil

import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.droidboi.common.data.ui.PopUpData

import com.droidboi.common.mvvm.data.ErrorData

import com.droidboi.common.mvvm.viewModelFactory.VMFactory

import com.droidboi.common.utility.networkCallback.data.NetworkType

import com.droidboi.common.utility.networkCallback.helper.NetworkUtils

import com.squareup.picasso.Picasso

import dagger.android.AndroidInjection

import sample.ritwik.app.R

import sample.ritwik.app.databinding.ActivityMainBinding

import sample.ritwik.app.mvvm.model.MainModel

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.utility.constant.NAVIGATE_TO_COMMON_FRAGMENT

import com.droidboi.common.views.activity.BaseActivity

import javax.inject.Inject

/**
 * [BaseActivity] for demonstration of 'common' Library.
 *
 * @author Ritwik Jamuar
 */
class MainActivity : BaseActivity<MainModel, MainViewModel, ActivityMainBinding>() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [VMFactory] of this [BaseActivity]
     * injected from [com.droidboi.common.mvvm.di.module.ViewModelModule].
     */
    @Inject
    lateinit var vmFactory: VMFactory

    /**
     * Reference of [NetworkUtils], injected from [sample.ritwik.common.di.module.CommonModule].
     */
    @Inject
    lateinit var networkUtility: NetworkUtils

    /**
     * Reference of [Picasso] injected from [sample.ritwik.common.di.module.PicassoModule].
     */
    @Inject
    lateinit var picassoLibrary: Picasso

    /**
     * Reference of [NavController] to perform navigation between [androidx.fragment.app.Fragment]s.
     */
    private val navigationController: NavController by lazy {
        Navigation.findNavController(this, R.id.fragment_container)
    }

    /*---------------------------------- BaseActivity Callbacks ----------------------------------*/

    override fun provideViewModel(): MainViewModel =
        ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

    override fun provideBinding(): ActivityMainBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main)

    override val networkUtils: NetworkUtils
        get() = networkUtility

    override fun inject() = AndroidInjection.inject(this)

    override fun showLoading() = Unit

    override fun hideLoading() = Unit

    override fun showPopUpDialog(popUpData: PopUpData) = Unit

    override fun dismissPopUpDialog() = Unit

    override fun showError(errorData: ErrorData) = Unit

    override fun extractArguments() = Unit

    override fun initialize() = Unit

    override fun attachObservers() = Unit

    override fun onNetworkChanged(isNetworkAvailable: Boolean, networkType: NetworkType) = Unit

    override fun onUIDataChanged(uiData: MainModel) = Unit

    override fun onAction(uiData: MainModel) = when(uiData.action) {

        NAVIGATE_TO_COMMON_FRAGMENT -> navigationController.navigate(R.id.commonFragment)

        else -> Unit

    }

    override fun cleanUp() = Unit

}
