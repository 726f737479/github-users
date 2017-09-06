package com.example.rosty.githubusers.presentation.data

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.data.DataSourceImpl
import com.example.rosty.githubusers.data.remote.ApiService
import com.example.rosty.githubusers.data.remote.response.SearchResponse
import com.example.rosty.githubusers.data.temp.TempDataManager
import com.example.rosty.githubusers.model.User

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber

import org.junit.Assert.assertEquals
import org.mockito.Matchers.anyString
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class DataSourceImplTest {

    private val TEST_USERS_COUNT = 3

    @Mock private lateinit var apiService:      ApiService
    @Mock private lateinit var tempDataManager: TempDataManager
          private lateinit var dataSource:      DataSource

    private val scheduler  = TestScheduler()
    private val schedulers = object : RxSchedulers {

        override fun main(): Scheduler { return scheduler }

        override fun io(): Scheduler { return scheduler }
    }

    @Before @Throws(Exception::class) fun setUp() {

        MockitoAnnotations.initMocks(this)

        dataSource = DataSourceImpl(apiService, tempDataManager, schedulers)
    }

    @Test @Throws(Exception::class) fun serachRecipe() {

        val users = ArrayList<User>()

        for (i in 0..TEST_USERS_COUNT - 1) users.add(User())

        `when`(apiService.searchUsers(anyString())).thenReturn(
                Single.just(SearchResponse(users.size, users)))

        val observer = TestObserver<List<User>>()

        dataSource.searchUsers(anyString()).subscribe(observer)

        observer.assertSubscribed()
        observer.assertNoErrors()

        scheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        val onNextEvents = observer.values()

        assertEquals(onNextEvents[0].size.toLong(), TEST_USERS_COUNT.toLong())
        assertEquals(onNextEvents[0], users)

        verify<ApiService>(apiService).searchUsers(anyString())
        verify<TempDataManager>(tempDataManager).saveUsers(users)
    }

    @Test @Throws(Exception::class) fun getRecipe() {

        val user1 = User()
        val user2 = User()

        `when`<User>(tempDataManager.getUser(anyString())).thenReturn(user1)
        `when`(apiService.getUser(anyString())).thenReturn(Single.just(user2))

        val subscriber = TestSubscriber<User>()
        dataSource.getUser(anyString()).subscribe(subscriber)

        subscriber.assertSubscribed()
        subscriber.assertNoErrors()

        scheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        val onNextEvents = subscriber.values()

        assertEquals(onNextEvents[0], user1)
        assertEquals(onNextEvents[1], user2)

        verify<ApiService>(apiService).getUser(anyString())
        verify<TempDataManager>(tempDataManager).getUser(anyString())
    }
}