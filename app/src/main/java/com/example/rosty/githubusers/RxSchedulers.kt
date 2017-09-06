package com.example.rosty.githubusers

import io.reactivex.Scheduler

interface RxSchedulers {

    fun main(): Scheduler

    fun io(): Scheduler
}
