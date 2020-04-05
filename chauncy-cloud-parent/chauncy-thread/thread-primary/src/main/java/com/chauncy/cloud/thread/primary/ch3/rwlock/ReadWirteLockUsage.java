package com.chauncy.cloud.thread.primary.ch3.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author cheng
 * @create 2020-04-04 17:52
 *
 * 读写锁使用方法
 *
 * 1、任何一个线程获取读锁的时候都不能获得相应的写锁，读锁是共享锁，使得多个线程能够以线程安全的方式
 * 在同一时刻对共享变量进行读取
 *
 * 2、写锁是排他锁，保障了写线程能够以独占的方式安全地更新共享变量
 */
public class ReadWirteLockUsage {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    // 读线程执行该方法
    public void reader() {
        readLock.lock(); // 申请读锁
        try {
            // 在此区域读取共享变量
        } finally {
            readLock.unlock();// 总是在finally块中释放锁，以免锁泄漏
        }
    }

    // 写线程执行该方法
    public void writer() {
        writeLock.lock(); // 申请读锁
        try {
            // 在此区域访问（读、写）共享变量
        } finally {
            writeLock.unlock();// 总是在finally块中释放锁，以免锁泄漏
        }
    }

}
