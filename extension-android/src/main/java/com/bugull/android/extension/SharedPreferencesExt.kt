package com.bugull.android.extension

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class SharedPreferencesExt<T>(private val prefs: SharedPreferences, private val mKey: String, private val mDefaultValue: T) :
    ReadWriteProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValueFromPrefs(mKey, mDefaultValue)
    }

    private fun getValueFromPrefs(name: String, default: T): T = with(prefs) {
        when (default) {
            is String -> getString(name, default) as T
            is Long -> getLong(name, default) as T
            is Int -> getInt(name, default) as T
            is Float -> getFloat(name, default) as T
            is Boolean -> getBoolean(name, default) as T
            else -> throw IllegalArgumentException("Sp.kt # This type can be saved into Preferences")
        }
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setValueToPrefs(mKey, value)
    }

    private fun setValueToPrefs(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is String -> putString(name, value)
            is Long -> putLong(name, value)
            is Int -> putInt(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            else -> throw IllegalArgumentException("Sp.kt # This type can be saved into Preferences")
        }
    }.apply()
}