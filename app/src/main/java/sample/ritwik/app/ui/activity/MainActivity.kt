package sample.ritwik.app.ui.activity

import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.droidboi.common.lifecycle.VMFactory

import com.droidboi.common.views.mvvm.activity.BaseMVVMActivity

import com.squareup.picasso.Picasso

import dagger.android.AndroidInjection

import sample.ritwik.app.R

import sample.ritwik.app.databinding.ActivityMainBinding

import sample.ritwik.app.mvvm.view.MainView

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.mvvm.vmDelegate.MainViewModelDelegate

import javax.inject.Inject

/**
 * [BaseMVVMActivity] of [MainView] for demonstration of 'common' Library.
 *
 * @author Ritwik Jamuar
 */
class MainActivity : BaseMVVMActivity<MainView>(), MainView {

	/*---------------------------------------- Components ----------------------------------------*/

	private var _binding: ActivityMainBinding? = null

	private val binding: ActivityMainBinding
		get() = _binding!!

	@Inject
	lateinit var vmFactory: VMFactory

	/**
	 * Reference of [Picasso] to download and show Images from Internet.
	 */
	@Inject
	lateinit var picassoLibrary: Picasso

	/**
	 * Reference of [NavController] to perform navigation between [androidx.fragment.app.Fragment]s.
	 */
	private val navigationController: NavController by lazy {
		Navigation.findNavController(this, R.id.fragment_container)
	}

	/*------------------------------------ MainView Callbacks ------------------------------------*/

	override val ui: MainView
		get() = this

	override val activity: AppCompatActivity
		get() = this

	override val scope: LifecycleCoroutineScope
		get() = lifecycleScope

	override val viewModel: MainViewModel
		get() = ViewModelProvider(this, vmFactory)[MainViewModelDelegate::class.java]

	override var uiStarted: Boolean = false

	override fun setUpViews() {
		_binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
	}

	override fun cleanUpViews() {
		_binding = null
	}

	override fun navigateToCommonFragment() {
		navigationController.navigate(R.id.commonFragment)
	}

	override fun inject() {
		AndroidInjection.inject(this)
	}

}
