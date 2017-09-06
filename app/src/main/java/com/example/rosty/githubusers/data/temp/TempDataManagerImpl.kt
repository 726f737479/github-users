package com.example.rosty.githubusers.data.temp


import com.example.rosty.githubusers.model.User

import java.util.ArrayList

class TempDataManagerImpl : TempDataManager {

    private var users: List<User> = ArrayList()

    override fun saveUsers(users: List<User>) {

        this.users = users
    }

    override fun getUser(login: String) = users.stream()
            .filter { it.login == login }
            ?.findFirst()
            ?.get()!!
}
