package com.bugull.android.extension.base

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import androidx.multidex.MultiDexApplication
import com.bugull.android.extension.isRunningCurrentProcess

 abstract class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        init()
        /* 多进程导致Application#onCreate()执行多次 */
        if (isRunningCurrentProcess) {
            initCurrentProcess()
        }
    }

     open fun init(){}

    /**
     * 当前进程初始化
     */
     open fun initCurrentProcess(){}

    companion object {
        @JvmStatic
        lateinit var INSTANCE: Application

        fun startActivity(className:String){
            try{
                this.INSTANCE.startActivity(Intent().apply {
                    component = ComponentName(INSTANCE, className)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }catch (e:Throwable){
                e.printStackTrace()
            }
        }
    }
}