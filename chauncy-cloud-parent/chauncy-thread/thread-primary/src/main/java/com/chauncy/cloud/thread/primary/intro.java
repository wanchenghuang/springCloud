package com.chauncy.cloud.thread.primary;

/**
 * @Author cheng
 * @create 2020-04-02 21:46
 *
 * 1、线程属千 ”一次性用品 ”，我们不能通过重新调用一个已经运行结束的线程的
 * start 方法来使其重新运行。事实上， start 方法也只能够被调用一次，多次调用同一个
 * Thread 实 例的 start 方法会导致其抛出 IllegalThreadStateException 异常。
 *
 * 2、ava 虚拟机会为每个线程分配调用栈(Call Stack) 所需的内存空间。调用栈用于跟踪 Java 代码（方法）间的调用关系以及 Java
 * 代码对本地代码 (Native Code, 通常是 C 代码）的调用。 另外， Java 平台中的每个线程可能还有一个内核线程（具体与 Java 虚拟机的实现有关）
 * 与之对应。因此相对来说，创建线程对象比创建其他类型的对象的成本要高一些。
 *
 * 3、Java 平台中的任意一段代码（比如一个方法）总是由确定的线程负责执行的，这个线 程就相应地被称为这段代码的执行线程
 */
public class intro {
}
