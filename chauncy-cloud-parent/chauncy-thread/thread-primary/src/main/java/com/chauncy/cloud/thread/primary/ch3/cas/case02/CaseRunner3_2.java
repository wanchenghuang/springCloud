package com.chauncy.cloud.thread.primary.ch3.cas.case02;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-05 13:44
 */
public class CaseRunner3_2 {

    public static void main(String[] args) throws InterruptedException {
        final AlarmMgr alarmMgr = AlarmMgr.INSTANCE;
        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        for (int i = 0; i < threads.length; i++) {
            // 模拟多个线程调用alarmMgr.init();
            threads[i] = new Thread() {
                @Override
                public void run() {
                    alarmMgr.init();
                }
            };
        }

        // 启动并等待指定的线程结束
        Tools.startAndWaitTerminated(threads);
    }


}
