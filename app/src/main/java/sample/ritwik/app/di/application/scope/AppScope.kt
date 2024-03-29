package sample.ritwik.app.di.application.scope

import javax.inject.Scope

/**
 * Annotation as a [Scope] to mark the scope of injection of the application.
 *
 * @author Ritwik Jamuar
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope
