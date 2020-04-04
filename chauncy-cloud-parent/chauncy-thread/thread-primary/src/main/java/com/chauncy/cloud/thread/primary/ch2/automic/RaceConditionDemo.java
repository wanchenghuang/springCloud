package com.chauncy.cloud.thread.primary.ch2.automic;

import com.chauncy.cloud.thread.common.Tools;

/**
 * @Author cheng
 * @create 2020-04-03 14:28
 */
public class RaceConditionDemo {

    public static void main(String[] args) throws Exception{
        args = new String[]{"4"};
        // 客户端线程数
        int numberOfThreads = args.length > 0 ? Short.valueOf(args[0]) : Runtime
                .getRuntime().availableProcessors();

        Thread[] workerThreads = new Thread[numberOfThreads];
        //创建对应的线程数量
        for (int i = 0; i < numberOfThreads; i++) {
            workerThreads[i] = new WorkerThread(i, 10);
        }

        // 待所有线程创建完毕后，再一次性将其启动，以便这些线程能够尽可能地在同一时间内运行
        for (Thread ct : workerThreads) {
            ct.start();
        }

    }

    //模拟业务线程
    static class WorkerThread extends Thread {

        private final int requestCount;

        public WorkerThread(int id, int requestCount) {
            super("worker-" + id);
            this.requestCount = requestCount;
        }

        @Override
        public void run(){
            int i = requestCount;
            String requestID;
            RequestIDGenerator requestIDGen = RequestIDGenerator.getInstance();
            while (i-- > 0) {
                // 生成Request ID
                requestID = requestIDGen.nextID();
                processRequest(requestID);
            }
        }

        // 模拟请求处理
        private void processRequest(String requestID) {
            // 模拟请求处理耗时
            Tools.randomPause(50);
            System.out.printf("%s got requestID: %s %n",
                    Thread.currentThread().getName(), requestID);
        }
    }
}
