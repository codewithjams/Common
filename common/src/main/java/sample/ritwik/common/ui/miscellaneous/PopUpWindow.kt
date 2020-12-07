package sample.ritwik.common.ui.miscellaneous

import android.content.Context

import android.graphics.Rect

import android.graphics.drawable.ColorDrawable

import android.util.TypedValue

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import android.widget.PopupWindow

import androidx.core.content.ContextCompat

import androidx.databinding.DataBindingUtil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import sample.ritwik.common.R

import sample.ritwik.common.data.ui.PopUpData

import sample.ritwik.common.databinding.ViewPopUpWindowBinding

import sample.ritwik.common.ui.activity.BaseActivity

import sample.ritwik.common.utility.helper.hideSoftInput

/**
 * Custom Class that encapsulates a [PopupWindow] along with it's setup to display it in the UI.
 *
 * @param context [Context] on which the [PopupWindow] us displayed.
 * @author Ritwik Jamuar
 */
class PopUpWindow constructor(private val context: Context) {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [PopupWindow] responsible for displaying it in the UI.
     */
    private lateinit var window: PopupWindow

    /**
     * [androidx.databinding.ViewDataBinding] of the custom layout of [PopUpWindow.window].
     */
    private val binding: ViewPopUpWindowBinding by lazy { provideBinding() }

    /**
     * Reference of Nullable [View] that is the [View] of the Toolbar.
     */
    private var toolbarView: View? = null

    /*------------------------------------- Member Variables -------------------------------------*/

    /**
     * Reference of [PopUpData] to contain all the Data related to showing the [PopUpWindow.window].
     */
    private lateinit var popUpData: PopUpData

    /*-------------------------------------- View Listeners --------------------------------------*/

    /**
     * [View.OnClickListener] to intercept click on Close button.
     */
    private val closeClickListener = View.OnClickListener {
        dismiss()
    }

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Shows the [window] in the UI with following provisions:
     * 1. To Auto-Close, pass the value of [PopUpData.autoClose] under [PopUpData] as true
     * with a value of [PopUpData.timeOutDuration] greater than 0,
     * which should represent the time in milli-seconds.
     * 2. To show the [PopUpWindow.window] in the Top,
     * set the value of [PopUpData.showAtBottom] under [PopUpWindow] as false,
     * and to show at the bottom, set the value as true.
     * 3. The [PopUpWindow.window] can be shown in the three states under [PopUpData.State].
     * To set the state, set the value of [PopUpData.state] under [PopUpData].
     */
    fun show(popUpData: PopUpData, toolbarView: View? = null) {

        // Halt the further execution if the window is not displayable as determined by PopUpData.
        if (popUpData.isDisplayable()) return

        // Assign the variables.
        this.popUpData = popUpData
        this.toolbarView = toolbarView

        // Show the 'window'.
        show()

    }

    /**
     * Dismisses the [window].
     */
    fun dismiss() {

        // Halt the further execution if the window is not initialized.
        if (!::window.isInitialized) return

        // Dismiss the window.
        window.dismiss()

    }

    /*------------------------------------- Private Methods --------------------------------------*/

    /**
     * Takes care of showing the [window] using [binding].
     */
    private fun show() {

        if (isPopUpShowing()) {
            dismiss() // Dismiss the already shown 'window'.
        }

        // Checks whether the 'window' is initialized or not.
        if (!this::window.isInitialized) {

            // Instantiate and set-up the Pop-Up Window.
            window = PopupWindow(context).apply {
                contentView = binding.root
                width = LinearLayout.LayoutParams.MATCH_PARENT
                height = LinearLayout.LayoutParams.WRAP_CONTENT
                isFocusable = false
                setBackgroundDrawable(ColorDrawable(context.resources.getColor(android.R.color.transparent)))
                isOutsideTouchable = true
                animationStyle = R.style.PopUpWindowAnimation
            }

            // Set the click-listener on Close Button.
            binding.buttonClose.setOnClickListener(closeClickListener)

        }

        // Set the background color of the Root View of Pop-Up depending on the given state to show.
        binding.rootPopUp.setBackgroundColor(
            ContextCompat.getColor(
                context,
                when (popUpData.state) {
                    PopUpData.State.NORMAL -> R.color.color_pop_up_state_normal
                    PopUpData.State.WARNING -> R.color.color_pop_up_state_warning
                    PopUpData.State.ERROR -> R.color.color_pop_up_state_error
                }
            )
        )

        // Update the State of 'window'.
        window.update()

        // Set the text of Informational Text in the view of Pop-Up Window.
        binding.valueTextMessage.text = popUpData.infoText

        // Show the PopupWindow
        showPopUpAtLocation(popUpData.showAtBottom)

        if (popUpData.autoClose) {

            // Dismiss the 'window' after a given duration.
            CoroutineScope(Dispatchers.Main).launch {
                delay(popUpData.timeOutDuration)
                dismiss()
            }

        }

    }

