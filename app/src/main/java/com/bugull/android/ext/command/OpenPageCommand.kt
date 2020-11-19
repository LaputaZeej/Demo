package com.bugull.android.ext.command

import android.content.ComponentName
import android.content.Intent
import com.bugull.android.extension.base.BaseApplication
import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.laputa.webview.IWebViewProcessToMainProcessCallback
import com.laputa.webview.command.Command
import com.laputa.webview.util.e

@AutoService(value = [Command::class])
class OpenPageCommand(override val name: String = "openPage") : Command {
    private val gson: Gson by lazy { Gson() }
    override fun execute(param: String?, callback: IWebViewProcessToMainProcessCallback?) {
        val fromJson = gson.fromJson(param, JsonObject::class.java)
        val className = fromJson?.get("target_class")?.asString

        if (className.isNullOrEmpty()) {
            e("command:$name ,param:msg is null")
        } else {
            BaseApplication.startActivity(className)
        }

    }
}