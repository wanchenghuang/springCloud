package com.chauncy.cloud.jvm.cp2.jdk6;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author cheng
 * @create 2020-07-24 10:25
 *
 * 方法区内存溢出
 *
 * String::intern()是一个本地方法，它的作用是如果字符串常量池中已经包含一个等于此String对象的
 * 字符串，则返回代表池中这个字符串的String对象的引用；否则，会将此String对象包含的字符串添加
 * 到常量池中，并且返回此String对象的引用。在JDK 6或更早之前的HotSpot虚拟机中，常量池都是分配
 * 在永久代中，我们可以通过-XX：PermSize和-XX：MaxPermSize限制永久代的大小，即可间接限制其
 * 中常量池的容量
 *
 * VM Args：-XX:PermSize=6M -XX:MaxPermSize=6M
 *
 * jdk6运行时常量池溢出时，在OutOfMemoryError异常后面跟随的提示信息
 * 是“PermGen space”，说明运行时常量池的确是属于方法区（即JDK 6的HotSpot虚拟机中的永久代）的
 * 一部分
 *
 * jdk8  -Xms6m
 * JDK 8及以上版本使用-XX：MaxMeta-spaceSize参数把方法区容量同
 * 样限制在6MB，也都不会重现JDK 6中的溢出异常，循环将一直进行下去，永不停歇[1]。出现这种变
 * 化，是因为自JDK 7起，原本存放在永久代的字符串常量池被移至Java堆之中，所以在JDK 7及以上版
 * 本，限制方法区的容量对该测试用例来说是毫无意义的。这时候使用-Xmx参数限制最大堆到6MB就能
 * 够看到以下两种运行结果之一，具体取决于哪里的对象分配时产生了溢出
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
// 使用Set保持着常量池引用，避免Full GC回收常量池行为
        Set<String> set = new HashSet<String>();
// 在short范围内足以让6MB的PermSize产生OOM了
        short i = 0;
        /*while (true) {
            set.add(String.valueOf(i++).intern());
        }*/
    }

    public void intern(){
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1); //true
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2); //false
    }
}
