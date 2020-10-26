package com.bugull.example.http

import com.bugull.android.http.HttpResponse
import com.bugull.android.http.Response

const val HOME = "https://meiqi-test.yunext.com"

data class DefaultResponse<T>(val code: Int, val msg: String, val data: T) :
    Response<T>()

fun <T> DefaultResponse<T>.httpResponse(): HttpResponse<T> = HttpResponse(
    success = this.code == 1000,
    code = this.code,
    msg = this.msg,
    data = this.data
)





