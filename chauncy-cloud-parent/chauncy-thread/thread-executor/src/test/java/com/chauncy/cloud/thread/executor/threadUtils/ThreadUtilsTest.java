package com.chauncy.cloud.thread.executor.threadUtils;

import com.chauncy.cloud.thread.executor.utils.ThreadUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @Author cheng
 * @create 2020-07-11 17:26
 */
public class ThreadUtilsTest {

    private static final Logger logger = LoggerFactory.getLogger(ThreadUtilsTest.class);

    @Test
    public void testNewDaemonCachedThreadPool() {

        ThreadPoolExecutor threadPoolExecutor = ThreadUtils.newDaemonCachedThreadPool("threadTest-");
        Thread thread1 = threadPoolExecutor.getThreadFactory().newThread(() -> {
            for (int i = 0; i < 10; ++i) {
                System.out.println("this task is with index " + i );
            }
        });
        assertTrue(thread1.getName().startsWith("threadTest-"));
        assertFalse(threadPoolExecutor.isShutdown());
        threadPoolExecutor.shutdown();
        assertTrue(threadPoolExecutor.isShutdown());
    }
}
