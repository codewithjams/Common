package sample.ritwik.common.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import java.lang.IllegalArgumentException
import java.lang.RuntimeException

import javax.inject.Inject
import javax.inject.Provider

/**
 * Generic [ViewModel] Factory which is configured to work with Dagger 2.
 *
 * @param creators [Map] of [Class] which extends [ViewModel] and [Provider] of [ViewModel].
 * @author Ritwik Jamuar
 */
class VMFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        /*
        To create the ViewModel we are interested in as evident from 'modelClass', we are only
        concerned with the Provider of ViewModel.

        However, what we get is a Map of Class extending ViewModel and Provider of ViewModel,
        stored in the reference 'creators'.

        In the 'creators', following is the attribute:
        a) Key: Class extending ViewModel.
        b) Value: Provider of type ViewModel.
         */

        // To extract ViewModel, we fetch the Provider of type ViewModel first.
        // This can be extracted by passing 'modelClass' as key for 'creators' to get the value as
        // Provider of type ViewModel, and store this Provider in the reference 'provider'.
        val provider = creators[modelClass]

            ?:

        // If the 'provider' is not found, then we iterate every key of 'creators' and check whether
        // the 'modelClass' is assignable from the key in the iteration.
        // If above is the case, then we re-assign the 'provider' with the current key in iteration.
        creators.entries.firstOrNull() { entry ->
            modelClass.isAssignableFrom(entry.key)
        }?.value

            ?:

        // If the 'provider' is still not found, then this means the ViewModel represented as
        // 'modelClass' is not present in 'creators' at all and thus the 'modelClass' as 'ViewModel'
        // cannot be instantiated
        throw IllegalArgumentException("Unknown Model Class : $modelClass")

        // Now get the ViewModel from the 'provider'.
        try {
            @Suppress("UNCHECKED_CAST")
            return provider.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

}
