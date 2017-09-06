package com.example.rosty.githubusers.presentation.search

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import android.databinding.ObservableList
import android.support.annotation.VisibleForTesting

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.injection.AppComponent
import com.example.rosty.githubusers.model.User

import javax.inject.Inject

import io.reactivex.disposables.Disposable


class SearchViewModel : ViewModel(), AppComponent.Injectable {

    @Inject lateinit var source:     DataSource
    @Inject lateinit var schedulers: RxSchedulers

    var users = ObservableArrayList<User>()
    var state = ObservableField(State.IDLE)

    private var searchRecipesSubscription: Disposable? = null


    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCleared() {
        super.onCleared()

        searchRecipesSubscription?.dispose()
    }

    fun searchUser(query: String) {

        searchRecipesSubscription?.dispose()

        searchRecipesSubscription = source.searchUsers(query)
                .doOnSubscribe { disposable -> state.set(State.PROGRESS) }
                .doOnSubscribe { disposable -> users.clear() }
                .observeOn(schedulers.main())
                .doOnSuccess { onSuccess -> state.set(State.RESULT) }
                .subscribe(
                        { result -> users.addAll(result) },
                        { error ->  state.set(State.ERROR) })
    }


    enum class State { IDLE, PROGRESS, RESULT, ERROR }
}
