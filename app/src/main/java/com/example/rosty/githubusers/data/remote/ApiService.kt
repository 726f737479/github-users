package com.example.rosty.githubusers.data.remote


import com.example.rosty.githubusers.data.remote.response.SearchResponse
import com.example.rosty.githubusers.model.User

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Single<SearchResponse>

    @GET("users/{user}")
    fun getUser(@Path("user") login: String): Single<User>

    companion object {

        val BASE_URL = "https://api.github.com/"
    }
}
