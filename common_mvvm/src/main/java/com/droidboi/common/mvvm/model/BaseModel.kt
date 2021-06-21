package com.droidboi.common.mvvm.model

import com.droidboi.common.data.ui.PopUpData
import com.droidboi.common.data.ui.ProgressData

import com.droidboi.common.mvvm.data.ErrorData

import com.droidboi.common.mvvm.utility.ACTION_NONE

/**
 * Abstract Model to contain common data related to UI.
 *
 * @param action [Integer] denoting the identifier of the Action.
 * @param progressData Instance of [ProgressData].
 * @param errorData Instance of [ErrorData].
 * @param popUpData Instance of [PopUpData].
 * @author Ritwik Jamuar
 */
abstract class BaseModel(
    var action: Int = ACTION_NONE,
    var progressData: ProgressData = ProgressData(),
    var errorData: ErrorData = ErrorData(),
    var popUpData: PopUpData = PopUpData()
)
