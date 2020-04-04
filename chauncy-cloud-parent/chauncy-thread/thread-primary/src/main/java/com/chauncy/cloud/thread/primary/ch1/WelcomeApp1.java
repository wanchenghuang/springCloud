package com.chauncy.cloud.thread.primary.ch1;

/**
 * @Author cheng
 * @create 2020-04-02 23:21
 * Thread类常用的两个构造器 Thread()和Thread(Runnable target)
 * 创建线程
 *
 * 不管采用哪种方法，一旦线程的run方法执行(由java虚拟机调用)结束（包括正常和异常），那么相应的线程的运行就结束了
 * 结束的线程占用的资源被java虚拟机垃圾回收
 *
 * 以实现Runnable接口实例的方式创建线程
 */
public class WelcomeApp1 {

    public static void main(String[] args) {

        // 创建线程
        Thread welcomeThread = new Thread(new WelcomeTask());
        Thread welcomeThread2 = new Thread(new WelcomeTask());
        Thread welcomeThread3 = new Thread(new WelcomeTask());
        Thread welcomeThread4 = new Thread(new WelcomeTask());
        welcomeThread.setName("welcomeThread");
        welcomeThread2.setName("welcomeThread2");
        welcomeThread3.setName("welcomeThread3");
        welcomeThread4.setName("welcomeThread4");

        // 启动线程
        welcomeThread.start();
        welcomeThread2.start();
        welcomeThread3.start();
        welcomeThread4.start();
        // 输出“当前线程”的线程名称
        System.out.printf("1.Welcome! I'm %s.%n", Thread.currentThread().getName());

    }
}

class WelcomeTask implements Runnable{

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


