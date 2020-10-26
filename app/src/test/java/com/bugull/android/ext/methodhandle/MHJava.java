package com.bugull.android.ext.methodhandle;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;

public class MHJava {


    @Test
    public void test01() {
        TestMethodHandler.testMethodHandlerDemo();
    }

    public static class TestMethodHandler {
        static void testMethodHandlerDemo() {
            try {
                MethodHandles.Lookup lookup = MethodHandles.publicLookup();
                MethodType methodType = MethodType.methodType(String.class, char.class, char.class);
                MethodHandle replace = lookup.findVirtual(String.class, "replace", methodType);
                Object invoke = replace.invoke("hello", Character.valueOf('l'), Character.valueOf('w'));
                System.out.println("result = " + invoke);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}


