package sample.ritwik.app.ui.activity

import androidx.appcompat.app.AppCompatActivity

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope

import androidx.activity.compose.setContent

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

import androidx.compose.ui.tooling.preview.Preview

import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.droidboi.common.lifecycle.VMFactory

import com.droidboi.common.views.mvvm.activity.BaseMVVMActivity

import com.squareup.picasso.Picasso

import dagger.android.AndroidInjection

import sample.ritwik.app.R

import sample.ritwik.app.mvvm.view.MainView

import sample.ritwik.app.mvvm.viewModel.MainViewModel

import sample.ritwik.app.mvvm.vmDelegate.MainViewModelDelegate

import sample.ritwik.app.ui.theme.TestTheme

import javax.inject.Inject

/**
 * [BaseMVVMActivity] of [MainView] for demonstration of 'common' Library.
 *
 * @author Ritwik Jamuar
 */
class MainActivity : BaseMVVMActivity<MainView>(), MainView {

	/*---------------------------------------- Components ----------------------------------------*/

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
		setContent {
			TestTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					Greeting("Droid")
				}
			}
		}
	}

	override fun cleanUpViews() {
	}

	override fun navigateToCommonFragment() {
		navigationController.navigate(R.id.commonFragment)
	}

	override fun inject() {
		AndroidInjection.inject(this)
	}

}

@Composable
fun Greeting(name: String) {
	Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	TestTheme {
		Greeting("Android")
	}
}
