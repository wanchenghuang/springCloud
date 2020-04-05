package com.chauncy.cloud.thread.primary.ch3.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author cheng
 * @create 2020-04-04 17:54
 *
 * 读写锁的降级示例
 * 一个线程持有读写锁的写锁的情况下可以继续获得相应的读锁
 *
 * 锁的降级的反面是锁的升级，即一个线程在持有读写锁的读锁的情况下，申请相应的写锁。
 * ReentrantReadWriteLock不支持升级，读线程如果要转而申请写锁，需要先释放读锁，然后申请相应的写锁
 */
public class ReadWriteLockDowngrade {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public void operationWithLockDowngrade() {
        boolean readLockAcquired = false;
        writeLock.lock(); // 申请写锁
        try {
            // 对共享数据进行更新
            // ...
            // 当前线程在持有写锁的情况下申请读锁readLock
            readLock.lock();
            readLockAcquired = true;
        } finally {
            writeLock.unlock();// 释放写锁
        }

        if (readLockAcquired) {
            try {
                // 读取共享数据并据此执行其他操作
                // ...

            } finally {
                readLock.unlock();// 释放读锁
            }
        } else {
            // ...
        }
    }

}
