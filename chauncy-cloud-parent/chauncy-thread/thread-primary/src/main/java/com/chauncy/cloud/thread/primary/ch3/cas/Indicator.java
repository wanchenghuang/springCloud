package com.chauncy.cloud.thread.primary.ch3.cas;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author cheng
 * @create 2020-04-05 13:28
 *
 * 基于原子变量类的指标统计器
 *
 * 某分布式系统的性能测试桩(Test Stub)需要记录其在测试过程中接收到的请求总数
 * (Request Count)、 处理成功数(Success Count)和处理失败数(Failure Count)这3个指
 * 标气以便于测试后收集相关数据进行相互验证
 *
 * singleton
 */
public class Indicator {

    private Indicator(){};

    private static final Indicator INSTANCE = new Indicator();

    public static Indicator getInstance(){
        return INSTANCE;
    }

    /**
     * 记录请求总数
     */
    private final AtomicLong requestCount = new AtomicLong(0);

    /**
     * 记录处理成功总数
     */
    private final AtomicLong successCount = new AtomicLong(0);

    /**
     * 记录处理失败总数
     */
    private final AtomicLong failureCount = new AtomicLong(0);

    public void newRequestReceived() {
        // 使总请求数增加1。 这里无需加锁。
        requestCount.incrementAndGet();
    }

    public void newRequestProcessed() {
        // 使总请求数增加1。 这里无需加锁。
        successCount.incrementAndGet();
    }

    public void requestProcessedFailed() {
        // 使总请求数增加1。 这里无需加锁。
        failureCount.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public long getSuccessCount() {
        return successCount.get();
    }

    public long getFailureCountCount() {
        return failureCount.get();
    }

    public void reset() {
        requestCount.set(0);
        successCount.set(0);
        failureCount.set(0);
    }

    @Override
    public String toString() {
        return "Counter [requestCount=" + requestCount + ", successCount="
                + successCount + ", failureCount=" + failureCount + "]";
    }

}
