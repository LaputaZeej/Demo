package com.laputa.webview.webviewprocess

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import androidx.core.os.bundleOf
import com.bugull.android.extension.base.BaseApplication
import com.laputa.webview.IWebViewProcessToMainProcessCallback
import com.laputa.webview.IWebViewProcessToMainProcessInterface
import com.laputa.webview.mainprocess.CommandService
import com.laputa.webview.util.e
import com.laputa.webview.util.i

object CommandDispatcher : ServiceConnection {

    private var iWebViewProcessToMainProcessInterface: IWebViewProcessToMainProcessInterface? = null

    fun bindService() {
        BaseApplication.INSTANCE.bindService(
            Intent(BaseApplication.INSTANCE, CommandService::class.java),
            this,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        i("CommandDispatcher onServiceDisconnected")
        iWebViewProcessToMainProcessInterface = null
        bindService()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

        iWebViewProcessToMainProcessInterface =
            IWebViewProcessToMainProcessInterface.Stub.asInterface(service)
        i("CommandDispatcher onServiceConnected $iWebViewProcessToMainProcessInterface")
    }

    fun executeCommand(commandName: String, param: String?, handler: Handler) {
        i("CommandDispatcher executeCommand: $commandName ---> $param")
        try {
            iWebViewProcessToMainProcessInterface?.handleCommand(commandName, param, object : IWebViewProcessToMainProcessCallback.Stub() {
                    override fun onResult(callbackName: String?, response: String?) {
                        val obtainMessage = handler.obtainMessage()
                        obtainMessage.data = bundleOf(CALLBACKNAME to callbackName, RESPONSE to response)
                        handler.sendMessage(obtainMessage)
                    }
                })
        } catch (e: Throwable) {
            e(e)
        }
    }

    const val CALLBACKNAME = "callbackName"
    const val RESPONSE = "response"
}