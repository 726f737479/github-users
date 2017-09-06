package com.example.rosty.githubusers

import android.app.Application

import com.example.rosty.githubusers.injection.AppComponent
import com.example.rosty.githubusers.injection.AppModule
import com.example.rosty.githubusers.injection.DaggerAppComponent

import timber.log.Timber


class GitHubApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
