package com.droidboi.common.views.dialogs

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Abstract [BottomSheetDialogFragment] for handling common set-up required
 * to show a [BottomSheetDialogFragment] in the UI.
 *
 * @param Binding Any Class referencing the View/Data Binding class
 *   of this [BottomSheetDialogFragment].
 * @author Ritwik Jamuar
 */
abstract class BaseBottomSheetDialog<Binding : ViewDataBinding> : BottomSheetDialogFragment() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [Binding] which can be used to configure instance
	 * safe from Memory Leak.
	 */
	private var _binding: Binding? = null

	/**
	 * Reference of [Binding] to control the Views under it.
	 */
	protected val binding: Binding
		get() = _binding!!

	/**
	 * [Int] as thhe [androidx.annotation.LayoutRes] denoting the Layout ID
	 * of this [BottomSheetDialogFragment].
	 */
	protected abstract val layoutRes: Int

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
	): View? {
		_binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
		return binding.root
	}

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
	protected open fun setUpWindowDimension() = Unit

	/**
	 * Sets the background of [android.view.Window] of this [BottomSheetDialogFragment].
	 */
	protected open fun setUpWindowBackground() = Unit

	/**
	 * Extract the arguments from [Bundle] present in [arguments].
	 *
	 *
	 * This method will be executed only if the [arguments] was set during the instantiation
	 * of this [BottomSheetDialogFragment].
	 *
	 * @param arguments [Bundle] that contains the arguments.
	 */
	protected open fun extractArguments(arguments: Bundle) = Unit

	/**
	 * Perform initialization of Views through [binding].
	 */
	protected open fun initializeViews() = Unit

	/**
	 * Performs Clean-Up procedure for avoiding Memory Leaks.
	 *
	 *
	 * Make sure to perform call to super method after performing all clean-up procedures
	 * to ensure clean-up of [binding] from Memory.
	 * If `super.cleanUp()` is called before, then this method will throw [NullPointerException]
	 * due to [binding] being `null`.
	 *
	 *
	 * Example usage: -
	 * ```
	 * override fun cleanUp() {
	 *     // Perform clean-up operations.
	 *     super.cleanUp()
	 * }
	 * ```
	 */
	protected open fun cleanUp() {
		_binding = null
	}

	/**
	 * Remove it's listeners for preventing any [NullPointerException]
	 * when the parent [View] is destroyed.
	 */
	protected open fun removeListeners() = Unit

}
