package com.laputa.skin

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

internal object SkinSp {
    private lateinit var sp: SharedPreferences
    fun init(application: Application) {
        sp = application.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var path: String? = null
        get() = sp.getString(SP_PATH, "")
        set(value) {
            sp.edit().putString(SP_PATH, value).apply()
            field = value
        }

    fun reset(){
        path = ""
    }

    private const val NAME = "skin"
    private const val SP_PATH = "skin-path"
}