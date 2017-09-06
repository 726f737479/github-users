package com.example.rosty.githubusers.presentation.data.temp

import com.example.rosty.githubusers.data.temp.TempDataManager
import com.example.rosty.githubusers.data.temp.TempDataManagerImpl
import com.example.rosty.githubusers.model.User

import org.junit.Test

import java.util.ArrayList

import org.junit.Assert.assertEquals


class TempDataManagerImplTest {

    private val TEST_RECIPES_COUNT = 5

    private val tempDataManager = TempDataManagerImpl()

    @Test @Throws(Exception::class) fun getRecipe() {

        val users = (0..TEST_RECIPES_COUNT - 1).map { User(login = it.toString()) }

        tempDataManager.saveUsers(users)

        for (i in 0..TEST_RECIPES_COUNT - 1)
            assertEquals(tempDataManager.getUser(i.toString()), users[i])
    }
}