package com.bugull.android.ext

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Process
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bugull.android.ext.app.MyApp.Companion.SKIN
import com.bugull.android.ext.data.UserData
import com.bugull.android.ext.recycler.SlideActivity
import com.bugull.android.ext.recycler.SlideAdapter
import com.bugull.android.ext.recycler.SlideCard
import com.bugull.android.extension.base.BaseActivity
import com.bugull.android.extension.base.viewModels
import com.bugull.android.extension.base.viewModelsFactory
import com.bugull.android.extension.logger
import com.bugull.android.extension.toast
import com.bugull.android.selector.photo.SelectorPhotoFragment
import com.bugull.android.selector.photo.SelectorPhotoFragmentFactory
import com.bugull.android.selector.photo.SelectorPhotoViewModel
import com.gyf.barlibrary.ImmersionBar
import com.laputa.base.autoservice.ServiceLoaderHelper
import com.laputa.common.autoservice.WebViewService
import com.laputa.linked.HomeActivity
import com.laputa.skin.SkinManager
import com.laputa.webview.util.assetsUrl
import com.laputa.webview.util.i
import com.luck.picture.lib.entity.LocalMedia
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.internal.MainDispatcherFactory
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.*
import kotlin.random.Random

class MainActivity(
    override val layoutResId: Int = R.layout.activity_main
) : BaseActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        testBundle()
        testSkin()
        initViewModel()
        initViewS()


    }

    private fun initViewModel() {
        //        mainViewModel = viewModels<MainViewModel>(MainViewModelFactory())
        mainViewModel = viewModelsFactory<MainViewModel> {
            MainViewModelFactory()
        }
            //ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
            .apply {
                /*  userData.observe(this@MainActivity, Observer {
                      Toast.makeText(this@MainActivity, "data:${it}", Toast.LENGTH_SHORT).show()
                  })

                  error.observe(this@MainActivity, Observer {
                      Toast.makeText(this@MainActivity, "error:${it}", Toast.LENGTH_SHORT).show()
                  })*/

                loading.observe(this@MainActivity, Observer {
                    it?.run {
                        tv_hello.text = it.msg
                    }
                })

                mineLiveData.observe(this@MainActivity, Observer {
                    toast(it?.toString() ?: "没有用户信息")
                })


            }
        mainViewModel.initMine()

        initViewModel(this, mainViewModel)

        viewModels<SelectorPhotoViewModel>().apply {
            localMediaList.observe(this@MainActivity, Observer<List<LocalMedia>> { list ->
                val msg = list?.joinToString {
                    "${it.path} ${it.androidQToPath}"
                } ?: " 空"
                logger("Observer MainActivity $msg")
                tv_hello.text = msg
            })

        }
    }

    private fun initViewS() {
        /*MainScope().launch {
            delay(1000)
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }*/

        tv_hello.setOnClickListener {
            mainViewModel.login("13040815454", "123456")
            initActionBar {
                ImmersionBar.with(this).apply {
                    barColor(android.R.color.background_dark).init()
                    statusBarDarkFont(false)
                }
            }
            selectorPhoto()
        }

        btn_01.setOnClickListener {
            //            mainViewModel.change("hehehehe")
            textStyle(222)
            startActivity(
                Intent(
                    this@MainActivity,
                    HomeActivity::class.java
                ).putExtras(bundleOf("name" to "zeej"))
            )

        }
        btn_02.setOnClickListener {
            //            mainViewModel.change2("hahahaha")
            textStyle(2)
        }
        btn_03.setOnClickListener {
            //mainViewModel.change("xixixixix")
            textStyle(12)
        }

        i("MainActivity -> "+ Process.myPid())

        test_webview_demo.setOnClickListener {
            ServiceLoaderHelper.load(WebViewService::class.java)
                .startWebVieActivity(this, assetsUrl("demo.html"))
        }
        test_webView.setOnClickListener {
            ServiceLoaderHelper.load(WebViewService::class.java)
                .startWebVieActivity(this, "https://www.baidu.com")
        }

        case_slide_card.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SlideActivity::class.java
                ).putExtras(bundleOf("name" to "zeej"))
            )
        }
    }

    private fun testSkin() {
        MainScope().launch {
            repeat(100) {
                delay(5000)
                if (Random.nextBoolean()) {
                    SkinManager.loadSkin(null)
                } else {
                    SkinManager.loadSkin(SKIN)
                }
            }
        }
    }

    private fun testBundle() {
        MainScope().launch {
            delay(2000)
            startActivity(
                Intent(
                    this@MainActivity,
                    SplashActivity::class.java
                ).putExtras(bundleOf("name" to "zeej"))
            )
        }
    }

    private fun selectorPhoto() {
        supportFragmentManager.beginTransaction().add(
            SelectorPhotoFragment.newInstance(SelectorPhotoFragmentFactory.Type.Album),
            "selectorPhoto"
        ).commit()
    }

    private fun textStyle(value: Int) {
        val end = when (value) {
            in 0..9 -> {
                2
            }
            in 10..99 -> {
                1
            }
            else -> {
                0
            }
        }
        val span = SpannableString(String.format("%03d", value))
        val spannableString = SpannableString(span).apply {
            setSpan(ForegroundColorSpan(Color.RED), 0, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        }
        tv_hello.text = spannableString
    }

    private fun testScope() {
        MainScope().launch {
            val s = suspendCancellableCoroutine<String> {

            }
            val su = suspend {
                ""
            }
            val l = launch {
                ""
            }
            val ss = su.createCoroutine(object : Continuation<String> {
                override val context: CoroutineContext
                    get() = TODO("Not yet implemented")

                override fun resumeWith(result: Result<String>) {
                    TODO("Not yet implemented")
                }

            })
            suspend {

            }.startCoroutine(ss)
        }


    }
}

private suspend fun test(): String = suspendCancellableCoroutine<String> {

}




