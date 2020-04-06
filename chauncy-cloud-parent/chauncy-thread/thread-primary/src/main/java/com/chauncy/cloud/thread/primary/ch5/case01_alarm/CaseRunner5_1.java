package com.chauncy.cloud.thread.primary.ch5.case01_alarm;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-05 22:05
 */
public class CaseRunner5_1 {

    final static AlarmAgent alarmAgent;
    static {
        alarmAgent = AlarmAgent.getInstance();
        alarmAgent.init();
    }

    public static void main(String[] args) throws InterruptedException {

        alarmAgent.sendAlarm("Database offline!");
        Tools.randomPause(12000);
        alarmAgent.sendAlarm("XXX service unreachable!");
    }
}
