package com.chauncy.cloud.thread.primary.ch3.security;

import com.chauncy.cloud.thread.common.Debug;

/**
 * @Author cheng
 * @create 2020-04-05 13:57
 *
 * 类的延迟初始化
 *
 * 访问语句1Collaborator本身仅仅是该类被java虚拟机加载，该类所有的静态变量的值都是默认值，
 * 直到有个线程初次访问了该类的任意一个静态变量才使这个类被初始化--类的静态初始块static{}被执行，
 * 类的所有静态变量被赋予初始值
 */
public class ClassLazyInitDemo {

    public static void main(String[] args) {
        Debug.info(Collaborator.class.hashCode());// 语句①
        Debug.info(Collaborator.number);// 语句②
        Debug.info(Collaborator.flag);

    }

    static class Collaborator {
        static int number = 1;
        static boolean flag = true;
        static {
            Debug.info("Collaborator initializing...");
        }
    }
}
