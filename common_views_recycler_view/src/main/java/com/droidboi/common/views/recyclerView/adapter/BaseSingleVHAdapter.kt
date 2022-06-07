package com.droidboi.common.views.recyclerView.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup

import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView

import com.droidboi.common.views.recyclerView.viewHolder.BaseViewHolder

/**
 * Abstract [RecyclerView.Adapter] for handling common set-up required for
 * any General Purpose 1 [ViewHolder] Adapter.
 *
 * @param Model Any Data Class on which [List] will be made for rendering.
 * @param ViewHolder [BaseViewHolder] as the [RecyclerView.ViewHolder] of
 *   this [BaseSingleVHAdapter].
 * @author Ritwik Jamuar
 */
abstract class BaseSingleVHAdapter<Model, ViewHolder : BaseViewHolder<out ViewDataBinding>> :
	RecyclerView.Adapter<ViewHolder>(), RecyclerAdapter<Model> {

	/*------------------------------------- Member Variables -------------------------------------*/

	/**
	 * Mutable Reference of [List] of [Model], which is bound for modification
	 * by different functions of [RecyclerAdapter].
	 */
	private val _list: ArrayList<Model> = ArrayList()

	/*------------------------------ RecyclerView.Adapter Callbacks ------------------------------*/

	override fun getItemCount(): Int = list.size

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
		provideViewHolder(parent)

	override fun onBindViewHolder(holder: ViewHolder, position: Int) =
		onBind(holder, position)

	override fun onViewAttachedToWindow(holder: ViewHolder) {
		super.onViewAttachedToWindow(holder)
		holder.markAttach()
	}

	override fun onViewDetachedFromWindow(holder: ViewHolder) {
		super.onViewDetachedFromWindow(holder)
		holder.markDetach()
	}

	/*-------------------------------------- Public Methods --------------------------------------*/

	override val list: List<Model>
		get() = _list

	@SuppressLint("NotifyDataSetChanged")
	override fun replaceList(list: List<Model>) {

		// Clear the existing list before adding new contents.
		this._list.clear()

		// Add all the contents to the existing list.
		this._list.addAll(list)

		// Notify this adapter about change in the whole data-set.
		notifyDataSetChanged()

	}

	override fun addToList(list: List<Model>) {

		// Halt the further execution if the new List is already empty.
		if (list.isEmpty()) return

		// Get the Stating Point of Insertion as the size of existing List.
		val positionOfInsertion = this._list.size

		// Modify existing List by adding new List.
		this._list.addAll(list)

		// Notify this adapter about insertion in the range.
		notifyItemRangeInserted(positionOfInsertion, this._list.size)

	}

	override fun clearAllItems() {

		// Halt the further execution if the existing List is already empty.
		if (_list.isEmpty()) return

		// Get the Ending Point of Deletion as the size of existing List.
		val positionOfDeletion = this._list.size

		// Clear the existing List.
		_list.clear()

		// Notify this adapter about deletion in the range.
		notifyItemRangeRemoved(0, positionOfDeletion)

	}

	override fun updateItemAtPosition(item: Model, position: Int) {

		// Halt the further execution if the 'position' is out of range,
		// so that ArrayIndexOutOfBoundsException can be avoided.
		if (position >= _list.size || position < 0) return

		// Replace with the 'item' at the 'position'.
		_list[position] = item

		// Notify this adapter of change in the 'position'.
		notifyItemChanged(position)

	}

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Tells this [BaseSingleVHAdapter] to provide the [ViewHolder].
	 *
	 * @param parent [ViewGroup] on which the individual [ViewHolder] resides.
	 * @return New Instance of [ViewHolder].
	 */
	protected abstract fun provideViewHolder(parent: ViewGroup): ViewHolder

	/**
	 * Tells this [BaseSingleVHAdapter] to handle the change in Data.
	 *
	 * @param viewHolder Currently rendered [ViewHolder].
	 * @param position [Int] denoting the position of [viewHolder].
	 */
	protected abstract fun onBind(viewHolder: ViewHolder, position: Int)

}
