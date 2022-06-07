package sample.ritwik.app.ui.activity

import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.droidboi.common.lifecycle.VMFactory

import com.squareup.picasso.Picasso

import dagger.android.AndroidInjection

import sample.ritwik.app.R

import sample.ritwik.app.databinding.ActivityMainBinding

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.utility.constant.NAVIGATE_TO_COMMON_FRAGMENT

import com.droidboi.common.views.mvvm.activity.BaseMVVMActivity

import javax.inject.Inject

/**
 * [BaseMVVMActivity] for demonstration of 'common' Library.
 *
 * @author Ritwik Jamuar
 */
class MainActivity : BaseMVVMActivity<MainViewModel, ActivityMainBinding>() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [VMFactory] of this [BaseMVVMActivity]
	 * injected from [com.droidboi.common.lifecycle.di.module.ViewModelModule].
	 */
	@Inject
	lateinit var vmFactory: VMFactory

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

	override val layoutRes: Int
		get() = R.layout.activity_main

	override fun inject() = AndroidInjection.inject(this)

	/*-------------------------------- BaseMVVMActivity Callbacks --------------------------------*/

	override val viewModel: MainViewModel
		get() = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]

	override fun onAction(action: Int) = when(action) {
		NAVIGATE_TO_COMMON_FRAGMENT -> navigationController.navigate(R.id.commonFragment)
		else -> Unit
	}

}
