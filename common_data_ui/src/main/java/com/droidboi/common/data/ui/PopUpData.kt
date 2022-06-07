package com.droidboi.common.data.ui

/**
 * Interface that marks an implementation to hold data related to Pop-Up Window.
 *
 * @author Ritwik Jamuar
 */
interface PopUpData {

    /**
     * [String] denoting the Text to be displayed in the Popup Message.
     */
    var infoText: String

    /**
     * [Boolean] denoting the flag whether the Popup Message would auto-close or not.
     */
    var autoClose: Boolean

    /**
     * [Long] denoting the Time-Out duration of dismissing the Popup Message.
     */
    var timeOutDuration: Long

    /**
     * [Boolean] denoting the flag that decides whether the Popup Message will be shown
     * at the bottom or not.
     */
    var showAtBottom: Boolean

    /**
     * [Boolean] denoting the flag indication whether the Popup Message is currently displayed
     * in the UI or not.
     */
    var isDisplayed: Boolean

    /**
     * [State] denoting the indicator as the type of Popup Message.
     */
    var state: State

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Checks whether the Popup Message is displayable or not based on the given data.
     *
     * @return true if the PopupWindow is displayable, else false.
     */
    fun isPopUpDisplayable(): Boolean = infoText.isNotEmpty()

    /**
     * Resets the value of all the members of [PopUpData].
     */
    fun resetPopUpData() {
        infoText = ""
        autoClose = false
        timeOutDuration = 0L
        showAtBottom = false
        isDisplayed = true
        state = State.NORMAL
    }

    fun setValues(
        message: String,
        isAutoClose: Boolean = false,
        isShowAtBottom: Boolean = false,
        timeOutBeforeClosing: Long = 0L,
        displayed: Boolean = true,
        newState: State = State.NORMAL
    ) {
        infoText = message
        autoClose = isAutoClose
        showAtBottom = isShowAtBottom
        timeOutDuration = timeOutBeforeClosing
        isDisplayed = displayed
        state = newState
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
