package sample.ritwik.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import sample.ritwik.app.data.ui.LibraryComponent

import sample.ritwik.app.databinding.ItemLibraryComponentBinding

import sample.ritwik.app.ui.viewHolder.LibraryComponentViewHolder

import sample.ritwik.common.ui.adapter.BaseSingleVHAdapter

/**
 * [BaseSingleVHAdapter] to render [LibraryComponentViewHolder]
 * in the [androidx.recyclerview.widget.RecyclerView].
 *
 * @author Ritwik Jamuar
 */
class LibraryComponentAdapter :
    BaseSingleVHAdapter<LibraryComponent, LibraryComponentViewHolder>() {

    /*------------------------------- BaseSingleVHAdapter Callbacks ------------------------------*/

    override fun provideViewHolder(parent: ViewGroup): LibraryComponentViewHolder =
        LibraryComponentViewHolder(
            ItemLibraryComponentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBind(viewHolder: LibraryComponentViewHolder, position: Int) =
        viewHolder.setItem(list[position])

}
