package com.chauncy.cloud.thread.primary.ch3.security.singleton;

/**
 * @Author cheng
 * @create 2020-04-05 14:46
 *
 * 简单加锁实现的单例模式实现
 */
public class SimpleMultithreadedSingleton {

    // 保存该类的唯一实例
    private static SimpleMultithreadedSingleton instance = null;

    /*
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private SimpleMultithreadedSingleton() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例 <BR>
     * 即只有该方法被调用时该类的唯一实例才会被创建
     *
     * 意味着每次getInstance都需要申请锁--改善，双重锁
     *
     * @return
     */
    public static SimpleMultithreadedSingleton getInstance() {
        synchronized (SimpleMultithreadedSingleton.class) {
            if (null == instance) {
                instance = new SimpleMultithreadedSingleton();
            }
        }
        return instance;
    }

    public void someService() {
        // 省略其他代码
    }

}
