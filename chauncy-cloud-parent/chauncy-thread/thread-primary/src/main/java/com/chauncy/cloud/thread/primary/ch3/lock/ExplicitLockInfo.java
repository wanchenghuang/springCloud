package com.chauncy.cloud.thread.primary.ch3.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author cheng
 * @create 2020-04-04 16:59
 *
 * 演示线程转储显示锁信息
 */
public class ExplicitLockInfo {

    public static final Lock lock = new ReentrantLock();
    private static int shareData = 0;

    public static void main(String[] args) throws Exception{
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    try{
                    Thread.sleep(220000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shareData = 1;
                }finally {
                    lock.unlock();
                }
                }
        });

        t.start();
        Thread.sleep(100);
        lock.lock();
        try {
            System.out.println("sharedData:" + shareData);
        }finally {
            lock.unlock();
        }
    }
}
