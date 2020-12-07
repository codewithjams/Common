package sample.ritwik.common.data.ui

/**
 * Data Class to represent the Data to render a [android.widget.PopupWindow] in the UI.
 *
 * @param infoText [String]      denoting the Text to be
 *   displayed in the [android.widget.PopupWindow].
 * @param autoClose [Boolean]    denoting the flag
 *   whether the [android.widget.PopupWindow] would auto-close or not.
 * @param timeOutDuration [Long] denoting the Time-Out duration of
 *   dismissing the [android.widget.PopupWindow].
 * @param showAtBottom [Boolean] denoting the flag that decides
 *   whether the [android.widget.PopupWindow] will be shown at the bottom or not.
 * @param isDisplayed [Boolean]  denoting the flag indication
 *   whether the [android.widget.PopupWindow] is currently displayed in the UI or not.
 * @param state                  [State] denoting the
 *   indicator as the type of [android.widget.PopupWindow].
 * @author Ritwik Jamuar
 */
data class PopUpData(
    var infoText: String = "",
    var autoClose: Boolean = false,
    var timeOutDuration: Long = 0L,
    var showAtBottom: Boolean = false,
    var isDisplayed: Boolean = false,
    var state: State = State.NORMAL
) {

    /*------------------------------------- Object Callbacks -------------------------------------*/

    override fun toString(): String = infoText

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Checks whether the [android.widget.PopupWindow] is displayable or not
     * based on the given data.
     *
     * @return true if the [android.widget.PopupWindow] is displayable, else false.
     */
    fun isDisplayable(): Boolean = infoText.isNotEmpty()

    /**
     * Resets the value of all the members of [PopUpData].
     */
    fun resetAllData() {
        infoText = ""
        autoClose = false
        timeOutDuration = 0L
        showAtBottom = false
        isDisplayed = true
        state = State.NORMAL
    }

    /*-------------------------------------- Inner Classes ---------------------------------------*/

    /**
     * Enumeration of different States of Pop-Up Message.
     *
     * @author Ritwik Jamuar
     */
    enum class State {

        /**
         * [State] denoting the Normal type of Pop-Up.
         */
        NORMAL,

        /**
         * [State] denoting the Warning type of Pop-Up.
         */
        WARNING,

        /**
         * [State] denoting the Error type of Pop-Up.
         */
        ERROR

    }

}
