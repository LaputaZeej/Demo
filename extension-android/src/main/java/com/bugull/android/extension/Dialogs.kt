package com.bugull.android.extension

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import java.lang.IllegalStateException


fun loadingDialog(activity: Activity) =
    AlertDialog.Builder(activity, R.style.AlertDialog_My)
        .setView(R.layout.dialog_loading_default)
        .create()
        .also {
            it.show()
        }

fun loadingDialog(fragment: Fragment) = loadingDialog(fragment.requireActivity())

fun loadingDialog(context: Any) = when (context) {
    is Activity -> {
        loadingDialog(context)
    }
    is Fragment -> {
        loadingDialog(context)
    }
    else -> throw IllegalStateException("context is error")
}