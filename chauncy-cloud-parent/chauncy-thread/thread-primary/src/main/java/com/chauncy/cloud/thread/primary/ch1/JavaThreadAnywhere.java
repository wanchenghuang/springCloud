package com.chauncy.cloud.thread.primary.ch1;

/**
 * @Author cheng
 * @create 2020-04-03 12:50
 */
public class JavaThreadAnywhere {

    public static void main(String[] args) {
        // 获取当前线程
        Thread currentThread = Thread.currentThread();

        // 获取当前线程的线程名称
        String currentThreadName = currentThread.getName();

        System.out.printf("The main method was executed by thread:%s.%n",
                currentThreadName);
        Helper helper = new Helper("Java Thread AnyWhere");
        helper.run();

        Thread thread = new Thread(new Helper("aaaa"));
        System.out.printf("thread name : %s.%n",thread.getName());
        thread.start();
    }

    static class Helper implements Runnable {
        private final String message;

        public Helper(String message) {
            this.message = message;
        }

        private void doSomething(String message) {
            // 获取当前线程
            Thread currentThread = Thread.currentThread();

            // 获取当前线程的线程名称
            String currentThreadName = currentThread.getName();

            System.out.printf("The doSomething method was executed by thread:%s.%n",
                    currentThreadName);
            System.out.println("Do something with " + message);
        }

        @Override
        public void run() {
            doSomething(message);
        }
    }
}
