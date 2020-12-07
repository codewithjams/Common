package sample.ritwik.common.di

import javax.inject.Qualifier

/**
 * Annotation as a [Qualifier] to mark the particular instance
 * as Application's [android.content.Context] linked.
 *
 *
 * Most common usage is to be used within a method parameter,
 * where parameter is [android.content.Context].
 *
 * @author Ritwik Jamuar
 */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationContext
