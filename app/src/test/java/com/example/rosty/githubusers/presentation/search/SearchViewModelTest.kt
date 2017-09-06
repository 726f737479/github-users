package com.example.rosty.githubusers.presentation.search


import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.model.User

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler

import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class SearchViewModelTest {

    private val TEST_USSERS_COUNT = 3

    @Mock private lateinit var dataSource: DataSource;
          private lateinit var viewModel:  SearchViewModel;

    private val scheduler  = TestScheduler()
    private val schedulers = object : RxSchedulers {

        override fun main(): Scheduler { return scheduler }

        override fun io(): Scheduler { return scheduler }
    }

    @Before @Throws(Exception::class) fun setUp() {

        MockitoAnnotations.initMocks(this)

        viewModel = SearchViewModel()

        viewModel.source     = dataSource
        viewModel.schedulers = schedulers
        viewModel.state      = spy(viewModel.state)
    }


    @Test @Throws(Exception::class) fun searchRecipeSuccess() {

        val users = ArrayList<User>()

        for (i in 0..TEST_USSERS_COUNT - 1) users.add(User())

        `when`(dataSource.searchUsers(anyString())).thenReturn(
                Single.just<List<User>>(users))

        viewModel.searchUser(anyString())

        scheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        verify(viewModel.state).set(SearchViewModel.State.PROGRESS)
        verify(viewModel.state).set(SearchViewModel.State.RESULT)

        verify<DataSource>(dataSource).searchUsers(anyString())

        assertTrue(viewModel.users.size == TEST_USSERS_COUNT)
    }

    @Test @Throws(Exception::class) fun searchRecipeError() {

        val users = ArrayList<User>()

        for (i in 0..TEST_USSERS_COUNT - 1) users.add(User())

        `when`(dataSource.searchUsers(anyString())).thenReturn(
                Single.error(RuntimeException()))

        viewModel.searchUser(anyString())

        scheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        verify(viewModel.state).set(SearchViewModel.State.PROGRESS)
        verify(viewModel.state).set(SearchViewModel.State.ERROR)

        verify<DataSource>(dataSource).searchUsers(anyString())

        assertTrue(viewModel.users.isEmpty())
    }
}