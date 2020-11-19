package com.laputa.login

import com.google.auto.service.AutoService
import com.laputa.common.autoservice.LoginService
import kotlin.random.Random

@AutoService(value = [LoginService::class])
class LoginServiceImpl : LoginService {
    override val logined: Boolean
        get() = Random.nextBoolean()

    override fun login(username: String, password: String, callback: ((String) -> Unit)?) {
        Thread.sleep(5000)
        callback?.invoke(username + "=" + password + Random.nextBits(2))
    }
}