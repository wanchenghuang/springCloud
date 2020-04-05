package com.chauncy.cloud.thread.primary.ch4.log.mutil;

import java.io.InputStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author cheng
 * @create 2020-04-05 20:33
 *
 * 日志读取线程实现类
 */
public class LogReaderThread extends AbstractLogReader {
    // 线程安全的队列
    final BlockingQueue<RecordSet> channel = new ArrayBlockingQueue<RecordSet>(2);

    public LogReaderThread(InputStream in, int inputBufferSize, int batchSize) {
        super(in, inputBufferSize, batchSize);
    }

    @Override
    public RecordSet nextBatch()
            throws InterruptedException {
        RecordSet batch;
        // 从队列中取出一个记录集
        batch = channel.take();
        if (batch.isEmpty()) {
            batch = null;
        }
        return batch;
    }

    @Override
    protected void publish(RecordSet recordBatch) throws InterruptedException {
        // 记录集存入队列
        channel.put(recordBatch);
    }
}