package com.chauncy.cloud.thread.primary.kuang.unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/4 22:25
 */
//ConcurrentModificationException并发修改异常
public class ListTest {

    public static void main(String[] args) {

        //并发下List是不安全的
        /**
         * 解决方案
         *  1、 List<String> list = new Vector<>()
         *  2、Collections.synchronizedList(Lists.newArrayList());
         */
        // CopyOnWrite 写入时复制  COW  计算机程序设计领域的一种优化策略；
        // 多个线程调用的时候，list，读取的时候，固定的，写入（覆盖）
        // 在写入的时候避免覆盖，造成数据问题！
        // 读写分离
        // CopyOnWriteArrayList  比 Vector Nb 在哪里？
//        List<String> list =Lists.newCopyOnWriteArrayList();
        List<String> list = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
