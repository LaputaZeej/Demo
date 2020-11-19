package com.laputa.webview

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laputa.webview.util.Constant.KEY_URL
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {
    private var mUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        mUrl = intent?.extras?.getString(KEY_URL)
        iv_back.setOnClickListener {
            finish()
        }
        initWebView()
    }

    private fun initWebView() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fr_content, WebViewFragment.newInstance(mUrl ?: "", intent?.extras))
            .commit()
    }

    fun updateTitle(title:String){
        tv_title.text =  title
    }

    companion object {
        fun open(context: Context, url: String, extras: Bundle?) {
            val intent = Intent().apply {
                setClass(context, WebViewActivity::class.java)
                val data = extras ?: Bundle()
                data.putString(KEY_URL, url)
                putExtras(data)
                when (context) {
                    is Activity -> {

                    }
                    else -> {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                }
            }
            context.startActivity(intent)
        }
    }
}
