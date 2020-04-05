package com.chauncy.cloud.thread.primary.ch3.security.final1;

/**
 * @Author cheng
 * @create 2020-04-05 14:22
 *
 * final关键字作用，仅保障final字段必然是初始化的，并不能保障可见性
 */
public class FinalFieldExample {

    final int x;
    int y;
    static FinalFieldExample instance;

    public FinalFieldExample() {
        x = 1;
        y = 2;
    }

    public static void writer() {
        instance = new FinalFieldExample();
    }

    public static void reader() {
        final FinalFieldExample theInstance = instance;
        if (theInstance != null) {
            int diff = theInstance.y - theInstance.x;
            // diff的值可能为1(=2-1），也可能为-1（=0-1）。
            print(diff);
        }
    }

    private static void print(int x) {
        // ...
    }


}
