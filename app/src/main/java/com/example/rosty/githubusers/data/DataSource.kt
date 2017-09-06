package com.example.rosty.githubusers.data

import com.example.rosty.githubusers.model.User

import io.reactivex.Flowable
import io.reactivex.Single


interface DataSource {

    fun searchUsers(query: String): Single<List<User>>

    fun getUser(login: String): Flowable<User>
}
