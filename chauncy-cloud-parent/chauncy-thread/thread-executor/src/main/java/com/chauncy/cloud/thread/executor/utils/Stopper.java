package com.chauncy.cloud.thread.executor.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author cheng
 * @create 2020-07-11 23:01
 */
public class Stopper {

    private static volatile AtomicBoolean signal = new AtomicBoolean(false);

    public static final boolean isStoped(){
        return signal.get();
    }

    public static final boolean isRunning(){
        return !signal.get();
    }

    public static final void stop(){
        signal.getAndSet(true);
    }
}
