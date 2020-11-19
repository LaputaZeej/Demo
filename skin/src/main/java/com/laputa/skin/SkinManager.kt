package com.laputa.skin

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.util.ArrayMap
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import java.util.*

object SkinManager : Observable() {
    private lateinit var callback: SkinActivityLifecycleCallback
    private lateinit var mContext: Context

    fun init(application: Application) {
        mContext = application
        SkinSp.init(application)
        SkinResources.init(application)
        callback = SkinActivityLifecycleCallback(this)
        application.registerActivityLifecycleCallbacks(callback)
        loadSkin(SkinSp.path)
    }

    @JvmStatic
    fun loadSkin(skinPath: String?) {
        if (skinPath.isNullOrEmpty()) {
            SkinSp.reset()
            SkinResources.reset()
        } else {
            try {
                val appResource = mContext.resources
                val assetManager = AssetManager::class.java.newInstance()
                val addAssetPathMethod =
                    assetManager::class.java.getMethod("addAssetPath", String::class.java)
                addAssetPathMethod.invoke(assetManager, skinPath)
                val skinResources =
                    Resources(assetManager, appResource.displayMetrics, appResource.configuration)
                // todo 这里的packageArchiveInfo为null
                val packageArchiveInfo = mContext.packageManager.getPackageArchiveInfo(
                    skinPath,
                    PackageManager.GET_ACTIVITIES
                )
                SkinResources.applySkin(skinResources, packageArchiveInfo.packageName)
                SkinSp.path = skinPath
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        setChanged()
        notifyObservers(null)
    }

    private class SkinActivityLifecycleCallback(
        private val observable: Observable,
        private val mLayoutInflaterFactories: ArrayMap<Activity, SkinFactory> = ArrayMap()
    ) : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            val factory = mLayoutInflaterFactories.get(activity)
            SkinManager.deleteObserver(factory)
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            // 更新状态栏
            SkinSDKUtils.updateStatusBarColor(activity)
            val layoutInflater = activity.layoutInflater
            try {
                // android 布局加载器 使用mFactorySet标记是否设置过Factory
                // 如果设置过抛出一次
                // 设置为false
                val mFactorySetDeclaredField =
                    LayoutInflater::class.java.getDeclaredField("mFactorySet")
                mFactorySetDeclaredField.isAccessible = true
                mFactorySetDeclaredField.setBoolean(layoutInflater, false)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            val skinFactory = SkinFactory(activity)
            LayoutInflaterCompat.setFactory2(layoutInflater, skinFactory)
            mLayoutInflaterFactories.put(activity, skinFactory)
            observable.addObserver(skinFactory)
        }

    }
}

