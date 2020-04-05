package com.chauncy.cloud.thread.primary.ch4.downfile;

import com.chauncy.cloud.thread.common.Debug;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @Author cheng
 * @create 2020-04-05 16:3
 * 缓冲区实现类
 *
 * 下载线程每次从网络下载的数据先被写入缓冲区，只有当缓冲区满的时候才写入本地文件
 */
public class DownloadBuffer implements Closeable {
    /**
     * 当前Buffer中缓冲的数据相对于整个存储文件的位置偏移
     */
    private long globalOffset;
    private long upperBound;
    private int offset = 0;
    public final ByteBuffer byteBuf;
    private final Storage storage;

    public DownloadBuffer(long globalOffset, long upperBound,
                          final Storage storage) {
        this.globalOffset = globalOffset;
        this.upperBound = upperBound;
        this.byteBuf = ByteBuffer.allocate(1024 * 1024);
        this.storage = storage;
    }

    public void write(ByteBuffer buf) throws IOException {
        int length = buf.position();
        final int capacity = byteBuf.capacity();
        // 当前缓冲区已满，或者剩余容量不够容纳新数据
        if (offset + length > capacity || length == capacity) {
            // 将缓冲区中的数据写入文件
            flush();
        }
        byteBuf.position(offset);
        buf.flip();
        byteBuf.put(buf);
        offset += length;
    }

    public void flush() throws IOException {
        int length;
        byteBuf.flip();
        length = storage.store(globalOffset, byteBuf);
        byteBuf.clear();
        globalOffset += length;
        offset = 0;
    }

    @Override
    public void close() throws IOException {
        Debug.info("globalOffset:%s,upperBound:%s", globalOffset, upperBound);
        if (globalOffset < upperBound) {
            flush();
        }
    }

}
