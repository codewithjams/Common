package sample.ritwik.common.ui.adapter

import android.view.ViewGroup

import androidx.databinding.ViewDataBinding

import androidx.recyclerview.widget.RecyclerView

import sample.ritwik.common.ui.viewHolder.BaseViewHolder

/**
 * Abstract [RecyclerView.Adapter] for handling common set-up required for
 * any General Purpose 1 [ViewHolder] Adapter.
 *
 * @param Model Any Data Class on which [List] will be made for rendering.
 * @param ViewHolder [BaseViewHolder] as the [RecyclerView.ViewHolder] of
 *   this [BaseSingleVHAdapter].
 * @author Ritwik Jamuar
 */
abstract class BaseSingleVHAdapter<
        Model,
        ViewHolder : BaseViewHolder<out ViewDataBinding>
        >: RecyclerView.Adapter<ViewHolder>() {

    /*------------------------------------- Member Variables -------------------------------------*/

    /**
     * Reference of [List] of [Model], which contains all the [Model] items to be rendered to the
     * [ViewHolder].
     */
    protected val list: ArrayList<Model> = ArrayList()

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

    /**
     * Replaces the content of [list] with new content from parameter
     * and notifies the change in [list] to this [BaseSingleVHAdapter].
     *
     * @param list New [List] of [Model].
     */
    fun replaceList(list: List<Model>) {

        // Clear the existing list before adding new contents.
        this.list.clear()

        // Add all the contents to the existing list.
        this.list.addAll(list)

        // Notify this adapter about change in the whole data-set.
        notifyDataSetChanged()

    }

    /**
     * Appends the [list] with new content from parameters and notifies the change in [list]
     * to this [BaseSingleVHAdapter].
     *
     * @param list New [List] of [Model] to be appended to [list].
     */
    fun addToList(list: List<Model>) {

        // Halt the further execution if the new List is already empty.
        if (list.isEmpty()) return

        // Get the Stating Point of Insertion as the size of existing List.
        val positionOfInsertion = this.list.size

        // Modify existing List by adding new List.
        this.list.addAll(list)

        // Notify this adapter about insertion in the range.
        notifyItemRangeInserted(positionOfInsertion, this.list.size)

    }

    /**
     * Clears the [list] and notifies the change in the [list] to this [BaseSingleVHAdapter].
     */
    fun clearAllItems() {

        // Halt the further execution if the existing List is already empty.
        if (list.isEmpty()) return

        // Get the Ending Point of Deletion as the size of existing List.
        val positionOfDeletion = this.list.size

        // Clear the existing List.
        list.clear()

        // Notify this adapter about deletion in the range.
        notifyItemRangeRemoved(0, positionOfDeletion)

    }

    /**
     * Gets the immutable [List] of [Model].
     *
     * @return Current Instance of [list].
     */
    fun getList(): List<Model> = list

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
