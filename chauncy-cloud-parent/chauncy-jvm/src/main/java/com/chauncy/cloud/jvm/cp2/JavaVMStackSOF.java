package com.chauncy.cloud.jvm.cp2;

/**
 * @Author cheng
 * @create 2020-07-23 16:59
 *
 * VM Args：-Xss128k
 *
 * 1）如果线程请求的栈深度大于虚拟机所允许的最大深度，将抛出StackOverflowError异常。
 * 2）如果虚拟机的栈内存允许动态扩展，当扩展栈容量无法申请到足够的内存时，将抛出
 * OutOfMemoryError异
 *
 * 《Java虚拟机规范》明确允许Java虚拟机实现自行选择是否支持栈的动态扩展，而HotSpot虚拟机
 * 的选择是不支持扩展，所以除非在创建线程申请内存时就因无法获得足够内存而出现
 * OutOfMemoryError异常，否则在线程运行时是不会因为扩展而导致内存溢出的，只会因为栈容量无法
 * 容纳新的栈帧而导致StackOverflowError异常。
 *
 * ·使用-Xss参数减少栈内存容量。
 * 结果：抛出StackOverflowError异常，异常出现时输出的堆栈深度相应缩小。
 *
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