    /**
     * Checks whether the [window] is displayed in the UI or not.
     *
     * @return false if the [window] is not initialized or [window] is not shown in the UI,
     *   else true.
     */
    private fun isPopUpShowing(): Boolean = ::window.isInitialized && window.isShowing

    /**
     * Provides the [binding].
     *
     * @return New Instance of [ViewPopUpWindowBinding].
     */
    private fun provideBinding(): ViewPopUpWindowBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.view_pop_up_window,
        if (context is BaseActivity<*, *, *>) {
            context.binding.root.parent as ViewGroup
        } else {
            null
        },
        false
    )

    /**
     * Displays the [window] either on top or bottom of the screen.
     *
     * @param showAtBottom [Boolean] flag to identify whether the display request is
     *   for Bottom or for Top.
     */
    private fun showPopUpAtLocation(showAtBottom: Boolean) {

        val (gravity, height) = if (showAtBottom) {
            (context as? BaseActivity<*, *, *>)?.hideSoftInput(null)
            Pair(Gravity.BOTTOM, 0)
        } else {
            Pair(Gravity.TOP, getToolbarHeight(TypedValue()))
        }

        // Show the 'window' at the specified location in the UI.
        window.showAtLocation(
            window.contentView,
            gravity,
            0,
            height
        )

    }

    /**
     * Determines the height of [toolbarView] rendered in the UI.
     *
     * @return [Int] denoting the height of [toolbarView], if found in the UI,
     *   else the height of Status Bar, or height of any other header.
     */
    private fun getToolbarHeight(typedValue: TypedValue): Int {

        // Initialize the variables.
        val statusBar = getStatusBarHeight()
        var actionBar = 0
        var otherHeader = 0

        try {

            if (context.theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)) {
                // Calculate the height of default ActionBar from Android.
                actionBar = TypedValue.complexToDimensionPixelSize(
                    typedValue.data,
                    context.resources.displayMetrics
                )
            }

            // Check whether the context is BaseActivity or not.
            if (context is BaseActivity<*, *, *>) {

                // Initialize a Rectangle that acts as Scrolling Boundary.
                val scrollBounds = Rect()

                // Set the 'scrollBounds' in the Root View of 'context'.
                context.binding.root.rootView.getHitRect(scrollBounds)

                // Point of Exception.
                // We are finding a very specific identifier in the DataBinding of the context.
                // That id is not necessarily to be found in the context that holds the DataBinding.
                // So, we may get exception at this point to access a identifier which is not present.
                if (otherHeader == 0 && toolbarView?.getGlobalVisibleRect(scrollBounds) == true) {

                    // Set the height of Other Header as the bottom dimension of Scrolling Boundary.
                    otherHeader = scrollBounds.bottom

                }


                return if (otherHeader == 0) {

                    // If the height of the Other Header in the UI is 0,
                    // this means that the Other Header does not exist in the UI.
                    // So, instead provide the height of the Toolbar as that of Status Bar.
                    statusBar

                } else {

                    // If the height of the Other Header in the UI is not 0,
                    // this means that the Other Header exists in the UI.
                    // So provide the height of Other Header as the height of the Toolbar.
                    otherHeader

                }

            }

        } catch (e: Exception) {

            // If there was an exception caused, then we set the height of ActionBar as 0.
            actionBar = 0

        }

        // Return the summation of height of Action Bar and Status Bar.
        return actionBar + statusBar

    }

    /**
     * Determines the height of Status Bar in the UI.
     *
     * @return [Int] denoting the height of Status Bar.
     */
    private fun getStatusBarHeight(): Int = with(context.resources) {

        // Get the identifier of Status Bar from Android's Default Package.
        val resourceID = getIdentifier("status_bar_height", "dimen", "android")

        // Check whether the resourceID exists or not,
        // i.e. by checking whether 'resourceID' is greater than 0 or not.
        if (resourceID > 0) {
            getDimensionPixelSize(resourceID) // Return the Dimension Pixel Size of the found identifier.
        } else {
            0 // Return 0 since the identifier is not found.
        }

    }

}
