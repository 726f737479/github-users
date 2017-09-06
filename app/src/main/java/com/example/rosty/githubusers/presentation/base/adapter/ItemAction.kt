package com.example.rosty.githubusers.presentation.base.adapter


class ItemAction<T>(val action : (T) -> Unit) {

    fun invoke(item: T) { action.invoke(item) }
}
