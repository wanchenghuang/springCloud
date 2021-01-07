package com.chauncy.cloud.thread.primary.kuang.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 17:42
 *
 * 1、Synchronized 内置的Java关键字， Lock 是一个Java类
 * 2、Synchronized 无法判断获取锁的状态，Lock 可以判断是否获取到了锁
 * 3、Synchronized 会自动释放锁，lock 必须要手动释放锁！如果不释放锁，死锁
 * 4、Synchronized 线程 1（获得锁，阻塞）、线程2（等待，傻傻的等）；Lock锁就不一定会等待下
 * 去；
 * 5、Synchronized 可重入锁，不可以中断的，非公平；Lock ，可重入锁，可以 判断锁，非公平（可以
 * 自己设置）；
 * 6、Synchronized 适合锁少量的代码同步问题，Lock 适合锁大量的同步代码！
 *
 */
public class SaleTicketDemo02 {

    public static void main(String[] args) {

        // 并发：多线程操作同一个资源类, 把资源类丢入线程
        Ticket2 ticket = new Ticket2();
        // @FunctionalInterface 函数式接口，jdk1.8  lambda表达式 (参数)->{ 代码 }
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"A").start();
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"B").start();
        new Thread(()->{for (int i = 1; i < 40 ; i++) ticket.sale();},"C").start();
    }
}

//lock使用
//1、new ReentrantLock
//2、加锁 lock.lock
//3、-> finally 释放锁 lock.unlock
class Ticket2 {
    //属性、方法
    private int number = 50;

    Lock lock = new ReentrantLock();

    public void sale() {
        //加锁
        lock.lock();
        try{
            //业务代码
            if (number > 0 ) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票,剩余：" + number);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            lock.unlock();
        }
    }
}
