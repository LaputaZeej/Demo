package com.bugull.android.ext;

import com.bugull.android.ext.demo03.TestPP;

import org.junit.Test;

public class UnitTest {
    @Test
    public void test() {
//        com.bugull.android.ext.Test test = new com.bugull.android.ext.Test();
//        Runnable runnable = () -> {
//
//        };
//        test.test1(runnable,()->{
//
//        });
//
//        test.test2(runnable,()->{
//
//        });

//        TestPP<?> testPP = new TestPP<>(new Integer[]{1, 2, 3});
//        testPP.print();
        TestPP<Object> testPP2 = new TestPP<>();
        System.out.println(testPP2.value.getClass());
        Object[] value = testPP2.value;
        System.out.println(value.getClass());
        testPP2.value[0] = 10;
//        value[0] = 10;
//        value[1] = 11f;
        testPP2.print();
    }
}
