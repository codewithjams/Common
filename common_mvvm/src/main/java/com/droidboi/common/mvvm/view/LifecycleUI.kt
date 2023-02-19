package com.droidboi.common.mvvm.view

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Custom [LifecycleEventObserver] to receive callbacks of [Lifecycle]
 * from supplied [LifecycleOwner].
 *
 * @author Ritwik Jamuar
 */
interface LifecycleUI : LifecycleEventObserver {

	/*------------------------------------- Abstract Fields --------------------------------------*/

	/**
	 * [Boolean] flag to determine if the UI is already started or not.
	 */
	var uiStarted: Boolean

	/*----------------------------- LifecycleEventObserver Callbacks -----------------------------*/

	override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
		when(event) {
			Lifecycle.Event.ON_CREATE -> onCreate(source)
			Lifecycle.Event.ON_START -> handleStart(source)
			Lifecycle.Event.ON_RESUME -> onResume(source)
			Lifecycle.Event.ON_PAUSE -> onPaused(source)
			Lifecycle.Event.ON_STOP -> onStop(source)
			Lifecycle.Event.ON_DESTROY -> onDestroy(source)
			Lifecycle.Event.ON_ANY -> return
		}
	}

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Triggers when the UI is created.
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	fun onCreate(lifecycleOwner: LifecycleOwner) {
	}

	/**
	 * Triggers when the UI is started.
	 *
	 *
	 * NOTE:
	 *
	 *
	 * This method designed to execute exactly once.
	 * If you wish to make [onStart] trigger whenever [Lifecycle.Event.ON_START] is triggered
	 * as opposed to exactly once:
	 *
	 *
	 * Make the implementation of [uiStarted] to return false always. Something like:
	 * ```
	 * override var uiStarted: Boolean
	 *     get() = false
	 *     set(value) {
	 *         // Do nothing
	 *     }
	 * ```
	 *
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 * @see uiStarted
	 */
	fun onStart(lifecycleOwner: LifecycleOwner) {
	}

	/**
	 * Triggers when the UI is resumed.
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	fun onResume(lifecycleOwner: LifecycleOwner) {
	}

	/**
	 * Triggers when the UI is paused.
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	fun onPaused(lifecycleOwner: LifecycleOwner) {
	}

	/**
	 * Triggers when the UI is stopped.
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	fun onStop(lifecycleOwner: LifecycleOwner) {
	}

	/**
	 * Triggers when the UI is destroyed.
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	fun onDestroy(lifecycleOwner: LifecycleOwner) {
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Handles the Lifecycle Event [Lifecycle.Event.ON_START].
	 *
	 * @param lifecycleOwner [LifecycleOwner] denoting the source of UI.
	 */
	private fun handleStart(lifecycleOwner: LifecycleOwner) {

		// We don't want to re-trigger onStart() callback.
		// So, if the UI is already started, we halt the further execution here.
		if (uiStarted)
			return

		// At this stage, the UI has never been started, so we trigger the onStart() callback.
		onStart(lifecycleOwner)

		// Now that onStart() callback has been shipped, we can now mark the UI as started.
		uiStarted = true

	}

}
