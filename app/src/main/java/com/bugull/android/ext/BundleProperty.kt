package com.bugull.android.ext

import android.content.Intent
import androidx.core.os.bundleOf
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class BundleProperty<T>(private var intent: Intent?, private val key: String) : ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(key)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        value?.let {
            putExtras(key, it)
        }

    }

    private fun putExtras(key: String, value: T) {
        intent?.putExtras(
            bundleOf(
                key to value
            )
        )
    }

    private fun getValue(key: String): T = intent?.extras?.get(key) as T

}