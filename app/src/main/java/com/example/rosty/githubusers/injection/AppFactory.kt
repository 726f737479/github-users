package com.example.rosty.githubusers.injection

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v4.app.Fragment

import com.example.rosty.githubusers.GitHubApp


class AppFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    constructor(fragment: Fragment) : this(fragment.activity.application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val t = super.create(modelClass)

        if (t is AppComponent.Injectable)
            t.inject((application as GitHubApp).appComponent)

        return t
    }
}
