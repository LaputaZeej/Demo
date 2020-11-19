package com.laputa.webview

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.auto.service.AutoService
import com.laputa.common.autoservice.WebViewService
import com.laputa.webview.util.i

@AutoService(value = [WebViewService::class])
class WebViewServiceImpl : WebViewService {
    override fun startWebVieActivity(context: Context, url: String, extras: Bundle?) {
        i("WebViewServiceImpl#startWebVieActivity url=$url,extras=$extras")
        WebViewActivity.open(context, url, extras)
    }

    override fun createWebViewFragment(url: String, extras: Bundle?): Fragment {
        i("createWebViewFragment#startWebVieActivity url=$url,extras=$extras")
        return WebViewFragment.newInstance(url, extras)
    }

    companion object {
    }

}