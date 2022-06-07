package com.droidboi.common.views.dialogs

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Abstract [BottomSheetDialogFragment] for handling common set-up required
 * to show a [BottomSheetDialogFragment] in the UI.
 *
 * @param Binding Any Class referencing the View/Data Binding class
 *   of this [BottomSheetDialogFragment].
 * @author Ritwik Jamuar
 */
abstract class BaseBottomSheetDialog<Binding> : BottomSheetDialogFragment() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Reference of [Binding] to control the Views under it.
	 */
	protected val binding: Binding by lazy { provideBinding() }

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
	): View? = provideView()

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
	 * Sets the dimensions of [android.view.Window] of this [BottomSheetDialogFragment].
	 */
	protected fun setUpWindowDimension() = Unit

	/**
	 * Sets the background of [android.view.Window] of this [BottomSheetDialogFragment].
	 */
	protected fun setUpWindowBackground() = Unit

	/*------------------------------------- Abstract Methods -------------------------------------*/

	/**
	 * Tells this [BottomSheetDialogFragment] to provide it's Binding instance which will be used
	 * to access the views under this [BottomSheetDialogFragment].
	 *
	 * @return New Instance of [Binding].
	 */
	protected abstract fun provideBinding(): Binding

	/**
	 * Tells this [BottomSheetDialogFragment] to provide the [View] under which this is rendered.
	 *
	 * @return Instance of [View] under which this [BottomSheetDialogFragment] is rendered.
	 */
	protected abstract fun provideView(): View

	/**
	 * Tells this [BottomSheetDialogFragment] to extract the arguments from [Bundle].
	 *
	 *
	 * This method will be executed only if the [Bundle] argument was set during the instantiation
	 * of this [BottomSheetDialogFragment].
	 *
	 * @param arguments [Bundle] that contains the arguments.
	 */
	protected abstract fun extractArguments(arguments: Bundle)

	/**
	 * Tells this [BottomSheetDialogFragment] to perform initialization of it's [View]s
	 * through [binding].
	 */
	protected abstract fun initializeViews()

	/**
	 * Tells this [BottomSheetDialogFragment] to perform Clean-Up procedures
	 * for avoiding Memory Leaks.
	 */
	protected abstract fun cleanUp()

	/**
	 * Tells the extending [BottomSheetDialogFragment] to remove it's listeners
	 * for preventing any [NullPointerException] when the parent [View] is destroyed.
	 */
	protected abstract fun removeListeners()

}
