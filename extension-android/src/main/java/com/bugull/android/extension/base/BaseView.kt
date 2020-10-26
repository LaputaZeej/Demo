package com.bugull.android.extension.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.bugull.android.extension.*
import com.gyf.barlibrary.ImmersionBar

// <editor-fold desc="[BaseActivity]">

abstract class BaseActivity : FragmentActivity(), IView,
    StatusBarDelegate<ImmersionBar> by StatusBarDelegateImpl(),
    ViewModelDelegate by ViewModelDelegateImpl(),
    ViewDelegate by ViewDelegateImpl() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initStatusBar()
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

    }

    override fun onDestroy() {
        super.onDestroy()
        unInitActionBar(this)
    }

    protected open fun initStatusBar() {
      /*  initActionBar(this){
            ImmersionBar.with(this)
                .statusBarColor(android.R.color.holo_blue_light)
                .statusBarDarkFont(true)

        }*/
    }



}

// </editor-fold>]

// <editor-fold desc="[BaseFragment]">

abstract class BaseFragment: Fragment(),IView{
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(layoutResId,container,false)
    }

}

// </editor-fold>

// <editor-fold desc="[interfaces]">

interface IView {
    val layoutResId: Int
}

interface StatusBarDelegate<T> {
    fun initActionBar(initializer: () -> T)
    fun unInitActionBar(activity: Activity)
    var immersionBar: T?

}

interface ViewModelDelegate {
    fun initViewModel(activity: FragmentActivity, viewModel: BaseViewModel)

}

interface ViewDelegate {
    fun showLoading(context: Context, msg: String? = null)
    fun hideLoading(context: Context, msg: String? = null)
}

// </editor-fold>

// <editor-fold desc="[impls]">

// 沉浸式
class StatusBarDelegateImpl() : StatusBarDelegate<ImmersionBar> {
    override var immersionBar: ImmersionBar? = null

    override fun initActionBar(initializer: () -> ImmersionBar) {
        immersionBar = initializer().also { it.init() }
    }

    override fun unInitActionBar(activity: Activity) {
        immersionBar?.destroy()
    }
}

// ViewModel
class ViewModelDelegateImpl : ViewModelDelegate {
    override fun initViewModel(activity: FragmentActivity, viewModel: BaseViewModel) {
        defaultInitViewModel(activity, viewModel)
    }

    private fun defaultInitViewModel(activity: FragmentActivity, viewModel: BaseViewModel) {
        viewModel.apply {
            error.observe(activity, Observer {
                logger("error $it")
                when (activity) {
                    is ViewDelegate -> {
                        activity.toast("$it")
                    }
                }
            })

            loading.observe(activity, Observer {
                it?.run {
                    logger("loading $it")
                    when (activity) {
                        is ViewDelegate -> {
                            if (loading) {
                                activity.showLoading(activity)
                            } else {
                                activity.hideLoading(activity)
                            }

                        }
                    }
                }
            })
        }
    }

}

class ViewDelegateImpl : ViewDelegate {

    private var loadingDialog: AlertDialog? = null

    override fun showLoading(context: Context, msg: String?) {
        loadingDialog?.dismiss()
        when (context) {
            is FragmentActivity -> {
                loadingDialog = loadingDialog(context)
            }
        }
    }

    override fun hideLoading(context: Context, msg: String?) {
        loadingDialog?.dismiss()
    }
}

// </editor-fold>