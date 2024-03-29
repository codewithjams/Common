package sample.ritwik.app.application

import android.app.Activity
import android.app.Application

import androidx.appcompat.app.AppCompatDelegate

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector

import sample.ritwik.app.di.application.DaggerAppComponent

import javax.inject.Inject

/**
 * Represents [Application] Class of this Application.
 *
 * @author Ritwik Jamuar
 */
class App : Application(), HasAndroidInjector {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Android Injector for [Activity].
	 */
	@Inject
	lateinit var activityInjector: DispatchingAndroidInjector<Any>

	/*------------------------------------ Initializer Block -------------------------------------*/

	init {

		// Enable Vector Resources Globally.
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

	}

	/*---------------------------------- Application Callbacks -----------------------------------*/

	override fun onCreate() {
		super.onCreate()
		initialize()
	}

	/*------------------------------ HasActivityInjector Callbacks -------------------------------*/

	override fun androidInjector(): AndroidInjector<Any> = activityInjector

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Takes care of initialization of this [Application].
	 */
	private fun initialize() {
		initializeComponents()
	}

	/**
	 * Initializes the component of the [App].
	 */
	private fun initializeComponents() {
		DaggerAppComponent.builder().application(this).build().inject(this)
	}

}
