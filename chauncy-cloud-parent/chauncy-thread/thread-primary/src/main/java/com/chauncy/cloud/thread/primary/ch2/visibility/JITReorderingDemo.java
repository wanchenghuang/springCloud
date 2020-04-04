package com.chauncy.cloud.thread.primary.ch2.visibility;

import com.chauncy.cloud.thread.common.stf.*;

/**
 * @Author cheng
 * @create 2020-04-04 09:48
 *
 * 再现JIT指令重排序的Demo
 */
@ConcurrencyTest(iterations = 200000)
public class JITReorderingDemo {
    private int externalData = 1;
    private Helper helper;

    @Actor
    public void createHelper() {
        helper = new Helper(externalData);

        //jvm 分解为几个子操作
        //1、objRef = allocate();分配Helper实例所需要的内存空间，并获得一个指向该空间的引用
        //2、invokeConstructor(objRef);调用Helper类的构造函数初始化ObjRef引用指向的Helper实例
        //3、helper = objRef;将实例变量helper指向刚分配的内存地址
    }

    @Observer({
            @Expect(desc = "Helper is null", expected = -1),
            @Expect(desc = "Helper is not null,but it is not initialized",
                    expected = 0),
            @Expect(desc = "Only 1 field of Helper instance was initialized",
                    expected = 1),
            @Expect(desc = "Only 2 fields of Helper instance were initialized",
                    expected = 2),
            @Expect(desc = "Only 3 fields of Helper instance were initialized",
                    expected = 3),
            @Expect(desc = "Helper instance was fully initialized", expected = 4) })
    public int consume() {
        int sum = 0;

        /*
         * 由于我们未对共享变量helper进行任何处理（比如采用volatile关键字修饰该变量），
         * 因此，这里可能存在可见性问题，即当前线程读取到的变量值可能为null。
         */
        final Helper observedHelper = helper;
        if (null == observedHelper) {
            sum = -1;
        } else {
            sum = observedHelper.payloadA + observedHelper.payloadB
                    + observedHelper.payloadC + observedHelper.payloadD;
        }

        return sum;
    }

    static class Helper {
        int payloadA;
        int payloadB;
        int payloadC;
        int payloadD;

        public Helper(int externalData) {
            this.payloadA = externalData;
            this.payloadB = externalData;
            this.payloadC = externalData;
            this.payloadD = externalData;
        }

        @Override
        public String toString() {
            return "Helper [" + payloadA + ", " + payloadB + ", " + payloadC + ", "
                    + payloadD + "]";
        }

    }

    public static void main(String[] args) throws InstantiationException,
            IllegalAccessException {
        // 调用测试工具运行测试代码
        TestRunner.runTest(JITReorderingDemo.class);
    }
}

