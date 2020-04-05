package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

import java.io.InputStream;

/**
 * @Author cheng
 * @create 2020-04-04 23:48
 */
public class Request {

    private final long transactionId;
    private final int transactionType;
    private InputStream in;

    public Request(long transactionId, int transactionType) {
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public InputStream getIn() {
        return in;
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

}
