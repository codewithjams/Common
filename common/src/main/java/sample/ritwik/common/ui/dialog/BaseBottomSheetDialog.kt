package sample.ritwik.common.ui.dialog

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.DialogFragment

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Abstract [BottomSheetDialogFragment] for handling common set-up required
 * to show a [BottomSheetDialogFragment] in the UI.
 *
 * @param DataBinding [ViewDataBinding] referencing the Data Binding class
 *   of this [BottomSheetDialogFragment].
 * @author Ritwik Jamuar
 */
abstract class BaseBottomSheetDialog<DataBinding : ViewDataBinding> : BottomSheetDialogFragment() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [DataBinding] to control the Views under it.
     */
    protected lateinit var binding: DataBinding

    /*--------------------------------- DialogFragment Callbacks ---------------------------------*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            extractArguments(it)
        }
    }

    override fun onStart() {
        super.onStart()
        setUpWindowDimension()
        setUpWindowBackground()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListeners()
    }

    /*------------------------------------- Protected Methods ------------------------------------*/

    /**
     * Sets the dimensions of [android.view.Window] of this [DialogFragment].
     */
    protected fun setUpWindowDimension() = Unit

    /**
     * Sets the background of [android.view.Window] of this [DialogFragment].
     */
    protected fun setUpWindowBackground() = Unit

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Provides the Layout XML of this [DialogFragment].
     *
     * @return [Int] denoting the [LayoutRes].
     */
    @LayoutRes
    protected abstract fun layoutRes(): Int

    /**
     * Tells this [DialogFragment] to extract the arguments from [Bundle].
     *
     *
     * This method will be executed only if the [Bundle] argument was set during the instantiation
     * of this [DialogFragment].
     *
     * @param arguments [Bundle] that contains the arguments.
     */
    protected abstract fun extractArguments(arguments: Bundle)

    /**
     * Tells this [DialogFragment] to perform initialization of it's [View]s through [binding].
     */
    protected abstract fun initializeViews()

    /**
     * Tells this [DialogFragment] to perform Clean-Up procedures for avoiding Memory Leaks.
     */
    protected abstract fun cleanUp()

    /**
     * Tells the extending [DialogFragment] to remove it's listeners
     * for preventing any [NullPointerException] when the parent [View] is destroyed.
     */
    protected abstract fun removeListeners()

}
