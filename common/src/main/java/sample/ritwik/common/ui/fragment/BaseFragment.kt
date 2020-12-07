package sample.ritwik.common.ui.fragment

import android.content.Context

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer

import com.squareup.picasso.Picasso

import sample.ritwik.common.mvvm.model.BaseModel

import sample.ritwik.common.mvvm.repository.BaseRepository

import sample.ritwik.common.mvvm.viewModel.BaseViewModel

import sample.ritwik.common.ui.activity.BaseActivity

import sample.ritwik.common.utility.constant.ACTION_NONE
import sample.ritwik.common.utility.constant.ACTION_PROGRESS_BAR
import sample.ritwik.common.utility.constant.ACTION_UPDATE_UI

import java.lang.RuntimeException

/**
 * Abstract [Fragment] for handling common set-up required to show a [Fragment] in the UI.
 *
 * @param DataBinding [ViewDataBinding] referencing the Data Binding class of this [Fragment].
 * @author Ritwik Jamuar.
 */
abstract class BaseFragment<
        DataBinding: ViewDataBinding,
        Model: BaseModel,
        ViewModel: BaseViewModel<out BaseRepository, Model>
        > : Fragment() {

    /*---------------------------------------- Components ----------------------------------------*/

    /**
     * Reference of [DataBinding] to control the Views under it.
     */
    protected var binding: DataBinding? = null

    /**
     * Reference of [ViewModel] as the ViewModel of [BaseActivity].
     */
    protected var viewModel: ViewModel? = null

    /**
     * Reference of [Picasso] as the Image Loader Library of this Application.
     */
    protected var imageLoader: Picasso? = null

    /*---------------------------------------- Observers -----------------------------------------*/

    /**
     * [Observer] of [BaseViewModel.uiLiveData].
     */
    private val uiObserver = Observer<Model> { uiData ->
        processUpdateOnUIData(uiData)
    }

    /*------------------------------------ Fragment Callbacks ------------------------------------*/

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *, *>) {
            viewModel = context.getVM() as ViewModel
            imageLoader = context.getImageLoader()
        } else {
            throw RuntimeException("$context must be an extension of ${BaseActivity::class.java}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            extractArguments(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes(), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        viewModel?.uiLiveData?.observe(viewLifecycleOwner, uiObserver)
    }

    override fun onPause() {
        super.onPause()
        hideLoading()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanUp()
        binding = null
    }

    override fun onDetach() {
        super.onDetach()
        viewModel = null
        imageLoader = null
    }

    /*-------------------------------------- Private Methods -------------------------------------*/

    /**
     * Handles the change in [Model] propagated from [viewModel].
     *
     * @param uiData Modified [Model].
     */
    private fun processUpdateOnUIData(uiData: Model): Unit = when (uiData.action) {

        ACTION_PROGRESS_BAR -> with(uiData.progressData) {
            if (showProgress) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        ACTION_UPDATE_UI -> onUIDataChanged(uiData)

        ACTION_NONE -> Unit

        else -> onAction(uiData)

    }

    /*------------------------------------- Abstract Methods -------------------------------------*/

    /**
     * Provides the Layout XML of this [BaseFragment].
     *
     * @return [Int] denoting the [LayoutRes].
     */
    abstract fun layoutRes(): Int

    /**
     * Tells this [BaseFragment] to extract the arguments from [Bundle].
     *
     *
     * This method will be executed only if the [Bundle] argument was set during the instantiation
     * of this [BaseFragment].
     *
     * @param arguments [Bundle] that contains the arguments.
     */
    protected abstract fun extractArguments(arguments: Bundle)

    /**
     * Tells this [BaseFragment] to perform initialization of it's [View]s through [binding].
     */
    protected abstract fun initializeViews()

    /**
     * Tells this [BaseFragment] to show the Loading.
     */
    protected abstract fun showLoading()

    /**
     * Tells this [BaseFragment] to hide the Loading.
     */
    protected abstract fun hideLoading()

    /**
     * Tells this [BaseFragment] that there is a change in UI Data.
     *
     * @param uiData Instance of [Model] changed from [viewModel].
     */
    protected abstract fun onUIDataChanged(uiData: Model)

    /**
     * Tells this [BaseFragment] to perform some action received from [ViewModel].
     */
    protected abstract fun onAction(uiData: Model)

    /**
     * Tells this [BaseFragment] to perform Clean-Up procedures for avoiding Memory Leaks.
     */
    protected abstract fun cleanUp()

}
