package sample.ritwik.common.data.ui

/**
 * Data Class to represent the data of Progress Dialog.
 *
 * @param showProgress [Boolean] to denote whether the Progress Dialog should be shown or not.
 * @param isCancelable [Boolean] to denote whether the Progress Dialog is cancelable in nature or not.
 * @author Ritwik Jamuar
 */
data class ProgressData(
    var showProgress: Boolean = false,
    var isCancelable: Boolean = false
) {

    /*------------------------------------- Object Callbacks -------------------------------------*/

    override fun toString(): String = "$showProgress"

}
