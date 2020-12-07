package sample.ritwik.common.ui.viewHolder

import androidx.databinding.ViewDataBinding

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

import androidx.recyclerview.widget.RecyclerView

/**
 * Abstract [RecyclerView.ViewHolder] for handling common set-up required for
 * any General Purpose Lifecycle Aware [RecyclerView.ViewHolder].
 *
 * @param DataBinding [ViewDataBinding] referencing the Data Binding Class
 *   of this [RecyclerView.ViewHolder].
 * @param binding Instance of [DataBinding].
 * @author Ritwik Jamuar
 */
abstract class BaseViewHolder<DataBinding : ViewDataBinding>(
    protected val binding: DataBinding
) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [LifecycleRegistry] to control the Lifecycle of this [BaseViewHolder].
     */
    private lateinit var lifecycleRegistry: LifecycleRegistry

    /*------------------------------------ Initializer Block -------------------------------------*/

    init {
        initialize()
    }

    /*----------------------------------- Lifecycle Callbacks ------------------------------------*/

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Notifies that this [BaseViewHolder] is attached to the Window.
     */
    fun markAttach() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        initializeComponents()
    }

    /**
     * Notifies that this [BaseViewHolder] is detached from the Window.
     */
    fun markDetach() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        cleanUp()
    }

    /*-------------------------------------- Private Methods -------------------------------------*/

    /**
     * Initializes components of this [BaseViewHolder].
     */
    private fun initialize() {
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
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
