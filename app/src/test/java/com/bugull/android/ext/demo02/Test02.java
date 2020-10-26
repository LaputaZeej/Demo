package com.bugull.android.ext.demo02;

import kotlin.Unit;

public class Test02 {
    public static void main(String[] args) {
        Demo02Kt.test(() -> {
            //
            return null;
        });

        Demo02Kt.test01(() -> {

        });
        Demo02Kt.test02(() -> {
            return Unit.INSTANCE;
        });


    }
}

