package com.laputa.webview.util

fun assetsUrl(fileName: String): String = ANDROID_ASSET_URI + fileName
private const val ANDROID_ASSET_URI = "file:///android_asset/"
