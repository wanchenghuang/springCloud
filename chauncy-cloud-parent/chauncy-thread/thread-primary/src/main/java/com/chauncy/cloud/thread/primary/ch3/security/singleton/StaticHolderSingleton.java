package com.chauncy.cloud.thread.primary.ch3.security.singleton;

import com.chauncy.cloud.thread.common.Debug;

/**
 * @Author cheng
 * @create 2020-04-05 14:57
 *
 * 基于静态内部类的单例模式
 */
public class StaticHolderSingleton {

    // 私有构造器
    private StaticHolderSingleton() {
        Debug.info("StaticHolderSingleton inited.");
    }

    static class InstanceHolder {
        // 保存外部类的唯一实例
        static {
            Debug.info("InstanceHolder inited.");
        }
        final static StaticHolderSingleton INSTANCE = new StaticHolderSingleton();
    }

    public static StaticHolderSingleton getInstance() {
        Debug.info("getInstance invoked.");
        return InstanceHolder.INSTANCE;
    }

    public void someService() {
        Debug.info("someService invoked.");
        // 省略其他代码
    }

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Debug.info(StaticHolderSingleton.InstanceHolder.class.getName());
                StaticHolderSingleton.InstanceHolder.INSTANCE.someService();
            };
        };
        t.start();
    }
}
