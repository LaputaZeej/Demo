package com.bugull.android.extension.base

import androidx.multidex.MultiDexApplication
import com.bugull.android.extension.isRunningCurrentProcess

 abstract class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
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
}