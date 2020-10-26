package com.bugull.android.ext.kotlin;

public class MM {
    int sa = 2;

    class MMM {
        protected void f() {
            System.out.println("fffff" + sa);
        }
    }

    public static void main(String[] args) {
        MMM mmm = new MM().new MMM() {
            @Override
            protected void f() {
                super.f();
            }
        };
    }
}
