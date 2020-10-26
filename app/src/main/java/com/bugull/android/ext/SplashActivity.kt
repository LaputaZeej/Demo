package com.bugull.android.ext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bugull.android.extension.toast
import com.laputa.skin.SkinManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class SplashActivity : AppCompatActivity(),CoroutineScope by MainScope(){


    val name1: String? by lazy { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_default)
        var name: String? by BundleProperty(intent, "name")
        name = "hello"
        toast(name)

        SkinManager.loadSkin("/data/data/com.bugull.android.ext/skin/skin-demo-debug.apk")
    }

}