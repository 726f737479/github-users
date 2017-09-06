package com.example.rosty.githubusers.presentation.details

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rosty.githubusers.R
import com.example.rosty.githubusers.databinding.FragmentDetailsBinding
import com.example.rosty.githubusers.presentation.base.BaseFragment
import com.example.rosty.githubusers.presentation.getUserLogin
import com.example.rosty.githubusers.util.applyTransactions
import com.example.rosty.githubusers.util.onBackAction
import com.example.rosty.githubusers.util.openWeb


class DetailsFragment : BaseFragment<DetailsViewModel, FragmentDetailsBinding>() {

    override fun getViewModelClass() = DetailsViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyTransactions(android.R.transition.move)

        if (viewModel.state.get() != DetailsViewModel.State.RESULT)
            viewModel.loadUser(getUserLogin())
    }

    override fun onCreateView(inflater:           LayoutInflater?,
                              container:          ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (!isViewCreated) {

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

            binding.vm = viewModel
            binding.id = getUserLogin()

            binding.toolbar.onBackAction { fragmentManager.popBackStack() }
            binding.viewOriginal.setOnClickListener { context.openWeb(it.tag as String, binding.root) }
        }

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setTransitionName(binding.layout, binding.id)
    }
}