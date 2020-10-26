package com.bugull.android.extension;

import org.junit.Test;

public class TestUnitJava {

    @Test
    public void t(){
        A a = new A();
        String hello = a.getHello();
        String helloKt = a.getHelloKt();
    }
}
