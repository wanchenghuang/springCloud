package com.chauncy.cloud.thread.primary.ch2.visibility;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-03 21:54
 */
public class VisibilityDemo {

    public static void main(String[] args) throws InterruptedException {
        TimeConsumingTask timeConsumingTask = new TimeConsumingTask();
        Thread thread = new Thread(new TimeConsumingTask());
        thread.start();

        // 指定的时间内任务没有执行结束的话，就将其取消
        Thread.sleep(10000);
        timeConsumingTask.cancel();

        //程序可能一直运行，并没有达到预期效果停止；只因为自线程thread所读取到的toCancel变量的值始终是false,尽管某个时刻
        //main线程将共享变量toCancel更新为true;;
        //可见这里产生了可见性问题，即main线程对共享变量toCancel的更新对自线程thread而言不可见

    }

}

class TimeConsumingTask implements Runnable {

    private volatile boolean toCancel = false;

    @Override
    public void run() {

        while (!toCancel) {
            if (doExecute()) {
                break;
            }
        }

        if (toCancel) {
            System.out.println("Task was canceled.");
        } else {
            System.out.println("Task done.");
        }
    }

    //模拟业务处理
    private boolean doExecute() {
        boolean isDone = false;

        System.out.println("executing....");

        //模拟实际操作的时间消耗
        Tools.randomPause(50);

        return isDone;
    }

    public void cancel() {
        toCancel = true;
        System.out.println(this + " canceled.");
    }

}
