package com.chauncy.cloud.thread.primary.ch5.case01_alarm;

import com.chauncy.cloud.thread.common.Debug;
import com.chauncy.cloud.thread.common.Tools;

import java.util.Random;

/**
 * @Author cheng
 * @create 2020-04-05 21:51
 * <p>
 * 告警功能
 * 角色：网络连接、心跳检测、告警发送
 * <p>
 * 该模块中的告警代理(AlarmAgent类，如清单5-1所示） 负责与告警服务器建立网络连接， 并对外暴露一个sendAlarm方法用于将指定 的告警消息上报到告警服务器上。AlarmAgent
 * 内部会维护两个工作者线程：
 * 一、个工作者线程负责与告警服务器建立网络连接(Socket连接），我们称该线程为网络连接线程；
 * 二、一个工作者线程负责定时检测告警代理与告警服务器的网络连接状况，我们称该线程为心跳线程。
 * 告警模块还专门维护了一个告警发送线程，该工作者线程通过调用AlarmAgent.sendAlarm(String)将该模块接收到 的告警消息 上报给告警服务器。
 * 由于告警发送线程执行AlarmAgent.sendAlarm(String)的时候AlarmAgent与告警服务
 * 器的网络连接可能尚未建立 完毕， 或者中途由于一些故障（比如告警服务器宥机）连接已经中断，
 * 因此该线程需要等待AlarmAgent与告警服务器的连接完毕或者恢复连接之后才 能上报告警消息，
 * 否则会导致告警上报失败。
 * 这里，我们可以使用wait/notify实现一套等 待／通知 的机制：
 * 告警发送线程在上报告警消息前必须等待 ， 直到AlarmAgent与告警服务 器的连接成功建立或者恢复；
 * 心跳线程在检测到网络连接恢复 之后通知告警发送线程
 */
public class AlarmAgent {

    private AlarmAgent() {
    }

    private static final AlarmAgent INSTANCE = new AlarmAgent();

    public static AlarmAgent getInstance() {
        return INSTANCE;
    }

    // 心跳线程，用于检测告警代理与告警服务器的网络连接是否正常
    private final HeartbeartThread heartbeatThread = new HeartbeartThread();

    // 是否连接上告警服务器
    private boolean connectedToServer = false;

    public void init() {
        connectToServer();
        heartbeatThread.setDaemon(true);
        heartbeatThread.start();
    }

    private void connectToServer() {
        // 创建并启动网络连接线程，在该线程中与告警服务器建立连接
        new Thread() {
            @Override
            public void run() {
                doConnect();
            }
        }.start();
    }

    private void doConnect() {
        // 模拟实际操作耗时
        Tools.randomPause(100);
        synchronized (this) {
            connectedToServer = true;
            // 连接已经建立完毕，通知以唤醒告警发送线程
            notify();
        }
    }

    //发送警告
    public void sendAlarm(String message) throws InterruptedException {
        synchronized (this) {
            // 使当前线程等待直到告警代理与告警服务器的连接建立完毕或者恢复
            while (!connectedToServer) {
                Debug.info("Alarm agent was not connected to server.");
                wait();
            }
            // 真正将告警消息上报到告警服务器
            doSendAlarm(message);
        }
    }

    private void doSendAlarm(String message) {
        // ...
        Debug.info("Alarm sent:%s", message);
    }


    //心跳线程
    class HeartbeartThread extends Thread {
        @Override
        public void run() {
            try {
                // 留一定的时间给网络连接线程与告警服务器建立连接
                Thread.sleep(1000);
                while (true) {
                    if (checkConnection()) {
                        connectedToServer = true;
                    } else {
                        connectedToServer = false;
                        Debug.info("Alarm agent was disconnected from server.");

                        // 检测到连接中断，重新建立连接
                        connectToServer();
                    }
                    Thread.sleep(2000);
                }

            } catch (InterruptedException e) {

            }

        }

        // 检测与告警服务器的网络连接情况
        private boolean checkConnection() {
            boolean isConnected = true;
            final Random random = new Random();

            // 模拟随机性的网络断链
            int rand = random.nextInt(1000);
            if (rand <= 500) {
                isConnected = false;
            }
            return isConnected;
        }
    }
}
