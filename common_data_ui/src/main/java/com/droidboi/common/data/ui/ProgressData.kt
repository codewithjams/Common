package com.droidboi.common.data.ui

/**
 * Interface that marks an implementation to hold data related to Progress Dialog.
 *
 * @author Ritwik Jamuar
 */
interface ProgressData : UIData {

    /**
     * [Boolean] Flag to tell whether Progress should be shown or not.
     */
    var showProgress: Boolean

    /**
     * [Boolean] Flag indicating whether display of Progress is cancelable in any way or not.
     */
    var isCancelable: Boolean

}
