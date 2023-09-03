package com.example.unlimittaskapp.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.unlimittaskapp.R
import com.example.unlimittaskapp.databinding.NoRecordRecviewLayoutBinding

class NoRecordRecyclerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defStyle) {

    var binding =
        NoRecordRecviewLayoutBinding.inflate(LayoutInflater.from(context), this, true)


    init {
        val attrData: TypedArray =
            context.obtainStyledAttributes(
                attributeSet,
                R.styleable.NoRecordRecyclerView,
                defStyle,
                0
            )
        val noRecText = attrData.getString(R.styleable.NoRecordRecyclerView_noRecordText)
        val noRecImage = attrData.getResourceId(R.styleable.NoRecordRecyclerView_noRecordImage, -1)
        if (!noRecText.isNullOrEmpty())
            binding.noRecText.text = noRecText
        if (noRecImage != -1)
            binding.noRecImage.setImageResource(noRecImage)

    }

    fun setItemAnimator(animator: RecyclerView.ItemAnimator) {
        binding.recView.itemAnimator = animator
    }

    fun <VH : RecyclerView.ViewHolder> setAdapter(adapter: RecyclerView.Adapter<VH>) {
        binding.recView.adapter = adapter
    }

    fun addOnScrollListener(listener: RecyclerView.OnScrollListener) {
        binding.recView.addOnScrollListener(listener)
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.recView.layoutManager = layoutManager
    }

    fun scrollToPosition(position: Int) {
        binding.recView.layoutManager?.scrollToPosition(position)
    }
    fun smoothScrollToPosition(position: Int) {
        binding.recView.layoutManager?.smoothScrollToPosition(binding.recView, null, position)
    }

    fun enableNoRecordValidations() {
        binding.recView.adapter?.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onChanged() {

                if (binding.recView.adapter?.itemCount == 0)
                    showNoRecordLayout()
                else
                    hideNoRecordLayout()

            }
        })
    }



    fun showNoRecordLayout() {
        binding.noRecGroup.visibility = VISIBLE
        binding.recView.visibility = GONE
    }

    fun hideNoRecordLayout() {
        binding.noRecGroup.visibility = View.GONE
        binding.recView.visibility = View.VISIBLE
    }

}