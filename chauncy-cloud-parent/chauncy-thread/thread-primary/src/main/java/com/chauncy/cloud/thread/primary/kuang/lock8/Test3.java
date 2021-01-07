package com.chauncy.cloud.thread.primary.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 22:09
 *
 * 5、增加两个静态的同步方法,先打印发短信还是打电话？ 发短信
 * 6、两个对象 增加两个静态的同步方法,先打印发短信还是打电话？
 */
public class Test3 {
    public static void main(String[] args) {

        //两个对象的class类模板只有一个，static锁的是class
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();

        new Thread(() ->{
            phone1.sendSms();
        },"A").start();

        //捕获
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            phone2.call();
        },"B").start();
    }
}

class Phone3{

    //Synchronized 锁的对象是方法的调用者
    //static 静态方法
    //类一加载就有了 锁的是class
    public static synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call(){
        System.out.println("打电话");
    }

    //这里没有锁，不是同步方法，不受锁的影响
    public void hello(){
        System.out.println("hello");
    }
}