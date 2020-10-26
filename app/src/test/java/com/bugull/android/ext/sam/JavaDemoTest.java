package com.bugull.android.ext.sam;

import java.util.function.Function;

public class JavaDemoTest {
    private Item item;

    public String delegateWork(JavaInterface f) {
        return f.doSomething(item);
    }

    public String delegateOtherWork(Function<Item, String> f) {
        return f.apply(item);
    }

    void doWork() {
        delegateWork(new JavaInterface() {
            @Override
            public String doSomething(Item item) {
                return "Item = " + item;
            }
        });


        delegateWork(item -> "Item = " + item);
        delegateOtherWork(item -> "Item = " + item);


        KotlinDemoTest kotlinDemoTest = new KotlinDemoTest(new Item());
        kotlinDemoTest.delegateWork000(() -> {
        });
        kotlinDemoTest.delegateWork(it -> it.name);
        kotlinDemoTest.delegateOtherWork(item -> item.name);
        kotlinDemoTest.delegateOtherWork(item -> item.name);

        JavaInterface1 f1 = item -> "Print " + item;
        JavaInterface2 f2 = item -> "Print " + item;
        //f1 = f2; // error
    }

    public void test01(Runnable runnable) {

    }

    public void test02(String tag, Runnable runnable) {

    }

    public void test03(Object tag, Runnable runnable) {

    }

    public void test04(Runnable tag, Runnable runnable) {

    }
}
