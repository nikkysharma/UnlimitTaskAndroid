package com.example.unlimittaskapp.base

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.unlimittaskapp.R

open class BaseFragment : Fragment() {
    var loader: Dialog? = null
    var loader1: Dialog? = null
    fun showProgress() {
        if (loader != null) {
            loader?.show()
            return
        }
        loader = Dialog(requireContext())
        loader?.setCancelable(false)
        loader?.setContentView(R.layout.dialog_loading)
        loader?.show()
    }

    fun dismissProgress() {
        if (loader != null && loader?.isShowing!!)
            loader?.dismiss()
    }

}