package com.chauncy.cloud.thread.primary.ch1;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author cheng
 * @create 2020-04-02 22:30
 */
public class SimpleJavaApp {

    public static void main(String[] args) throws InterruptedException {

        while (true){
            System.out.println(LocalDateTime.now());
            Thread.sleep(1000);
        }
    }
}
