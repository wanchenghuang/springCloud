package com.chauncy.cloud.thread.primary.ch2.visibility;

/**
 * @Author cheng
 * @create 2020-04-04 09:47
 */
public class ReorderingIntro {

    protected static int a, b, c, d;

    public static void main(String[] args) {
        b = a * 2;// 语句①
        c = 1;// 语句②
        d = 2;// 语句③

        System.out.println(a + "," + b + "," + c + "," + d);

    }

}
