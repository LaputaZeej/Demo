package com.bugull.android.ext.kotlin;

import java.util.Random;

public class JavaCode {
    public void test01() {
        final String a = "hello";
        int b = a.length();
    }

    int sum1(int x, int y) {
        return x + y;
    }

    float sum2(int x, float y) {
        return x + y;
    }

    void test02() {
        Runnable r = () -> System.out.println("任务");
        Runnable r1 = () -> {
        };
        new Thread(r).start();
    }

    int test03(int x, int y, Cal cal) {
        return cal.cal(x, y);
    }

    void test03() {
        Cal sum = (x, y) -> x * x + y * y;
        test03(1, 2, sum);
        Cal dev = (x, y) -> x * y;
        test03(3, 4, dev);
    }


    void test04(boolean flag) {
        String a = null;
        if (flag) {
            a = "hellow";
        }
        System.out.println(a.toUpperCase());
    }

    void test06() {
        String a = null;
        if (a != null) {
            int length = a.length();
        }
        int length1 = a.length();// Method invocation 'length' will produce 'NullPointerException'
        boolean is = null instanceof String; // Condition 'null instanceof String' is always 'false'
    }

    void test08(Animal animal) {
        if (animal instanceof Bird) {
            String birdName = ((Bird) animal).getBirdName();
        }
    }

    public void loadA(CallbackA callback) {
        if (callback != null) {
            callback.call(new Random().nextLong());
        }
    }

    public void loadB(long id, CallbackB callback) {
        if (callback != null) {
            callback.call("id=" + id);
        }
    }

    private void test09() {
        loadA(new CallbackA() {
            @Override
            public void call(long id) {
                loadB(id, new CallbackB() {
                    @Override
                    public void call(String value) {
                        System.out.println(value);
                    }
                });
            }
        });
    }

    private void test10(People people) {
        if (people != null && people.getCat() != null && people.getCat().getLeg() != null) {
            int count = people.getCat().getLeg().getCount();
            System.out.println("count = " + count);
        }
    }



}

