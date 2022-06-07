package sample.ritwik.common.di.module

import com.droidboi.common.lifecycle.di.module.ViewModelModule

import com.droidboi.common.persistence.di.PersistenceModule

import com.droidboi.common.utility.di.UtilityModule

import dagger.Module

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
 * [com.droidboi.common.persistence.dataStore.DataStorePreference].
 * 3. [PicassoModule]: To provide the [com.squareup.picasso.Picasso].
 * 4. [RetrofitModule]: To provide the [retrofit2.Retrofit].
 * 5. [ViewModelModule]: To provide the [com.droidboi.common.lifecycle.VMFactory].
 *
 *
 * Along with the dependencies on the above, this [Module] also provides following:
 * 1. Instance of [com.droidboi.common.utility.networkCallback.helper.NetworkUtils].
 * 2. Instance of Application's [com.droidboi.common.utility.views.helper.ViewUtils].
 *
 * @author Ritwik Jamuar
 */
@Module(
	includes = [
		GsonModule::class,
		PersistenceModule::class,
		PicassoModule::class,
		RetrofitModule::class,
		UtilityModule::class,
		ViewModelModule::class
	]
)
class CommonModule
