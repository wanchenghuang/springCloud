package com.chauncy.cloud.thread.primary.ch4.log;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author cheng
 * @create 2020-04-05 20:18
 */
public class DelayItem {

    private long timeStamp;
    private AtomicInteger sampleCount = new AtomicInteger(0);
    private AtomicLong totalDelay = new AtomicLong(0);

    public DelayItem(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public AtomicInteger getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(AtomicInteger sampleCount) {
        this.sampleCount = sampleCount;
    }

    public AtomicLong getTotalDelay() {
        return totalDelay;
    }

    public void setTotalDelay(AtomicLong totalDelay) {
        this.totalDelay = totalDelay;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
