package com.bugull.android.ext.app

import com.bugull.android.extension.base.BaseApplication
import com.laputa.skin.SkinManager


class MyApp :BaseApplication(){


    override fun onCreate() {
        super.onCreate()
        SkinManager.init(this)
        SkinManager.loadSkin(SKIN)
    }

    override fun init() {
    }

    override fun initCurrentProcess() {
    }

    companion object{
        const val SKIN = "/data/data/com.bugull.android.ext/skin/skin-demo-debug.apk"
    }
}

