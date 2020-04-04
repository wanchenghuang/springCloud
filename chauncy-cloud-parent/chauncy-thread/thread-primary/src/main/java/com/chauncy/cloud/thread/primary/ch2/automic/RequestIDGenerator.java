package com.chauncy.cloud.thread.primary.ch2.automic;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author cheng
 * @create 2020-04-03 14:05
 */
public class RequestIDGenerator implements CircularSeqGenerator {

    private final static short SEQ_UPPER_LIMIT = 999;
    private short sequence = -1; //共享变量导致竞态.volatile、ThreadLocal

    //饿汉模式
    private RequestIDGenerator(){};

    private final static RequestIDGenerator instance = new RequestIDGenerator();

    public static RequestIDGenerator getInstance(){
        return instance;
    }

    /**
     * 生成循环递增序列号
     *
     * @return
     */
    @Override
    public synchronized short nextSequence() { //添加synchronized消除竞态
        if (sequence >= SEQ_UPPER_LIMIT) {
            sequence = 0;
        } else {
            sequence++;
        }
        return sequence;
    }

    /**
     * 生成一个新的Request ID
     *
     * @return
     */
    public String nextID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String timestamp = sdf.format(new Date());
        DecimalFormat df = new DecimalFormat("000");

        // 生成请求序列号
        short sequenceNo = nextSequence();

        return "0049" + timestamp + df.format(sequenceNo);
    }
}
