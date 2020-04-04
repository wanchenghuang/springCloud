package com.chauncy.cloud.thread.primary.ch2.automic;

/**
 * @Author cheng
 * @create 2020-04-03 16:57
 *
 * 非竞态
 */
public class NoRaceCondition {

    public int nextSequence(int sequence) {

        // 以下语句使用的是局部变量而非状态变量，并不会产生竞态
        if (sequence >= 999) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

}
