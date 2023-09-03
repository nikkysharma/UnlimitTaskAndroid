package com.example.unlimittaskapp.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder <in T>(val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(model:T)


}