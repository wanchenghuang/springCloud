package com.chauncy.cloud.thread.primary.ch2.automic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cheng
 * @create 2020-04-04 16:44
 */
public class LockbasedCircularSeqGenerator implements CircularSeqGenerator{

    private short sequence = -1;
    private final Lock lock = new ReentrantLock();

    @Override
    public synchronized short nextSequence() {
        lock.lock();
        try {
            if (sequence >= 999) {
                sequence = 0;
            } else {
                sequence++;
            }
            return sequence;
        }finally {
            lock.unlock();
        }
    }

}
