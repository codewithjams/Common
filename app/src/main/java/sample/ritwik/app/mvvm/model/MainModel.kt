package sample.ritwik.app.mvvm.model

import com.droidboi.common.mvvm.model.BaseModel

import sample.ritwik.app.data.ui.LibraryComponent

import javax.inject.Inject

/**
 * Model Class of [sample.ritwik.app.ui.activity.MainActivity].
 *
 * @author Ritwik Jamuar
 */
class MainModel @Inject constructor() : BaseModel() {

    /*------------------------------------- Member Variables -------------------------------------*/

    lateinit var libraryComponents: List<LibraryComponent>

    /*-------------------------------------- Public Methods --------------------------------------*/

    /**
     * Checks whether [libraryComponents] is populated or not,
     * by checking whether it is initialized as well as it is not empty.
     *
     * @return true, if [libraryComponents] is initialized and is not empty, else false.
     */
    fun isComponentsPopulated(): Boolean =
        this::libraryComponents.isInitialized && libraryComponents.isNotEmpty()

}
