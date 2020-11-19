package com.bugull.android.ext.command

import android.widget.Toast
import com.bugull.android.extension.base.BaseApplication
import com.google.auto.service.AutoService
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.laputa.base.autoservice.ServiceLoaderHelper
import com.laputa.common.autoservice.LoginService
import com.laputa.webview.IWebViewProcessToMainProcessCallback
import com.laputa.webview.command.Command
import com.laputa.webview.util.e
import com.laputa.webview.util.i
import kotlinx.coroutines.*
import kotlin.random.Random

@AutoService(value = [Command::class])
class LoginCommand(override val name: String = "login") : Command,
    CoroutineScope by CoroutineScope(SupervisorJob() + Dispatchers.IO) {


    private val gson: Gson by lazy { Gson() }
    private val loginService: LoginService by lazy { ServiceLoaderHelper.load(LoginService::class.java) }

    override fun execute(param: String?, callback: IWebViewProcessToMainProcessCallback?) {
        val fromJson = gson.fromJson(param, JsonObject::class.java)
        val callbacknameFromNativeJs = fromJson?.get("callbackname")?.asString

        if (callbacknameFromNativeJs.isNullOrEmpty()) {
            e("command:$name ,param:callbacknameFromNativeJs is null")
        } else {
            launch(Dispatchers.Main) {
                Toast.makeText(BaseApplication.INSTANCE, "登陆中...", Toast.LENGTH_SHORT).show()
                val result = withContext(Dispatchers.IO) {
                    login("zeej", "123456")
                }
                Toast.makeText(BaseApplication.INSTANCE, "登陆完成！$result", Toast.LENGTH_SHORT).show()
                i("登陆成功...$result")
                val mapOf = mapOf<String, String>("accountName" to result)
                callback?.onResult(callbacknameFromNativeJs, gson.toJson(mapOf))
            }
        }
    }

    private suspend fun login(username: String, password: String) =
        suspendCancellableCoroutine<String> { contination ->
            i("登陆中...")
            if (loginService.logined) {
                loginService.login(username, password) {
                    contination.resumeWith(Result.success(it))
                }
            } else {
                contination.resumeWith(Result.success("已经登录过了！"))
            }

        }
}