package com.chauncy.cloud.thread.primary.kuang.lock8;

import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 22:09
 * 7、一个静态同步方法，一个普通静态方法，一个对象 先打印发短信还是打电话 ？  打电话
 * 8、一个静态同步方法，一个普通静态方法，两个个对象 先打印发短信还是打电话 ？  打电话，因为是不同锁和发短信同步块延迟了
 */
public class Test4 {
    public static void main(String[] args) {

        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

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

class Phone4{

    //下面两个同步方法用的不是同一个锁

    //静态同步方法 锁的是 class模板
    public static synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //普通同步方法 锁的调用者
    public synchronized void call(){
        System.out.println("打电话");
    }

    //普通方法
    public void hello(){
        System.out.println("hello");
    }
}