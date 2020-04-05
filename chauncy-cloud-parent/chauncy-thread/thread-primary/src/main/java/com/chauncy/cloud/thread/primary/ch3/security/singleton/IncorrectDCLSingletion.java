package com.chauncy.cloud.thread.primary.ch3.security.singleton;

/**
 * @Author cheng
 * @create 2020-04-05 14:49
 *
 * 基于双重检查锁定的错误单例模式
 */
public class IncorrectDCLSingletion {

    // 保存该类的唯一实例
    private static IncorrectDCLSingletion instance = null;

    /*
     * 私有构造器使其他类无法直接通过new创建该类的实例
     */
    private IncorrectDCLSingletion() {
        // 什么也不做
    }

    /**
     * 创建并返回该类的唯一实例 <BR>
     * 即只有该方法被调用时该类的唯一实例才会被创建
     *
     * @return
     *
     * 这里的instance = new IncorrectDCLSingletion();会出现重排序，这个重排序会对没有加锁的
     * 操作①有影响
     */
    public static IncorrectDCLSingletion getInstance() {
        if (null == instance) {// 操作①：第1次检查
            synchronized (IncorrectDCLSingletion.class) {
                if (null == instance) {// 操作②：第2次检查
                    instance = new IncorrectDCLSingletion();// 操作③
                }
            }
        }
        return instance;
    }

    public void someService() {
        // 省略其他代码
    }

}
