package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-04 23:53
 *
 * 模拟多个线程
 */
public class CaseRunner3_1 {

    public static void main(String[] args) throws Exception {
        // 初始化请求派发器RequestDispatcher（即选择权重轮训负载均衡算法）
        SystemBooter.main(new String[] {});

        //本机Runtime.getRuntime().availableProcessors()为4,一下子启动4个线程
        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
            new RequestSender().start();
        }

    }

    static class RequestSender extends Thread {
        private static long id = -1;

        public RequestSender() {

        }

        //使用synchronized消除竞态，保证读到的是最后的结果，也就是会有4*100个请求
        static synchronized long nextId() {
            return ++id;
        }

        @Override
        public void run() {
            ServiceInvoker rd = ServiceInvoker.getInstance();

            //模拟一个线程处理100个请求
            for (int i = 0; i < 100; i++) {
                System.out.println(i);
                rd.dispatchRequest(new Request(nextId(), 1));
                Tools.randomPause(100);
            }

        }
    }
}
