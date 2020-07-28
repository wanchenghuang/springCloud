package com.chauncy.cloud.jvm.cp2;

import java.util.ArrayList;
import java.util.List;

/**
 * java堆溢出
 *
 * @Author cheng
 * @create 2020-07-22 08:40
 * VM Args：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * -XX:HeapDumpPath=/Users/cheng/Desktop/
 *
 * HeapDumpOnOutOfMemoryError：让虚拟机出现内存溢出的时候dump出当前
 * 内存堆h转储快照以便分析
 *
 * -XX:+PrintGCDetails： 告诉虚拟机在发生垃圾收集行
 * 为时打印内存回收日志，并且在进程退出的时候输出当前的内存各区域分配情
 */

/**
 * 启动方式
 *
 * cd /Users/cheng/Downloads/git/github/cloud/springCloud/chauncy-cloud-parent/chauncy-jvm/target/classes
 * java  -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError com.chauncy.cloud.jvm.cp2.HeapOOM
 *
 * 一个类的全名应该是包名+类名。类HeapOOM的全名com.chauncy.cloud.jvm.cp2.HeapOOM
 *
 */
public class HeapOOM {

    static class OOMObject {
    }
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            list.add(new OOMObject());
        }
    }
}