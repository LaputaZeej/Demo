package com.bugull.android.ext.coroutines;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.GlobalScope;
import kotlinx.coroutines.Job;

public class TestJavaInvokeCoroutines {

    @Test
    public void test01() throws InterruptedException {


                doCoroutines("t1");


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                doCoroutines("t2");
//
//            }
//        }).start();


    }

    private void doCoroutines(final String tag) {
        //CoroutineScopeKt.cancel(GlobalScope.INSTANCE, null);
        CoroutineScope coroutineScope = new CoroutineScope() {
            @NotNull
            @Override
            public CoroutineContext getCoroutineContext() {
                return EmptyCoroutineContext.INSTANCE;
            }
        };
        Object result = TestJavaAndCoroutinesKt.working(new Continuation<Boolean>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return coroutineScope.getCoroutineContext();
            }

            @Override
            public void resumeWith(@NotNull Object obj) {
                System.out.println(tag+"->resumeWith = " + obj);
            }
        });
        if (IntrinsicsKt.getCOROUTINE_SUSPENDED() == result) {
            System.out.println(tag+"->SUSPENDED");
        } else {
            System.out.println(tag+"->result =" + result);
        }

    }
}
