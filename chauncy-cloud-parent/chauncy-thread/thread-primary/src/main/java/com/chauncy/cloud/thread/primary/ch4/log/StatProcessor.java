package com.chauncy.cloud.thread.primary.ch4.log;

import java.util.Map;

/**
 * @Author cheng
 * @create 2020-04-05 20:17
 */
public interface StatProcessor {

    void process(String record);

    Map<Long, DelayItem> getResult();

}
