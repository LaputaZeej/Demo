package com.laputa.skin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import java.lang.Exception
import java.lang.reflect.Constructor
import java.util.*
import kotlin.collections.HashMap

/**
 * view的创建工厂
 */
internal class SkinFactory(
    private val activity: Activity, private val skinAttribute: SkinAttribute = SkinAttribute()
) : LayoutInflater.Factory2, Observer {
    //记录对应VIEW的参数和构造函数
    private val mConstructorSignature =
        arrayOf(
            Context::class.java, AttributeSet::class.java
        )

    private val mConstructorMap: HashMap<String, Constructor<out View>> =
        HashMap<String, Constructor<out View>>()


    override fun onCreateView(
        parent: View?,
        name: String?,
        context: Context?,
        attrs: AttributeSet?
    ): View {
        return onCreateView(name!!, context!!, attrs!!)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View {
        Log.e(TAG, "name = $name")
        val view = createSDKView(name, context, attrs) ?: createView(name, context, attrs)
        ?: throw Exception("can't create view")
        if (view is  WebView){

        }else{
            skinAttribute.lookup(view, attrs) // 加载属性
        }

        return view
    }

    private fun findConstructor(context: Context, name: String): Constructor<out View>? {
        Log.e(TAG, "#findConstructor name = $name")
        return mConstructorMap[name]
            ?: try {
                val clz = context.classLoader.loadClass(name).asSubclass(View::class.java)
                val constructorCreate = clz.getConstructor(*mConstructorSignature)
                mConstructorMap[name] = constructorCreate
                constructorCreate
            } catch (e: Throwable) {
                Log.e(TAG, "    #findConstructor error ${e.message}")
                null
            }
    }

    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.e(TAG, "#createSDKView name = $name")
        if (-1 != name.indexOf('.')) {
            return null
        }
        mClassPrefixList.forEach {
            val createView = createView(it + name, context, attrs)
            if (createView != null) {
                return createView
            }
        }
        return null
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        Log.e(TAG, "#createView name = $name")
        try {
            val findConstructor = findConstructor(context, name)
            return findConstructor?.newInstance(context, attrs)
        } catch (e: Throwable) {
            // no constructor
        }
        return null
    }

    override fun update(o: Observable?, arg: Any?) {
        SkinSDKUtils.updateStatusBarColor(activity)
        skinAttribute.applySkin()
    }

    companion object {
        private const val TAG = "SkinFactory"
        private val mClassPrefixList = arrayOf(
            "android.widget.",
            "android.webkit.",
            "android.app.",
            "android.view."
        )

    }
}