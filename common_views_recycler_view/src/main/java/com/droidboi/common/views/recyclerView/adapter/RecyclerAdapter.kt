package com.droidboi.common.views.recyclerView.adapter

/**
 * Marks any implementation an abstraction of a [androidx.recyclerview.widget.RecyclerView.Adapter].
 *
 *
 * We need to manipulate the [list] in one way or another to achieve desired results.
 * So here, placed some common methods that shall be implemented by a RecyclerView.Adapter
 * regardless of how many RecyclerView.ViewHolder(s) it is serving.
 *
 * @param Model Any Data Class that shall be rendered.
 *
 * @author Ritwik Jamuar
 */
interface RecyclerAdapter<Model> {

	/**
	 * Non-Mutable Reference of [List] of [Model],
	 * which contains all the [Model] items to be rendered.
	 */
	val list: List<Model>

	/**
	 * Replaces the content of [list] with new content from parameter
	 * and notifies the change in [list] to this [BaseSingleVHAdapter].
	 *
	 * @param list New [List] of [Model].
	 */
	fun replaceList(list: List<Model>)

	/**
	 * Appends the [list] with new content from parameters and notifies the change in [list]
	 * to this [BaseSingleVHAdapter].
	 *
	 * @param list New [List] of [Model] to be appended to [list].
	 */
	fun addToList(list: List<Model>)

	/**
	 * Clears the [list] and notifies the change in the [list] to this [BaseSingleVHAdapter].
	 */
	fun clearAllItems()

	/**
	 * Updates a given [item] in the [list].
	 *
	 * @param item Instance of [Model] which we want to replace with.
	 * @param position [Int] denoting the Position in the [list] where we want to place this [item].
	 */
	fun updateItemAtPosition(item: Model, position: Int)

}
