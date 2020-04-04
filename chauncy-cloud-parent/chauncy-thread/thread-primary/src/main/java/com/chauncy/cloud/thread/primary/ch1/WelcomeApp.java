package com.chauncy.cloud.thread.primary.ch1;

/**
 * @Author cheng
 * @create 2020-04-02 23:05
 *
 * Thread类常用的两个构造器 Thread()和Thread(Runnable target)
 * 创建线程
 *
 * 以定义Thread子类方式创建线程
 */
public class WelcomeApp {

    public static void main(String[] args) {

        //创建线程
        Thread welcomeThread = new WelComeThread();

        //启动线程,java虚拟机执行该线程的run()方法
        welcomeThread.start();

        //输出"当前线程"的线程名称
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}

//定义Thread类的子类
class WelComeThread extends Thread{

    //重写run方法,在该方法中实现线程的任务处理逻辑

    @Override
    public void run() {
        System.out.printf("2.Welcome! I'm %s.%n", Thread.currentThread().getName());
    }
}


//打印结果多数是
        //2.Welcome! I'm Thread-0.
        //1.Welcome! I'm main.
//也可能是
        //1.Welcome! I'm main.
        //2.Welcome! I'm Thread-0.

//这是因为welcomeThread线程不一定在打印"1"被执行之前运行
