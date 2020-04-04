package com.chauncy.cloud.thread.primary.ch2.visibility;

/**
 * @Author cheng
 * @create 2020-04-04 10:18
 *
 * 猜测执行能够造成if语句的语句体优先于其条件语句被执行的效果
 * 即可能导致指令重排
 */
public class SpeculativeLoadExample {
    private boolean ready = false;
    private int[] data = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };

    public void writer() {
        int[] newData = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        for (int i = 0; i < newData.length; i++) {// 语句①（for循环语句）

            // 此处包含读内存的操作
            newData[i] = newData[i] - i;
        }
        data = newData;
        // 此处包含写内存的操作
        ready = true;// 语句②
    }

    public int reader() {
        int sum = 0;
        int[] snapshot;
        if (ready) {// 语句③（if语句）
            snapshot = data;
            for (int i = 0; i < snapshot.length; i++) {// 语句④（for循环语句）
                sum += snapshot[i];// 语句⑤
            }
            //不同线程执行writer、reader

            //指令重排重排导致语句⑤先执行，在执行语句③,导致reader此时数组的内容可能为[1, 2, 3, 4, 5, 6, 7, 8]
            //而不是预想的[1,1,1,1,1,1,1]
        }
        return sum;
    }
}

