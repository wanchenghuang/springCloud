package com.chauncy.cloud.thread.primary.ch1;

/**
 * @Author cheng
 * @create 2020-04-03 12:44
 */
public class SimpleTimer {

    private static int count;

    public static void main(String[] args) {

        args = new String[]{"120", "240"};
        count = args.length >= 1 ? Integer.valueOf(args[0]) : 60;
        int remaining;
        while (true) {
            remaining = countDown();
            if (0 == remaining) {
                break;
            } else {
                System.out.printf("name:%s.%n",Thread.currentThread().getName());
                System.out.println("Remaining " + count + " second(s)");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // 什么也不做;
            }
        }
        System.out.println("Done.");

    }

    private static int countDown() {
        return count--;
    }
}
