package com.chauncy.cloud.thread.primary.ch2.visibility;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-03 23:45
 */
public class ThreadStartVisibility {

    // 线程间的共享变量
    static int data = 0;

    public static void main(String[] args) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                // 使当前线程休眠R毫秒（R的值为随机数）
                Tools.randomPause(50);

                // 读取并打印变量data的值
                System.out.println(data);
            }
        };

        // 在子线程thread启动前更新变量data的值
        data = 1;// 语句①
        thread.start();

        // 使当前线程休眠R毫秒（R的值为随机数）
        Tools.randomPause(50);

        // 在子线程thread启动后更新变量data的值
        data = 2;// 语句②

        //将语句②去掉，则输出的结果始终是1，因为父线程在启动子线程之前对共享变量的更新对于
        //子线程是可见的

    }

}
// 结果可能是1也可能是2，因为父线程在子线程启动后对共享变量的更新对子线程的可见性是没有保证的；
