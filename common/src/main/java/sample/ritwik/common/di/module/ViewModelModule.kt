package sample.ritwik.common.di.module

import androidx.lifecycle.ViewModel

import dagger.Module
import dagger.Provides

import sample.ritwik.common.mvvm.viewModel.VMFactory

import javax.inject.Provider

/**
 * [Module] of [VMFactory].
 *
 *
 * This [Module] is responsible for providing the new instance of [VMFactory]
 * for every [android.app.Activity].
 *
 *
 * Since, every [android.app.Activity] will have
 * different [sample.ritwik.common.mvvm.viewModel.BaseViewModel], so [VMFactory] will be provided
 * multiple times for every [android.app.Activity]
 * that requests through [dagger.android.ContributesAndroidInjector].
 *
 * @author Ritwik Jamuar
 */
@Module
class ViewModelModule {

    /**
     * Provides the new instance of [VMFactory].
     *
     * @param creators [Map] of [Class] extending [ViewModel] and [Provider] of [ViewModel] from any
     *   [android.app.Activity] [Provides] method
     *   annotated with [dagger.android.ContributesAndroidInjector]
     *   and [ViewModel]'s [Provides] annotated with [dagger.Binds], [dagger.multibindings.IntoMap]
     *   and [sample.ritwik.common.mvvm.viewModel.ViewModelKey].
     * @return New Instance of [VMFactory].
     */
    @Provides
    fun providesVMFactory(
        creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
    ): VMFactory = VMFactory(creators)

}
