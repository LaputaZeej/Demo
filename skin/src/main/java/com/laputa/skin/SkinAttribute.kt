package com.laputa.skin

import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.laputa.skin.SkinAttribute.Companion.ATTRIBUTES_MAP
import java.lang.Exception

/**
 * 保存需要改变的属性
 * 包含多个[SkinView]
 */
internal class SkinAttribute {
    private var mSkinViews: MutableList<SkinView> = mutableListOf()

    fun applySkin() {
        mSkinViews.forEach {
            it.applySkin()
        }
    }

    /**
     * 读取需要改变的属性，保存。
     */
    fun lookup(view: View, attributes: AttributeSet) {
        val skinPairs = mutableListOf<SkinPair>()
        (0 until attributes.attributeCount).forEach { index ->
            val attributeName = attributes.getAttributeName(index)
            Log.e(TAG, "lookup name = ${attributeName}")
            if (ATTRIBUTES.contains(attributeName)) {
                // # 不是resource的值，比如#ffffff
                // ? 系统
                // @ resource
                val attributeValue = attributes.getAttributeValue(index)
                when {
                    attributeValue.startsWith("#") -> {
                        Log.w(TAG, "attributeValue value is begin with # ： $attributeName = $attributeValue")
                    }
                    attributeValue.startsWith("?") -> {
                        // ?  android:background="?selectableItemBackground" 有问题
           /*             val attrId = attributeValue.substring(1).toInt()
                        val resId = SkinSDKUtils.getResId(view.context, IntArray(attrId))[0]
                        skinPairs.add(SkinPair(attributeName, resId))*/
                    }
                    attributeValue.startsWith("@") -> {
                        val resId = attributeValue.substring(1).toInt()
                        skinPairs.add(SkinPair(attributeName, resId))
                    }
                }
            }
        }

        if (skinPairs.isNotEmpty() || view is SkinViewSupport) {
            val skinView = SkinView(view, skinPairs)
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
        Log.e(TAG, "lookup size = ${mSkinViews.size}")
    }

    companion object {
        private const val TAG = "SkinAttribute"
        private val ATTRIBUTES: List<String> = listOf(
            "background", "src", "textColor",
            "drawableLeft", "drawableRight", "drawableTop", "drawableBottom"
        )

        // for test
        val ATTRIBUTES_MAP: Map<String, (View, Int) -> Unit> = mapOf(
            "background" to { view, resId ->
                when (val background = SkinResources.getBackground(resId)) {
                    is Int -> {
                        view.setBackgroundColor(background)
                    }
                    is Drawable -> {
                        ViewCompat.setBackground(view, background)
                    }
                    else -> {
                        throw Exception("apply background error ! resId is wrong : ${resId}")
                    }
                }
            }
        )
    }
}

/**
 * 属性名称-id
 */
internal data class SkinPair(val attributeName: String, val resId: Int)

/**
 * 一个View包含多个[SkinPair]
 */
internal class SkinView(private val view: View, private val skinPairs: MutableList<SkinPair>) {

    fun applySkin() {
        applySkinSupport()
        skinPairs.forEach {
            var left: Drawable? = null
            var top: Drawable? = null
            var right: Drawable? = null
            var bottom: Drawable? = null
            when (it.attributeName) {
                "background" -> {
                    when (val background = SkinResources.getBackground(it.resId)) {
                        is Int -> {
                            view.setBackgroundColor(background)
                        }
                        is Drawable -> {
                            ViewCompat.setBackground(view, background as Drawable)
                        }
                        else -> {
                            throw Exception("apply background error ! resId is wrong : ${it.resId}")
                        }
                    }
                }
                "src" -> {
                    if (view is ImageView) {
                        when (val src = SkinResources.getBackground(it.resId)) {
                            is Int -> {
                                view.setImageResource(src)
                            }
                            is Drawable -> {
                                view.setImageDrawable(src)
                            }
                            else -> {
                                throw Exception("apply src error ! resId is wrong : ${it.resId}")
                            }
                        }
                    }
                }
                "textColor" -> {
                    if (view is TextView) {
                        val colorStateList = SkinResources.getColorStateList(it.resId)
                        view.setTextColor(colorStateList)
                    }
                }
                "drawableLeft" -> {
                    if (view is TextView)
                        left = SkinResources.getDrawable(it.resId)
                }
                "drawableRight" -> {
                    if (view is TextView)
                        right = SkinResources.getDrawable(it.resId)
                }
                "drawableTop" -> {
                    if (view is TextView)
                        top = SkinResources.getDrawable(it.resId)
                }
                "drawableBottom" -> {
                    if (view is TextView)
                        bottom = SkinResources.getDrawable(it.resId)
                }

            }
            if (view is TextView && (left != null || top != null || right != null || bottom != null)) {
                view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
            }

        }
    }

    fun applySkinExt() {
        applySkinSupport()
        skinPairs.forEach {
            ATTRIBUTES_MAP[it.attributeName]?.invoke(view, it.resId)
        }
    }


    private fun applySkinSupport() {
        if (view is SkinViewSupport) {
            view.applySkin()
        }
    }
}
