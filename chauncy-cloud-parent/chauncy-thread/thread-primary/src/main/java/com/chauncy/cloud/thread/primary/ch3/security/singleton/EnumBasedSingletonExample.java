package com.chauncy.cloud.thread.primary.ch3.security.singleton;

import com.chauncy.cloud.thread.common.Debug;

/**
 * @Author cheng
 * @create 2020-04-05 14:59
 */
public class EnumBasedSingletonExample {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Debug.info(Singleton.class.getName());
                Singleton.INSTANCE.someService();
            };
        };
        t.start();
    }

    public static enum Singleton {
        INSTANCE;
        // 私有构造器
        Singleton() {
            Debug.info("Singleton inited.");
        }

        public void someService() {
            Debug.info("someService invoked.");
            // 省略其他代码
        }
    }

}
