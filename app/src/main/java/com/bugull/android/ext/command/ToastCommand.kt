package com.bugull.android.ext.command

import android.widget.Toast
import com.bugull.android.extension.base.BaseApplication
import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.laputa.webview.IWebViewProcessToMainProcessCallback
import com.laputa.webview.command.Command
import com.laputa.webview.util.e

@AutoService(value = [Command::class])
class ToastCommand(override val name: String = COMMOND_SHOWTOAST) : Command {
    private val gson: Gson by lazy { Gson() }
    override fun execute(param: String?,callback: IWebViewProcessToMainProcessCallback?) {
        val fromJson = gson.fromJson(param, JsonObject::class.java)
        val msg = fromJson?.get("message")?.asString

        if (msg.isNullOrEmpty()) {
            e("command:$name ,param:msg is null")
        } else {
            Toast.makeText(BaseApplication.INSTANCE, msg, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private const val COMMOND_SHOWTOAST = "showToast"
    }

}