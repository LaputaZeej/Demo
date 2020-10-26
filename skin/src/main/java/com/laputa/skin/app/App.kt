package com.laputa.skin.app

import android.app.Application
import com.laputa.skin.SkinManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
    }
}