package com.example.rosty.githubusers.presentation.search

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.rosty.githubusers.R
import com.example.rosty.githubusers.databinding.FragmentSearchBinding
import com.example.rosty.githubusers.model.User
import com.example.rosty.githubusers.presentation.base.BaseFragment
import com.example.rosty.githubusers.presentation.buildDetailsScreen
import com.example.rosty.githubusers.presentation.details.DetailsFragment
import com.example.rosty.githubusers.util.onSearchAction


class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {

    override fun getViewModelClass() = SearchViewModel::class.java

    override fun onCreateView(inflater:           LayoutInflater?,
                              container:          ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (!isViewCreated) {

            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

            binding.vm = viewModel

            binding.users.adapter = SearchAdapter(viewModel.users, { openDetailsScreen(it) })

            binding.etQuery.onSearchAction { viewModel.searchUser(it) }
            binding.btnRetry.setOnClickListener { viewModel.searchUser(binding.etQuery.text.toString()) }
        }

        return binding.root
    }

    private fun openDetailsScreen(user: User) {

        fragmentManager.beginTransaction()
                .addSharedElement(binding.users.findViewWithTag<View>(user), user.login)
                .replace(R.id.container, buildDetailsScreen(user.login!!))
                .addToBackStack(DetailsFragment::class.java.simpleName)
                .commit()
    }
}
