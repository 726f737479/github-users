package com.example.rosty.githubusers.presentation.details

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.model.User

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.concurrent.TimeUnit

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler

import org.junit.Assert.assertTrue
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`


class DetailsViewModelTest {

    @Mock private lateinit var dataSource: DataSource;
          private lateinit var viewModel:  DetailsViewModel;

    private val scheduler  = TestScheduler()
    private val schedulers = object : RxSchedulers {

        override fun main(): Scheduler { return scheduler }

        override fun io(): Scheduler { return scheduler }
    }

    @Before @Throws(Exception::class) fun setUp() {

        MockitoAnnotations.initMocks(this)

        viewModel = DetailsViewModel()

        viewModel.source     = dataSource
        viewModel.schedulers = schedulers
        viewModel.state      = spy(viewModel.state)
    }

    @Test @Throws(Exception::class) fun loadRecipeSuccess() {

        val user1 = User()
        val user2 = User(creationDate = "")

        `when`(dataSource.getUser(anyString())).thenReturn(Single.concat<User>(
                Single.just(user1),
                Single.just(user2)))

        viewModel.loadUser(anyString())

        scheduler.advanceTimeBy(0, TimeUnit.MILLISECONDS)

        verify(viewModel.state).set(DetailsViewModel.State.LOAD)
        verify(viewModel.state).set(DetailsViewModel.State.RESULT)

        verify<DataSource>(dataSource).getUser(anyString())

        assertTrue(viewModel.user.get() == user2)
        assertTrue(viewModel.user.get().creationDate != null)
    }

    @Test @Throws(Exception::class) fun loadRecipeError() {

        val user = User()

        `when`(dataSource.getUser(anyString())).thenReturn(Single.concat(
                Single.just(user),
                Single.error(RuntimeException())))

        viewModel.loadUser(anyString())

        scheduler.advanceTimeBy(0, TimeUnit.SECONDS)

        verify(viewModel.state).set(DetailsViewModel.State.LOAD)
        verify(viewModel.state).set(DetailsViewModel.State.ERROR)

        verify<DataSource>(dataSource).getUser(anyString())

        assertTrue(viewModel.user.get() == user)
        assertTrue(viewModel.user.get().creationDate == null)
    }
}