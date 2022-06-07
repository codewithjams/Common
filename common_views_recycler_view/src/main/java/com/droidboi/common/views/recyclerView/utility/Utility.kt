package com.droidboi.common.views.recyclerView.utility

import androidx.recyclerview.widget.RecyclerView

import com.droidboi.common.views.recyclerView.adapter.RecyclerAdapter

/**
 * Type-Alias as a General Purpose event notifier from [RecyclerView.ViewHolder]
 * to the [RecyclerView.Adapter].
 *
 * @author Ritwik Jamuar
 */
typealias ViewHolderListener = (Int) -> Unit

/**
 * Type-Alias as a General Purpose event notifier from [RecyclerView.Adapter]
 * to the view encompassing the [RecyclerView] itself.
 *
 * @author Ritwik Jamuar
 */
typealias AdapterListener<Model> = (Model) -> Unit

/**
 * Adds item to the [RecyclerView] through the [RecyclerView.Adapter] in such a way that no frames
 * on UI are lost to provide a smooth addition of Items.
 *
 *
 * NOTE: This method works only if the [RecyclerView.Adapter] in this [RecyclerView] is an
 * implementation of [RecyclerAdapter], otherwise this method would not do anything.
 *
 * @param Model Any Data Class that is in use with [RecyclerAdapter].
 * @param list [List] of [Model] as the Collection we want to add.
 * @param isReplace [Boolean] Flag to mark whether the Addition of [list] in this [RecyclerView]
 *   is going to replace the existing items, or add items on the top of the existing items.
 * @author Ritwik Jamuar
 */
@Suppress("UNCHECKED_CAST")
fun <Model> RecyclerView.addItems(list: List<Model>, isReplace: Boolean) = adapter?.let { a ->

	if (a is RecyclerAdapter<*>) {
		(a as? RecyclerAdapter<Model>)?.let { ad ->

			// Switch to a Runnable so that the below actions are posted on the next UI Frame
			// for smoother experience.
			post {
				if (isReplace) {
					ad.replaceList(list)
				} else {
					ad.addToList(list)
				}
			}

		}

	}

}

/**
 * Clears all the items from the [RecyclerView] through the [RecyclerView.Adapter] in such a way
 * that no frames on UI are lost to provide a smooth removal of items.
 *
 *
 * NOTE: This method works only if the [RecyclerView.Adapter] in this [RecyclerView] is an
 * implementation of [RecyclerAdapter], otherwise this method would not do anything.
 *
 * @author Ritwik Jamuar
 */
fun RecyclerView.clearItems() = adapter?.let { adapter ->

	if (adapter is RecyclerAdapter<*>) {
		post {
			adapter.clearAllItems()
		}
	}

}

/**
 * Initializes this [RecyclerView] with components you need to set in order for it to be rendered
 * in the UI.
 *
 * @param Adapter Any [RecyclerView.Adapter] as the Adapter for this [RecyclerView].
 * @param LayoutManager Any [RecyclerView.LayoutManager] as the Layout Manager
 *   for this [RecyclerView].
 * @param rvAdapter Instance of [Adapter].
 * @param rvLayoutManager Instance of [LayoutManager].
 * @param scrollListener Any [RecyclerView.OnScrollListener] to intercept the scroll
 *   on this [RecyclerView].
 * @author Ritwik Jamuar
 */
fun <Adapter : RecyclerView.Adapter<out RecyclerView.ViewHolder>, LayoutManager : RecyclerView.LayoutManager> RecyclerView.initialize(
	rvAdapter: Adapter,
	rvLayoutManager: LayoutManager,
	scrollListener: RecyclerView.OnScrollListener? = null
) {

	adapter = rvAdapter // Set the Adapter.

	layoutManager = rvLayoutManager // Set the Layout Manager.

	// Set the Scroll Listener only if it is provided.
	scrollListener?.let { listener ->
		addOnScrollListener(listener)
	}

}

/**
 * Initializes this [RecyclerView] with components you need to set in order for it to be rendered
 * in the UI.
 *
 * @param Adapter Any [RecyclerView.Adapter] as the Adapter for this [RecyclerView].
 * @param LayoutManager Any [RecyclerView.LayoutManager] as the Layout Manager
 *   for this [RecyclerView].
 * @param Decoration Any [RecyclerView.ItemDecoration] as the Item Decoration to be used
 *   in this [RecyclerView].
 * @param rvAdapter Instance of [Adapter].
 * @param rvLayoutManager Instance of [LayoutManager].
 * @param decoration Instance of [Decoration].
 * @param scrollListener Any [RecyclerView.OnScrollListener] to intercept the scroll
 *   on this [RecyclerView].
 * @author Ritwik Jamuar
 */
fun <Adapter : RecyclerView.Adapter<out RecyclerView.ViewHolder>, LayoutManager : RecyclerView.LayoutManager, Decoration : RecyclerView.ItemDecoration> RecyclerView.initialize(
	rvAdapter: Adapter,
	rvLayoutManager: LayoutManager,
	decoration: Decoration,
	scrollListener: RecyclerView.OnScrollListener? = null
) {
	initialize(rvAdapter, rvLayoutManager, scrollListener) // Use the method above.
	addItemDecoration(decoration) // Add the Item Decoration.
}

/**
 * Cleans-up any used references of [RecyclerView.Adapter], [RecyclerView.LayoutManager] and such
 * in this [RecyclerView] in order to avoid Memory Leaks.
 *
 * @author Ritwik Jamuar
 */
fun RecyclerView.cleanUp() {
	adapter = null // De-Reference the Adapter.
	layoutManager = null // De-Reference the Layout Manager.
	clearOnScrollListeners() // Clear all the Scroll Listeners.
}

/**
 * Notifies an update in [item] at the given [position].
 *
 *
 * NOTE: This method works only if the [RecyclerView.Adapter] in this [RecyclerView] is an
 * implementation of [RecyclerAdapter], otherwise this method would not do anything.
 *
 * @param T Any Data Class.
 * @param item Instance of [T] we want to update.
 * @param position [Int] denoting the position at which we want to place the [item].
 */
@Suppress("UNCHECKED_CAST")
fun <T> RecyclerView.notifyUpdateInAnItem(item: T, position: Int) = adapter?.let { adapter ->

	if (adapter is RecyclerAdapter<*>) {
		(adapter as? RecyclerAdapter<T>)?.updateItemAtPosition(item, position)
	}

}
