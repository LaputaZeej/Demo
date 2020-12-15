package com.bugull.android.ext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Process
import com.bugull.android.extension.toast
import com.laputa.skin.SkinManager
import com.laputa.webview.util.i
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

class SplashActivity : AppCompatActivity(), CoroutineScope by MainScope() {


    val name1: String? by lazy { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading_default)
        val stringExtra = intent.getStringExtra("haha")
        var name: String? by BundleProperty(intent, "name")
        name = "hello"
        toast(name)
        i("SplashActivity = ${Process.myPid()}")
        i("haha = $stringExtra")


        SkinManager.loadSkin("/data/data/com.bugull.android.ext/skin/skin-demo-debug.apk")

        val a: Int? = null
        val b = a?.toFloat()
        val c = 1f.div (b ?: 0f)

    }

}