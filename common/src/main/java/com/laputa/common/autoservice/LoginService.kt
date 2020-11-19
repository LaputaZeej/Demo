package com.laputa.common.autoservice

interface LoginService {
    val logined: Boolean
    fun login(username: String, password: String, callback: ((String) -> Unit)?)
}