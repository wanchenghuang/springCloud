package com.chauncy.cloud.jvm.cp2;

/**
 * @Author cheng
 * @create 2020-07-23 17:09
 * VM args: -Xss2M
 *
 * 创建线程导致内存溢出异常
 */
public class JavaVMStackOOM {
    private void dontStop() {
        while (true) {
        }
    }
    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }
    public static void main(String[] args) throws Throwable {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
