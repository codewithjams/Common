package com.droidboi.common.data.ui

/**
 * Data Class to represent the Data to render a Popup Message in the UI.
 *
 * @param infoText [String]      denoting the Text to be displayed in the Popup Message.
 * @param autoClose [Boolean]    denoting the flag whether the Popup Message would auto-close
 *   or not.
 * @param timeOutDuration [Long] denoting the Time-Out duration of dismissing the Popup Message .
 * @param showAtBottom [Boolean] denoting the flag that decides whether the Popup Message
 *   will be shown at the bottom or not.
 * @param isDisplayed [Boolean]  denoting the flag indication whether the Popup Message is
 *   currently displayed in the UI or not.
 * @param state                  [State] denoting the indicator as the type of Popup Message.
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
     * Checks whether the Popup Message is displayable or not based on the given data.
     *
     * @return true if the PopupWindow is displayable, else false.
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
