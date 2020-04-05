package com.chauncy.cloud.thread.primary.ch2.automic;

/**
 * @Author cheng
 * @create 2020-04-03 21:52
 *
 * synchronized
 */
public class SafeCircularSeqGenerator implements CircularSeqGenerator {
    private short sequence = -1;

    @Override
    public synchronized short nextSequence() {
        if (sequence >= 999) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }
}