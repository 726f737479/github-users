package com.example.rosty.githubusers.data.temp


import com.example.rosty.githubusers.model.User

interface TempDataManager {

    fun saveUsers(users: List<User>)

    fun getUser(login: String): User?
}
