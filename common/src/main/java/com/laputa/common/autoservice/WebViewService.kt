package com.laputa.common.autoservice

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

interface WebViewService {

    fun startWebVieActivity(context: Context, url: String, extras: Bundle? = null)

    fun createWebViewFragment(url: String, extras: Bundle? = null): Fragment

}