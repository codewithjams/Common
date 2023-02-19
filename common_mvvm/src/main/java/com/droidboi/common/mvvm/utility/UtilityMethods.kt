package com.droidboi.common.mvvm.utility

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

import com.droidboi.common.mvvm.EventObserver

/**
 * Observes a given [LiveData] of [Event] of [T] in the given [lifecycleOwner]
 * with [EventObserver] and provided [listener].
 *
 * @param T Any Class
 * @param lifecycleOwner The [LifecycleOwner] which controls the observer.
 * @param listener The listener that will receive the value [T].
 */
fun <T> LiveData<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit) {
	this.observe(lifecycleOwner, EventObserver(listener))
}
