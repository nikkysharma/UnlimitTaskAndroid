package com.example.unlimittaskapp.ui.home.presentations.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.example.unlimittaskapp.base.BaseRealmRecyclerAdapter
import com.example.unlimittaskapp.base.BaseViewHolder
import com.example.unlimittaskapp.data.database.module.Jokes
import com.example.unlimittaskapp.databinding.ItemJokesListBinding
import io.realm.OrderedRealmCollection
import io.realm.RealmQuery


class JokesAdapter(data: OrderedRealmCollection<Jokes?>, context: Context) :
BaseRealmRecyclerAdapter<Jokes>(context,data) {
    override fun getBaseAdapterBinding(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup
    ): ViewBinding {
        return ItemJokesListBinding.inflate(layoutInflater, viewGroup, false)
    }

    override fun getBaseAdapterViewHolder(binding: ViewBinding): BaseViewHolder<Jokes> {
        return PatrolListHolder(binding)
    }

    inner class PatrolListHolder(binding: ViewBinding) :
        BaseViewHolder<Jokes>(binding) {
        private val eventListBinding = binding as ItemJokesListBinding


        @SuppressLint("SetTextI18n")
        override fun bind(model: Jokes) {
            eventListBinding.txtContent.text = model?.jokes
            eventListBinding.txtDate.text= model?.startDateStr

        }
    }


}