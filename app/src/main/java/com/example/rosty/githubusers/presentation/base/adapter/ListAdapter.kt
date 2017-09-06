package com.example.rosty.githubusers.presentation.base.adapter

import android.databinding.ObservableList


abstract class ListAdapter<T, B : android.databinding.ViewDataBinding>(protected var data: android.databinding.ObservableList<T>) : android.support.v7.widget.RecyclerView.Adapter<ItemViewHolder<B>>() {

    init { data.addOnListChangedCallback(OnListChangedCallback<T>(this)) }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ItemViewHolder<B> {

        val layoutInflater = parent.context
                .getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater

        return ItemViewHolder(layoutInflater.inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int { return data.size }

    @get:android.support.annotation.LayoutRes protected abstract val layoutRes: Int

    private inner class OnListChangedCallback<T> constructor(adapter: com.example.rosty.githubusers.presentation.base.adapter.ListAdapter<T, B>) : android.databinding.ObservableList.OnListChangedCallback<ObservableList<T>>() {

        private val adapterWeakReference: java.lang.ref.WeakReference<ListAdapter<T, B>>

        init { this.adapterWeakReference = java.lang.ref.WeakReference<ListAdapter<T, B>>(adapter)
        }

        override fun onChanged(items: android.databinding.ObservableList<T>) { notifyDataSetChanged() }

        override fun onItemRangeChanged(items: android.databinding.ObservableList<T>, positionStart: Int, itemCount: Int) {

            adapterWeakReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(items: android.databinding.ObservableList<T>, positionStart: Int, itemCount: Int) {

            adapterWeakReference.get()?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(items: android.databinding.ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {

            adapterWeakReference.get()?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(items: android.databinding.ObservableList<T>, positionStart: Int, itemCount: Int) {

            adapterWeakReference.get()?.notifyItemRangeChanged(positionStart, itemCount)
        }
    }
}
