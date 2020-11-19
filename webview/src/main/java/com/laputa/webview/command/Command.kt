package com.laputa.webview.command

import com.laputa.webview.IWebViewProcessToMainProcessCallback

interface Command {
    val name: String
    fun execute(param: String?, callback: IWebViewProcessToMainProcessCallback?)
}