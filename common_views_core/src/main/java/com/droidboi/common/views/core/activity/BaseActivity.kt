package com.droidboi.common.views.core.activity

import android.content.Intent

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil

import androidx.databinding.ViewDataBinding

/**
 * Abstract [AppCompatActivity] to contain all the common methods related to setting up the UI.
 *
 * @param Binding Any class representing the View/Data Binding Class of this [AppCompatActivity].
 * @author Ritwik Jamuar
 */
abstract class BaseActivity<Binding : ViewDataBinding> : AppCompatActivity() {

	/*---------------------------------------- Components ----------------------------------------*/

	/**
	 * Nullable Reference of [Binding] that is used to manually clear it's instance in the event of
	 * Activity destruction so that Memory Leaks can be avoided.
	 */
	private var _binding: Binding? = null

	/**
	 * Reference of [Binding] to access the views under it.
	 */
	protected val binding: Binding
		get() = _binding!!

	/**
	 * [Integer] as [androidx.annotation.LayoutRes] denoting the Layout ID
	 * of this [AppCompatActivity].
	 */
	protected abstract val layoutRes: Int

	/*------------------------------------ Activity Callbacks ------------------------------------*/

	override fun onCreate(savedInstanceState: Bundle?) {
		inject()
		super.onCreate(savedInstanceState)
		setUp()
		intent?.let {
			extractArguments(it)
		}
		initialize()
	}

	override fun onDestroy() {
		super.onDestroy()
		cleanUp()
	}

	/*------------------------------------- Private Methods --------------------------------------*/

	/**
	 * Performs setting-up all the components of this [AppCompatActivity].
	 */
	private fun setUp() {
		setUpViews()
	}

	/**
	 * Sets-up the [binding].
	 */
	private fun setUpViews() {
		_binding = DataBindingUtil.setContentView(this, layoutRes)
	}

	/*------------------------------------- Protected Methods ------------------------------------*/

	/**
	 * Use Dependency Injection to inject the required dependencies into this [AppCompatActivity].
	 */
	protected open fun inject() = Unit

	/**
	 * Extracts the data sent from another [android.app.Activity] via [Intent]
	 * to this [AppCompatActivity].
	 *
	 * @param intent [Intent] extracted from [AppCompatActivity.getIntent].
	 */
	protected open fun extractArguments(intent: Intent) = Unit

	/**
	 * Initialize the components of this [AppCompatActivity].
	 */
	protected open fun initialize() = Unit

	/**
	 * Cleans-up the resources of this [AppCompatActivity] to avoid Memory Leaks.
	 */
	protected open fun cleanUp() {
		_binding = null
	}

}
