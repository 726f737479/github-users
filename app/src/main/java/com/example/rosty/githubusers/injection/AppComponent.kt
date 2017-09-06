package com.example.rosty.githubusers.injection


import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.presentation.details.DetailsViewModel
import com.example.rosty.githubusers.presentation.search.SearchViewModel

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun dataSource():   DataSource
    fun rxSchedulers(): RxSchedulers

    fun inject(viewModel: SearchViewModel)
    fun inject(viewModel: DetailsViewModel)

    interface Injectable {

        fun inject(appComponent: AppComponent)
    }
}
