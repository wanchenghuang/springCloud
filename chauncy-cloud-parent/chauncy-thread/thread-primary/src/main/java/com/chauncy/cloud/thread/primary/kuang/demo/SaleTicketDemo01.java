package com.chauncy.cloud.thread.primary.kuang.demo;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 17:42
 * 真正的多线程开发，公司中的开发，降低耦合性
 * 线程就是一个单独的资源类，没有任何附属的操作！
 * 1、 属性、方法
 */
public class SaleTicketDemo01 {

    public static void main(String[] args) {

        // 并发：多线程操作同一个资源类, 把资源类丢入线程
        Tickets ticket = new Tickets();

        new Thread(() -> {
            for (int i = 1; i<40; i++){
                ticket.sale();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        },"C").start();
    }
}



class Tickets {
    //属性、方法
    private int number = 50;

    // 卖票的方式
    // synchronized 本质: 队列，锁
    public synchronized void sale() {
        if (number > 0 ) {
            System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票,剩余：" + number);
        }
    }
}
