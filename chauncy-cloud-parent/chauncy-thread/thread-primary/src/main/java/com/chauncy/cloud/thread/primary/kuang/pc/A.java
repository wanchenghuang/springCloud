package com.chauncy.cloud.thread.primary.kuang.pc;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 20:57
 * 线程之间的通信问题：生产者和消费者问题！  等待唤醒，通知唤醒
 * 线程交替执行  A   B 操作同一个变量   num = 0
 * A num+1
 * B num-1
 *
 */
public class A {

    public static void main(String[] args) {
        Data data = new Data();

        new Thread(()->{
            for (int i = 1; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 1; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 1; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 1; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

//判断等待，业务，通知
class Data{

    private int num = 0;

    //+1
    public synchronized void increment() throws InterruptedException {

        while (num != 0){ // 0
            //等待
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName()+"=>"+num);
        //通知其它线程+1完毕了
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException {
        while (num == 0){ // 1
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName()+"=>"+num);
        //通知其它线程-1完毕了
        this.notifyAll();
    }
}
