package com.bugull.android.ext.methodhandle

import org.junit.Test
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType

class MethodHandleDemo {
    @Test
    fun test02() {
        MHJava.TestMethodHandler.testMethodHandlerDemo()
    }

    @Test
    fun test01() {
        try {
            val lookup =
                MethodHandles.publicLookup()
            val methodType = MethodType.methodType(
                String::class.java,
                Char::class.javaPrimitiveType,
                Char::class.javaPrimitiveType
            )
            val replace =
                lookup.findVirtual(String::class.java, "replace", methodType)
            val invoke = replace.invoke(
                "hello",
                Character.valueOf('l'),
                Character.valueOf('w')
            )
            println("result = $invoke")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}


