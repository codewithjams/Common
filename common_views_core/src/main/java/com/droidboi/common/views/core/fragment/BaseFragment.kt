package com.droidboi.common.views.core.fragment

import android.content.Context

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.fragment.app.Fragment

/**
 * Abstract [Fragment] to contain all the common methods related to setting up the UI.
 *
 * @param Binding Any Class representing the View/Data Binding Class of this [Fragment].
 * @author Ritwik Jamuar
 */
abstract class BaseFragment<Binding : ViewDataBinding> : Fragment() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [Binding] that is used to manually clear it's instance in the event of
	 * Fragment destruction so that Memory Leaks can be avoided.
	 */
	private var _binding: Binding? = null

	/**
	 * Reference of [Binding] to access the views under it.
	 */
	protected val binding: Binding
		get() = _binding!!

	/**
	 * [Integer] as [androidx.annotation.LayoutRes] denoting the Layout ID
	 * of this [Fragment].
	 */
	protected abstract val layoutRes: Int

	/*------------------------------------ Fragment Callbacks ------------------------------------*/

	override fun onAttach(context: Context) {
		super.onAttach(context)
		attachListeners(context)
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
		detachListeners()
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Attaches the listeners of this [Fragment] via the [context].
	 *
	 * @param context [Context] of the invoking [androidx.appcompat.app.AppCompatActivity].
	 */
	protected open fun attachListeners(context: Context) = Unit

	/**
	 * Extracts the arguments present in the [Bundle] from [Fragment.getArguments].
	 *
	 * @param arguments [Bundle] consisting of all the arguments.
	 */
	protected open fun extractArguments(arguments: Bundle) = Unit

	/**
	 * Initializes all the views under [Binding].
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
	 * Detaches the listeners from this [Fragment].
	 */
	protected open fun detachListeners() = Unit

}
