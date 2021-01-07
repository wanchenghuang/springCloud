package com.chauncy.cloud.thread.primary.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 21:52
 *
 * 8个锁问题
 * 1、标准情况下，两个线程先打印 发短信/打电话？ 1/发短信 2/打电话   发短信
 * 1、sendSms延迟4秒，两个线程先打印 发短信/打电话？ 1/发短信 2/打电话   发短信
 */
public class Test1 {

    public static void main(String[] args) {

        Phone phone = new Phone();

        new Thread(() ->{
            phone.sendSms();
        },"A").start();

        //捕获
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            phone.call();
        },"B").start();
    }
}
class Phone{

    //Synchronized 锁的对象是方法的调用者
    //两个方法用的是同一个方法，谁先拿到谁先执行！
    public synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");

    }
}
