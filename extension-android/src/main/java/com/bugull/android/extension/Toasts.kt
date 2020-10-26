package com.bugull.android.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast

object ToastUtil {
    private var toast: Toast? = null

    fun show(context: Context, msg: String) {
        toast?.cancel()
        toast =
            Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun show(context: Context, id: Int) {
        show(context, context.applicationContext.getString(id))
    }
}

fun Context.toast(any: Any?) {
    any?.let {
        ToastUtil.show(this, it.toString())
    }
}

fun Context.toast(id: Int) {
    ToastUtil.show(this, id)
}

/**
 * 获取dp
 */
fun Context.getDp(id: Int) = this.resources.getDimension(id)

inline fun <reified T : Activity> Activity.startActivityEx(intent: Intent? = null) {
    intent?.let {
        it.setClass(this, T::class.java)
        startActivity(it)
    } ?: startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> Activity.startActivityForResultEx(
    intent: Intent? = null,
    requestCode: Int
) {
    intent?.let {
        it.setClass(this, T::class.java)
        startActivityForResult(it, requestCode)
    } ?: startActivityForResult(Intent(this, T::class.java), requestCode)
}