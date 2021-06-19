package sample.ritwik.app.ui.viewHolder

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.databinding.ItemLibraryComponentBinding

import com.droidboi.common.views.recyclerView.viewHolder.BaseViewHolder

/**
 * [BaseViewHolder] to render an individual [LibraryComponent].
 *
 * @param binding Instance of [ItemLibraryComponentBinding] as the Data Binding Class of this
 *   [BaseViewHolder].
 * @author Ritwik Jamuar
 */
class LibraryComponentViewHolder(
     override val binding: ItemLibraryComponentBinding
) : BaseViewHolder<ItemLibraryComponentBinding>(binding.root) {

    /*--------------------------------- BaseViewHolder Callbacks ---------------------------------*/

    override fun initializeComponents() = Unit

    override fun cleanUp() = Unit

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Sets the [libraryComponent] to the [binding].
     *
     * @param libraryComponent Instance of [LibraryComponent].
     */
    fun setItem(libraryComponent: LibraryComponent) {
        binding.item = libraryComponent
    }

}
