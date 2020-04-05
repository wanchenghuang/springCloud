package com.chauncy.cloud.thread.primary.ch3;

import com.chauncy.cloud.thread.common.stf.*;

/**
 * @Author cheng
 * @create 2020-04-04 19:42
 *
 * volatile对有序性的保障
 */
@ConcurrencyTest(iterations = 200000)
public class VolatileOrderingDemo {
    private int dataA = 0;
    private long dataB = 0L;
    private String dataC = null;
    private volatile boolean ready = false;

    @Actor
    public void writer() {
        dataA = 1;
        dataB = 10000L;
        dataC = "Content...";
        ready = true;
    }

    @Observer({
            @Expect(desc = "Normal", expected = 1),
            @Expect(desc = "Impossible", expected = 2),
            @Expect(desc = "ready not true", expected = 3)
    })
    public int reader() {
        int result = 0;
        boolean allISOK;
        if (ready) {
            allISOK = (1 == dataA) && (10000L == dataB) && "Content...".equals(dataC);
            result = allISOK ? 1 : 2;
        } else {
            result = 3;
        }
        return result;
    }

    public static void main(String[] args) throws InstantiationException,
            IllegalAccessException {

        // 调用测试工具运行测试代码
        TestRunner.runTest(VolatileOrderingDemo.class);
    }

    //结果不会出现2 说明reader方法执行的线程读取到的ready的值为true,并且该线程读取到的其他共享变量
    //的值一定是writer方法的执行线程更新之后的值

}