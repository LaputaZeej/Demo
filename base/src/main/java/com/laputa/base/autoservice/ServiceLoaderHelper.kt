package com.laputa.base.autoservice

import java.util.*

object ServiceLoaderHelper {
    @JvmStatic
    fun <S> load(service: Class<S>): S = try {
        ServiceLoader.load(service).iterator().next()
    } catch (e: Throwable) {
        throw e
    }
}