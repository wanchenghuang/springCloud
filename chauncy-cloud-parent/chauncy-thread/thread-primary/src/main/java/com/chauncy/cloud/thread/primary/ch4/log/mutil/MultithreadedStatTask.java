package com.chauncy.cloud.thread.primary.ch4.log.mutil;

import com.chauncy.cloud.thread.primary.ch4.log.AbstractStatTask;
import com.chauncy.cloud.thread.primary.ch4.log.StatProcessor;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author cheng
 * @create 2020-04-05 20:30
 *
 * 基于任务分割方式实现的统计程序
 */
public class MultithreadedStatTask extends AbstractStatTask {
    // 日志文件输入缓冲大小
    protected final int inputBufferSize;
    // 日志记录集大小
    protected final int batchSize;
    // 日志文件输入流
    protected final InputStream in;

    /* 实例初始化块 */
    {
        String strBufferSize = System.getProperty("x.input.buffer");
        inputBufferSize = null != strBufferSize ? Integer.valueOf(strBufferSize)
                : 8192;
        String strBatchSize = System.getProperty("x.batch.size");
        batchSize = null != strBatchSize ? Integer.valueOf(strBatchSize) : 2000;
    }

    public MultithreadedStatTask(int sampleInterval,
                                 StatProcessor recordProcessor) {
        super(sampleInterval, recordProcessor);
        this.in = null;
    }

    public MultithreadedStatTask(InputStream in, int sampleInterval,
                                 int traceIdDiff,
                                 String expectedOperationName, String expectedExternalDeviceList) {
        super(sampleInterval, traceIdDiff, expectedOperationName,
                expectedExternalDeviceList);
        this.in = in;
    }

    @Override
    protected void doCalculate() throws IOException, InterruptedException {
        final AbstractLogReader logReaderThread = createLogReader();
        // 启动工作者线程
        logReaderThread.start();
        RecordSet recordSet;
        String record;
        for (;;) {
            recordSet = logReaderThread.nextBatch();
            if (null == recordSet) {
                break;
            }
            while (null != (record = recordSet.nextRecord())) {
                // 实例变量recordProcessor是在AbstractStatTask中定义的
                recordProcessor.process(record);
            }
        }// for循环结束
    }

    protected AbstractLogReader createLogReader() {
        AbstractLogReader logReader = new LogReaderThread(in, inputBufferSize,
                batchSize);
        return logReader;
    }
}
