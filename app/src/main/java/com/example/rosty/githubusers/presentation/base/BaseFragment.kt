package com.example.rosty.githubusers.presentation.base

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.View
import com.example.rosty.githubusers.injection.AppFactory


abstract class BaseFragment<VM : ViewModel,  B : ViewDataBinding> : LifecycleFragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding:   B

    protected var isViewCreated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders
                .of(this, AppFactory(this))
                .get(getViewModelClass())
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isViewCreated = true
    }

    protected abstract fun getViewModelClass(): Class<VM>
}
