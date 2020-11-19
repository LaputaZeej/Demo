package com.laputa.webview.mainprocess

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.laputa.webview.IWebViewProcessToMainProcessCallback
import com.laputa.webview.IWebViewProcessToMainProcessInterface
import com.laputa.webview.command.Command
import com.laputa.webview.util.i
import java.util.*

// WebView process
// Main Process
// 通信服务
class CommandService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        i("CommandService onBind  ${this.hashCode()}")
        return ProcessCommandsManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private object ProcessCommandsManager : IWebViewProcessToMainProcessInterface.Stub() {
        // 所有的命令
        private val mCommands: MutableMap<String, Command> = mutableMapOf()

        private val handle: Handler = Handler(Looper.getMainLooper())

        init {
            val load: ServiceLoader<Command>? = ServiceLoader.load(Command::class.java)
            load?.forEach {
                if (!mCommands.containsKey(it.name)) {
                    mCommands[it.name] = it
                }
            }
            i("[ProcessCommandsManager] mCommands = $mCommands ${this.hashCode()}")
        }

        override fun handleCommand(commandName: String?, param: String?, callback: IWebViewProcessToMainProcessCallback?) {
            i("[ProcessCommandsManager] $commandName ---> $param")
            if (commandName.isNullOrEmpty()) return
            handle.post {
                executeCommand(commandName, param,callback)
            }
        }

        private fun executeCommand(commandName: String, params: String?,callback: IWebViewProcessToMainProcessCallback?) {
            mCommands[commandName]?.execute(params,callback)
        }

    }


}