package com.chauncy.cloud.thread.primary.startaway.start;

import lombok.SneakyThrows;

/**
 * @Description
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/9 17:47
 */
public class ThreadTest {

    public static void main(String[] args) {
        //创建线程
        MyThread thread = new MyThread();

        //启动线程，java虚拟机执行该线程的run方法
        thread.setDaemon(true);
        thread.start();
        //输出"当前线程"的线程名称
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
        System.out.println(Thread.currentThread().isDaemon());

    }
}

class MyThread extends Thread{

    //重写run方法,在该方法中实现线程的任务处理逻辑
    @SneakyThrows
    @Override
    public void run() {
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
        System.out.println(Thread.currentThread().isDaemon());
        Test test = new Test();
        test.print();
    }
}

class Test{
    public synchronized void print() throws InterruptedException {
            this.wait();
    }
}
