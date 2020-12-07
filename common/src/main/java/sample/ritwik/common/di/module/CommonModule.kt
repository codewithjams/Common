package sample.ritwik.common.di.module

import android.content.Context

import android.net.ConnectivityManager

import android.telephony.TelephonyManager

import dagger.Module
import dagger.Provides

import sample.ritwik.common.di.AppScope

import sample.ritwik.common.utility.helper.NetworkUtils
import sample.ritwik.common.utility.helper.ResourceUtils
import sample.ritwik.common.utility.helper.ViewUtils

/**
 * [Module] for the 'common' Android Library.
 *
 *
 * This [Module] is responsible for exposing all the dependants from 'common' Android Library.
 *
 *
 * This [Module] includes following [Module] annotated classes:
 * 1. [GsonModule]: To provide [com.google.gson.Gson].
 * 2. [PersistenceModule]: To provide
 * [sample.ritwik.common.component.persistence.DataStorePreference].
 * 3. [PicassoModule]: To provide the [com.squareup.picasso.Picasso].
 * 4. [RetrofitModule]: To provide the [retrofit2.Retrofit].
 * 5. [ViewModelModule]: To provide the [sample.ritwik.common.mvvm.viewModel.VMFactory].
 *
 *
 * Along with the dependencies on the above, this [Module] also provides following:
 * 1. Instance of [NetworkUtils].
 * 2. Instance of Application's [ViewUtils].
 *
 * @author Ritwik Jamuar
 */
@Module(
    includes = [
        GsonModule::class,
        PersistenceModule::class,
        PicassoModule::class,
        RetrofitModule::class,
        ViewModelModule::class
    ]
)
class CommonModule {

    /**
     * Provides the instance of [NetworkUtils].
     *
     * @param context Instance of Application's [Context].
     * @return New Instance of [NetworkUtils].
     */
    @AppScope
    @Provides
    fun providesNetworkUtils(context: Context): NetworkUtils = NetworkUtils(
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager,
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    )

    /**
     * Provides the instance of [ViewUtils].
     *
     * @param context Instance of Application's [Context].
     * @return New Instance of [ViewUtils].
     */
    @AppScope
    @Provides
    fun providesViewUtils(context: Context): ViewUtils = ViewUtils(context)

    /**
     * Provides the instance of [ResourceUtils].
     *
     * @param context Instance of Application's [Context].
     * @return New Instance of [ResourceUtils].
     */
    @AppScope
    @Provides
    fun providesResourceUtils(context: Context): ResourceUtils = ResourceUtils(context)

}
