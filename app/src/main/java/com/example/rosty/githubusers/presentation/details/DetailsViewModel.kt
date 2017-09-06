package com.example.rosty.githubusers.presentation.details

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField

import com.example.rosty.githubusers.RxSchedulers
import com.example.rosty.githubusers.data.DataSource
import com.example.rosty.githubusers.injection.AppComponent
import com.example.rosty.githubusers.model.User

import javax.inject.Inject

import io.reactivex.disposables.Disposable


class DetailsViewModel : ViewModel(), AppComponent.Injectable {

    @Inject lateinit var source:     DataSource
    @Inject lateinit var schedulers: RxSchedulers

    var user  = ObservableField<User>()
    var state = ObservableField(State.LOAD)

    private var recipeSubscription: Disposable? = null


    override fun inject(appComponent: AppComponent) = appComponent.inject(this)

    override fun onCleared() {
        super.onCleared()

        recipeSubscription?.dispose()
    }

    fun loadUser(userLogin: String) {

        recipeSubscription?.dispose()

        recipeSubscription = source.getUser(userLogin)
                .doOnSubscribe { disposable -> state.set(State.LOAD) }
                .observeOn(schedulers.main(), true)
                .subscribe(
                        { result -> user.set(result) },
                        { error ->  state.set(State.ERROR) },
                        {           state.set(State.RESULT) })

    }


    enum class State { LOAD, RESULT, ERROR }
}
