package com.chauncy.cloud.thread.primary.ch3.security.static1;

import com.chauncy.cloud.thread.common.Debug;
import com.chauncy.cloud.thread.common.Tools;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author cheng
 * @create 2020-04-05 14:05
 *
 * static关键字可见性保障
 */
public class StaticVisibilityExample {

    private static Map<String, String> taskConfig;

    //static关键字在多线程环境下有其特殊的涵义， 它能够保证一个线程即使在未使用其他
    //同步机制的情况下也总是可以读取到一个类的静态变量的初始值（而不是默认值）。但是，
    //这种可见性保障仅限千线程初次读取该变扯。如果这个静态变量在相应类初始化完毕之后
    //被其他线程更新过． 那么一个线程要读取该变揽的 相对新值仍然需要借助锁、volatile关键字等同步机制。
    static {
        Debug.info("The class being initialized...");
        taskConfig = new HashMap<String, String>();// 语句①
        taskConfig.put("url", "https://github.com/Viscent");// 语句②
        taskConfig.put("timeout", "1000");// 语句③
    }

    public static void changeConfig(String url, int timeout) {
        taskConfig = new HashMap<String, String>();// 语句④
        taskConfig.put("url", url);// 语句⑤
        taskConfig.put("timeout", String.valueOf(timeout));// 语句⑥
    }

    public static void init() {
        // 该线程至少能够看到语句①～语句③的操作结果，而能否看到语句④～语句⑥的操作结果是没有保障的。
        Thread t = new Thread() {
            @Override
            public void run() {
                String url = taskConfig.get("url");
                String timeout = taskConfig.get("timeout");
                doTask(url, Integer.valueOf(timeout));
            }
        };
        t.start();
    }

    private static void doTask(String url, int timeout) {
        // 省略其他代码

        // 模拟实际操作的耗时
        Tools.randomPause(500);
    }

}
