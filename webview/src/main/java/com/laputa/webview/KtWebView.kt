package com.laputa.webview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.webkit.*
import com.google.gson.Gson
import com.laputa.webview.bean.JsObject
import com.laputa.webview.util.e
import com.laputa.webview.util.i
import com.laputa.webview.webviewprocess.CommandDispatcher
import java.lang.ref.WeakReference

class KtWebView
@JvmOverloads
@SuppressLint("JavascriptInterface")
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    WebView(context, attrs, defStyleAttr) {

    init {
        this.addJavascriptInterface(this, JAVASCRIPT_INTERFACE_NAME)
        WebViewDefaultSettings.setSettings(this)
        CommandDispatcher.bindService()
    }

    fun setCallback(
        onPageStarted: ((String) -> Unit)? = null,
        onPageFinished: ((String) -> Unit)? = null,
        onError: ((String) -> Unit)? = null,
        onTitleUpdate: ((String) -> Unit)? = null
    ) {
        webViewClient = KtWebViewClient(onPageStarted, onPageFinished, onError)
        webChromeClient = KtWebChromeClient(onTitleUpdate)
    }

    private val gson: Gson by lazy { Gson() }

    // takeNativeAction 对应js里的方法
    // 即：lauputawebview.takeNativeAction
    // 映射给js
    @JavascriptInterface
    fun takeNativeAction(json: String) {
        i("json: $json")
        val jsObject: JsObject? = gson.fromJson(json, JsObject::class.java)
        val name = jsObject?.name

        if (name.isNullOrEmpty()) {
            i("其他")
        } else {
            val param = jsObject.param
            CommandDispatcher.executeCommand(name, param.toString(), callbackHandler)
        }
    }

    private val callbackHandler: Handler = CallbackHandler(this)

    private class CallbackHandler(webView: WebView) : Handler() {
        private val value: WeakReference<WebView> = WeakReference(webView)
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val callbackName = msg?.data?.getString(CommandDispatcher.CALLBACKNAME)
            if (!callbackName.isNullOrEmpty()) {
                val response = msg.data?.getString(CommandDispatcher.RESPONSE)
                val jscode =
                    "javascript:laputajs.callback('$callbackName',${response ?: ""})"
                e(jscode)
                value.get()?.evaluateJavascript(jscode, null)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        callbackHandler.removeCallbacksAndMessages(null)
    }

    companion object {
        private const val JAVASCRIPT_INTERFACE_NAME = "lauputawebview" /*对应js里面的lauputawebview*/

    }

    private class KtWebViewClient(
        val onPageStarted: ((String) -> Unit)? = null,
        val onPageFinished: ((String) -> Unit)? = null,
        val onError: ((String) -> Unit)? = null
    ) : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onPageStarted?.invoke(url)
        }

        override fun onPageFinished(view: WebView?, url: String) {
            super.onPageFinished(view, url)
            onPageFinished?.invoke(url)
        }

        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            onError?.invoke(description ?: "")
        }


    }

    private class KtWebChromeClient(val onTitleUpdate: ((String) -> Unit)? = null) :
        WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            i(consoleMessage?.message())
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            onTitleUpdate?.invoke(title ?: "")
        }


    }

    object WebViewDefaultSettings {
        private fun isNetworkConnected(context: Context): Boolean {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo
            return networkInfo?.isConnected ?: false
        }

        @SuppressLint("SetJavaScriptEnabled")
        fun setSettings(webView: WebView) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                WebView.enableSlowWholeDocumentDraw()
            }
            val mWebSettings = webView.settings
            mWebSettings.setJavaScriptEnabled(true)
            mWebSettings.setSupportZoom(true)
            mWebSettings.setBuiltInZoomControls(false)
            if (isNetworkConnected(webView.context)) {
                mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT)
            } else {
                mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW)
            }

            // 硬件加速兼容性问题有点多
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
            mWebSettings.setTextZoom(100)
            mWebSettings.setDatabaseEnabled(true)
            mWebSettings.setAppCacheEnabled(true)
            mWebSettings.setLoadsImagesAutomatically(true)
            mWebSettings.setSupportMultipleWindows(false)
            mWebSettings.setBlockNetworkImage(false) //是否阻塞加载网络图片  协议http or https
            mWebSettings.setAllowFileAccess(true) //允许加载本地文件html  file协议
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mWebSettings.setAllowFileAccessFromFileURLs(false) //通过 file url 加载的 Javascript 读取其他的本地文件 .建议关闭
                mWebSettings.setAllowUniversalAccessFromFileURLs(false) //允许通过 file url 加载的 Javascript 可以访问其他的源，包括其他的文件和 http，https 等其他的源
            }
            mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
            } else {
                mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)
            }
            mWebSettings.setSavePassword(false)
            mWebSettings.setSaveFormData(false)
            mWebSettings.setLoadWithOverviewMode(true)
            mWebSettings.setUseWideViewPort(true)
            mWebSettings.setDomStorageEnabled(true)
            mWebSettings.setNeedInitialFocus(true)
            mWebSettings.setDefaultTextEncodingName("utf-8") //设置编码格式
            mWebSettings.setDefaultFontSize(16)
            mWebSettings.setMinimumFontSize(10) //设置 WebView 支持的最小字体大小，默认为 8
            mWebSettings.setGeolocationEnabled(true)
            mWebSettings.setUseWideViewPort(true)
            val appCacheDir =
                webView.context.getDir("cache", Context.MODE_PRIVATE).path
            Log.i("WebSetting", "WebView cache dir: $appCacheDir")
            mWebSettings.setDatabasePath(appCacheDir)
            mWebSettings.setAppCachePath(appCacheDir)
            mWebSettings.setAppCacheMaxSize(1024 * 1024 * 80.toLong())

            // 用户可以自己设置useragent
            // mWebSettings.setUserAgentString("webprogress/build you agent info");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
            }
        }
    }
}

