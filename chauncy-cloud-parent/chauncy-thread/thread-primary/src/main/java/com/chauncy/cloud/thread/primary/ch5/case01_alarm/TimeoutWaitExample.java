package com.chauncy.cloud.thread.primary.ch5.case01_alarm;

import com.chauncy.cloud.thread.common.Debug;
import com.chauncy.cloud.thread.common.Tools;

import java.util.Random;

/**
 * @Author cheng
 * @create 2020-04-05 22:15
 *
 * 使用Objec.wait(long)实现等待超时控制
 *
 * 无法知道是自然唤醒还是等待超时唤醒
 */
public class TimeoutWaitExample {
    private static final Object lock = new Object();
    private static boolean ready = false;
    protected static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    synchronized (lock) {
                        ready = random.nextInt(100) < 5 ? true : false;
                        if (ready) {
                            lock.notify();
                        }
                    }
                    // 使当前线程暂停一段（随机）时间
                    Tools.randomPause(500);
                }// for循环结束
            }
        };
        t.setDaemon(true);
        t.start();
        waiter(1000);
    }

    public static void waiter(final long timeOut) throws InterruptedException {
        if (timeOut < 0) {
            throw new IllegalArgumentException();
        }

        long start = System.currentTimeMillis();
        long waitTime;
        long now;
        synchronized (lock) {
            while (!ready) {
                now = System.currentTimeMillis();
                // 计算剩余等待时间
                waitTime = timeOut - (now - start);
                Debug.info("Remaining time to wait:%sms", waitTime);
                if (waitTime <= 0) {
                    // 等待超时退出
                    break;
                }
                lock.wait(waitTime);
            }// while循环结束

            if (ready) {
                // 执行目标动作
                guardedAction();
            } else {
                // 等待超时，保护条件未成立
                Debug.error("Wait timed out,unable to execution target action!");
            }
        }// 同步块结束
    }

    private static void guardedAction() {
        Debug.info("Take some action.");
        // ...
    }
}

