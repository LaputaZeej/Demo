package com.bugull.android.http

abstract class Response<T>

data class HttpResponse<T>(val success: Boolean, val code: Int, val msg: String, val data: T) :
    Response<T>()

/**
 * 业务错误
 * 比如接口根据code=103 token过期等等
 */
class BusinessException(var code: Int, override val message: String?) : Exception(message)

/**
 * 解析response
 */
suspend fun <T> suspendResult(resourceAction: suspend () -> HttpResponse<T>): Result<T> = try {
    val httpResponse: HttpResponse<T> = resourceAction()
    if (httpResponse.success) {
        Result.success(httpResponse.data)
    } else {
        Result.failure(BusinessException(httpResponse.code, httpResponse.msg))
    }
} catch (e: Throwable) {
    Result.failure(e);
}

/**
 * 转换 resp -> data
 */
fun <T, R> Result<T?>.convert(action: T?.() -> R?): Result<R?> = when {
    this.isSuccess -> {
        Result.success(action(this.getOrNull()))
    }
    else -> Result.failure(this.exceptionOrNull())
}

/**
 * 解析response
 * 转换 resp -> data
 */
suspend fun <T, R> suspendResultAndConvert(
    resourceAction: suspend () -> HttpResponse<T>,
    convert: (T) -> R?
): Result<R?> = try {
    val httpResponse: HttpResponse<T> = resourceAction()
    if (httpResponse.success) {
        Result.success(convert(httpResponse.data))
    } else {
        Result.failure(BusinessException(httpResponse.code, httpResponse.msg))
    }
} catch (e: Throwable) {
    Result.failure(e)
}

//class ErrorMsg
//
//interface ErrorMsgInter<T> {
//    fun catch(error: ErrorMsg): ErrorMsg
//}
//
//fun errorResponse(throwable: Throwable) = when (throwable) {
//    is BusinessException -> {
//        when (throwable.code) {
//            103 -> "token过期"
//            else -> {
//                "${throwable.message}"
//            }
//        }
//    }
//    else -> {
//        "${throwable.message}"
//    }
//}




