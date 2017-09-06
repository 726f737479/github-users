package com.example.rosty.githubusers.data

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.remote.ApiService
import com.example.rosty.githubusers.data.temp.TempDataManager
import com.example.rosty.githubusers.model.User

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.Single


class DataSourceImpl @Inject constructor(private val apiService:      ApiService,
                                         private val tempDataManager: TempDataManager,
                                         private val schedulers:      RxSchedulers) : DataSource {

    override fun searchUsers(query: String) = apiService.searchUsers(query)
            .map({ it.users })
            .doOnSuccess({ tempDataManager.saveUsers(it) })
            .subscribeOn(schedulers.io())!!


    override fun getUser(login: String) = Single.concat(

            Single.just(tempDataManager.getUser(login)!!),
            apiService.getUser(login))

            .subscribeOn(schedulers.io())!!
}
