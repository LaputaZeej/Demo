package com.laputa.skin

import android.app.Application
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable

internal object SkinResources {
    private lateinit var mAppResources: Resources
    private lateinit var mSkinResources: Resources
    private var mSkinPkgName: String? = null
    private var isDefaultSkin: Boolean = true

    fun init(application: Application){
        mAppResources = application.resources
    }

    @JvmStatic
    fun applySkin(resources: Resources, pkgName: String) {
        mSkinResources = resources
        mSkinPkgName = pkgName
        isDefaultSkin = pkgName.isEmpty()
    }

    @JvmStatic
    fun reset() {
        //mSkinResources = null
        mSkinPkgName = null
        isDefaultSkin = true
    }

    /**
     * 1.通过原始app中得resId（R.color.xxx)获取到名字
     * 2.根据名字和类型获取皮肤包中得id
     */
    @JvmStatic
    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        val resourceEntryName = mAppResources.getResourceEntryName(resId)
        val resourceTypeName = mAppResources.getResourceTypeName(resId)
        return mSkinResources.getIdentifier(resourceEntryName, resourceTypeName, mSkinPkgName)
    }

    @JvmStatic
    fun getColor(resId: Int): Int {
        if (isDefaultSkin) {
            return mAppResources.getColor(resId)
        }
        val identifier = getIdentifier(resId)
        return if (identifier == 0) mAppResources.getColor(resId) else
            mSkinResources.getColor(identifier)
    }

    @JvmStatic
    fun getDrawable(resId: Int): Drawable {
        if (isDefaultSkin) {
            return mAppResources.getDrawable(resId)
        }
        val identifier = getIdentifier(resId)
        return if (identifier == 0) mAppResources.getDrawable(resId) else
            mSkinResources.getDrawable(identifier)

    }

    @JvmStatic
    fun getBackground(resId: Int): Any {
        return when (mAppResources.getResourceTypeName(resId)) {
            "color" -> getColor(resId) //color
            else -> getDrawable(resId) //drawable
        }
    }

    @JvmStatic
    fun getColorStateList(resId: Int): ColorStateList {
        if (isDefaultSkin) {
            return mAppResources.getColorStateList(resId)
        }
        val identifier = getIdentifier(resId)
        return if (identifier == 0) mAppResources.getColorStateList(resId)
        else mSkinResources.getColorStateList(resId)
    }

}