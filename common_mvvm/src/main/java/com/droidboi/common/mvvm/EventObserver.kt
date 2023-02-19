package com.droidboi.common.mvvm

import androidx.lifecycle.Observer

import com.droidboi.common.mvvm.utility.Event

/**
 * Custom [Observer] of [Event] of [T] to make sure that observation of [T] is done exactly once.
 *
 *
 * Problems with just using [Observer]:
 *
 *
 * Consider values of a [androidx.lifecycle.LiveData] is being observed by an [Observer]
 * which resides in a [androidx.lifecycle.LifecycleOwner] (Activity/Fragment) and such.
 *
 *
 * When [androidx.lifecycle.LifecycleOwner] goes to [androidx.lifecycle.Lifecycle.Event.ON_PAUSE]
 * state and comes back to [androidx.lifecycle.Lifecycle.Event.ON_RESUME] state, the value in the
 * [Observer] will be emitted again.
 *
 *
 * Another scenario: When [androidx.lifecycle.LifecycleOwner] is restarted, in that case
 * the transition will happen from [androidx.lifecycle.Lifecycle.Event.ON_DESTROY] to
 * [androidx.lifecycle.Lifecycle.Event.ON_CREATE], again leading to existing value
 * being emitted again.
 *
 *
 * Upon observation, if some code is set to be executed, because of this transition, those code
 * will execute again, which in some case is not desirable.
 *
 *
 * To make sure that the value is emitted exactly once, then in that case we can mae the use of
 * [Event]. It is designed to emit [T] exactly once, due to the method
 * [Event.getContentIfNotHandled].
 *
 *
 * With this, we control that we always emit non-`null` values.
 *
 * @param T Any Data Class
 * @param listener Propagates the value [T] only when the value is not handled yet.
 * @author Ritwik Jamuar
 * @see Event
 */
class EventObserver<T>(private val listener : (T) -> Unit) : Observer<Event<T>> {

	/*------------------------------------ Observer Callbacks ------------------------------------*/

	override fun onChanged(event: Event<T>?) {

		// If the value propagated from LiveData is null itself, then there's nothing to propagate.
		// Thus, halt the further execution here.
		if (event == null)
			return

		val data = event.getContentIfNotHandled() // Get the value from Event.

			// If the data fetched from Event is null, thhis means that the value is already handled
			// or the value passed from Event itself is null.
			// In that case, we don't propagate value at all.
			// Thus we halt the further execution here.
			?: return

		// At this point, the value is not handled yet.
		// This means that we should propagate this value to the consumer.
		listener.invoke(data)

	}

}
