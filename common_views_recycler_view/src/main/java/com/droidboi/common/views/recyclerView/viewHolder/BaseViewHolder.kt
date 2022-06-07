package com.droidboi.common.views.recyclerView.viewHolder

import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * Abstract [RecyclerView.ViewHolder] for handling common set-up required for
 * any General Purpose [RecyclerView.ViewHolder].
 *
 * @param Binding Any Class from which Views under this [RecyclerView.ViewHolder] are accessible.
 * @param rootView Instance of [View] that denotes the View used to render
 *   this individual [RecyclerView.ViewHolder].
 * @author Ritwik Jamuar
 */
abstract class BaseViewHolder<Binding>(rootView: View) : RecyclerView.ViewHolder(rootView) {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [Binding] as the Data/View Binding Class to access all the views under it.
	 */
	protected abstract val binding: Binding

	/*-------------------------------------- Public Methods --------------------------------------*/

	/**
	 * Notifies that this [BaseViewHolder] is attached to the Window.
	 */
	fun markAttach() {
		initializeComponents()
	}

	/**
	 * Notifies that this [BaseViewHolder] is detached from the Window.
	 */
	fun markDetach() {
		cleanUp()
	}

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Tells this [BaseViewHolder] to initialize it's components.
	 */
	protected abstract fun initializeComponents()

	/**
	 * Tells this [BaseViewHolder] to perform Clean-Up procedures for avoiding Memory Leak.
	 */
	protected abstract fun cleanUp()

}
