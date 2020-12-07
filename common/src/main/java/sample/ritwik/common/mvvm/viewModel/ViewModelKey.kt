package sample.ritwik.common.mvvm.viewModel

import androidx.lifecycle.ViewModel

import dagger.MapKey

import kotlin.reflect.KClass

/**
 * Annotation Class as [MapKey] for the [ViewModel] injection.
 *
 * @param value [KClass] of [ViewModel] as the Class of which [ViewModel] will be injected.
 * @author Ritwik Jamuar
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
