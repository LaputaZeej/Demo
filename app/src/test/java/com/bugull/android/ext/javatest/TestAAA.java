package com.bugull.android.ext.javatest;


import org.junit.Test;

public class TestAAA {

    @Test
    public  void ttt() {
        AA aa = new AA();
        AA.BBB b2 = aa.new BBB();
        AA.BBB b1 = aa.new AA.BBB(){};

    }

}