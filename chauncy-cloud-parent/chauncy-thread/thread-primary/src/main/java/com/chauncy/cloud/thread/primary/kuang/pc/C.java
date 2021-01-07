package com.chauncy.cloud.thread.primary.kuang.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 21:31
 *
 * 按顺序通知
 *
 * A 执行完调用B,B执行完调用C，C执行完调用A
 */
public class C {

    public static void main(String[] args) {

        Data3 data = new Data3();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printB();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printC();
            }
        }, "C").start();
    }
}

class Data3{

    Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int num = 1; //1A,2B,3C

    public void printA(){

        lock.lock();

        try{
            //业务 -> 判断 -> 执行-> 通知
            while(num != 1){
                //等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>AAAAAAAA");
            //唤醒，唤醒指定的人，B
            num = 2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printB(){

        lock.lock();

        try{
            //业务 -> 判断 -> 执行-> 通知
            while(num != 2){
                //等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>BBBBBBBBBBBB");
            //唤醒，唤醒指定的人，B
            num = 3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void printC(){

        lock.lock();

        try{
            //业务 -> 判断 -> 执行-> 通知
            while(num !=3){
                //等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName()+"=>CCCCCCCCCCCCC");
            //唤醒，唤醒指定的人，B
            num = 1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    //生产线：下单 -> 支付 -> 交易 -> 物流
}