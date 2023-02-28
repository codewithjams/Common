package sample.ritwik.app.di.activity

import dagger.Module
import dagger.Provides

import sample.ritwik.app.mvvm.model.MainModel

/**
 * [Module] of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @author Ritwik Jamuar
 */
@Module
class MainModule {

	/**
	 * Provides the Model of [sample.ritwik.app.mvvm.viewModel.MainViewModel].
	 *
	 * @return New Instance of [MainModel].
	 */
	@Provides
	fun providesModel(): MainModel = MainModel()

}
