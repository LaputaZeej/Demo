package com.bugull.android.ext.bit;

import org.junit.Test;

public class TestBitJava {
    @Test
    public void test002() {
        getBit(0b1101_1111);
    }

    private int getBit(int data) {
        // 1.原始 0b11011111 取3-6位
        // 2.操作 0b00011100 &上
        // 3.操作 0b00011100 得到结果
        // 4.右移 0b000111   右移2位
        // 5.最终 0b111       去掉多余0
        System.out.println("原始data =" + data);
        int b = (data & 0b0001_1100) // 第2步
                >> 2; // 第4步
        System.out.println("计算结果 =" + b);
        System.out.println("与期望比较" + (b == 0b111));
        return b;
    }
}
