package com.example.unlimittaskapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import io.realm.OrderedRealmCollection
import io.realm.RealmObject
import io.realm.RealmRecyclerViewAdapter

abstract class BaseRealmRecyclerAdapter<T : RealmObject>(
    val context: Context,
    data: OrderedRealmCollection<T?>
) : RealmRecyclerViewAdapter<T?,
        BaseViewHolder<T>>(data, true,true)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val layoutInflater = LayoutInflater.from(context)
        val binding = getBaseAdapterBinding(layoutInflater, parent)
        return getBaseAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position)!!)

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    abstract fun getBaseAdapterBinding(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup
    ): ViewBinding

    abstract fun getBaseAdapterViewHolder(binding: ViewBinding): BaseViewHolder<T>



}