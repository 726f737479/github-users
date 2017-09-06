package com.example.rosty.githubusers.presentation.search


import android.databinding.ObservableList
import com.example.rosty.githubusers.R
import com.example.rosty.githubusers.databinding.ItemUserBinding
import com.example.rosty.githubusers.model.User
import com.example.rosty.githubusers.presentation.base.adapter.ItemAction
import com.example.rosty.githubusers.presentation.base.adapter.ItemViewHolder
import com.example.rosty.githubusers.presentation.base.adapter.ListAdapter

internal class SearchAdapter(val users:      ObservableList<User>,
                             val itemAction: (User) -> Unit) : ListAdapter<User, ItemUserBinding>(users) {

    override val layoutRes: Int
        get() = R.layout.item_user

    override fun onBindViewHolder(holder: ItemViewHolder<ItemUserBinding>, position: Int) {

        holder.binding.item   = data[position]
        holder.binding.action = ItemAction(itemAction)
        holder.binding.isLast = position == data.size - 1
    }
}
