package sample.ritwik.app.di.application

import dagger.BindsInstance
import dagger.Component

import dagger.android.AndroidInjectionModule

import sample.ritwik.app.application.App

import sample.ritwik.app.di.application.module.AppModule
import sample.ritwik.app.di.application.module.ComponentBinder

import sample.ritwik.app.di.application.scope.AppScope

/**
 * [Component] to facilitate all the dependencies of this application.
 *
 *
 * This components includes following [dagger.Module]s:
 * 1. [AppModule]: Provides Application Level Dependencies.
 * 2. [ComponentBinder]: Provides Activity Level Dependencies.
 * 3. [AndroidInjectionModule]: Facilitates the fulfillment dependencies to
 * Android Component Level Dependencies.
 *
 * @author Ritwik Jamuar
 */
@AppScope
@Component(
	modules = [
		AppModule::class,
		ComponentBinder::class,
		AndroidInjectionModule::class
	]
)
interface AppComponent {

	/**
	 * Provides the instance of [App] to facilitate the Dependency Injection.
	 *
	 * @param application Instance of [App].
	 */
	fun inject(application: App)

	/**
	 * [ComponentBinder] for [AppComponent] to facilitate building of [AppComponent]
	 * from the supplied [dagger.Module].
	 *
	 * @author Ritwik Jamuar
	 */
	@Component.Builder
	interface Builder {

		/**
		 * Builds the [AppComponent] from the supplied arguments in [Builder].
		 *
		 * @return New Instance of [AppComponent].
		 */
		fun build(): AppComponent

		/**
		 * Binds the instance of [App] into [AppComponent].
		 *
		 * @param application Instance of [App].
		 * @return Current Instance of [Builder].
		 */
		@BindsInstance
		fun application(application: App): Builder

	}

}
