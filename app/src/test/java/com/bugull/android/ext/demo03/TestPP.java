package com.bugull.android.ext.demo03;

public class TestPP<T> {
    public T[] value = (T[]) new Object[10];

    public TestPP(){}

    public TestPP(T[] value) {
        this.value = value;
    }

    public void print() {
        for (T t : value) {
            System.out.println("value = " + t);
        }
    }
}





