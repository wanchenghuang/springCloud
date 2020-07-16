package com.chauncy.cloud.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;

/**
 * @Author cheng
 * @create 2020-07-11 16:43
 *
 * Daemon: 守护线程，主线程停止了，守护线程也停止了.如GC
 * 非守护线程(用户线程): 是指用户自定义创建的线程，主线程停止，用户线程不会停止（另一条执行路径）
 *
 * 守护线程和用户线程的没啥本质的区别：唯一的不同之处就在于虚拟机的离开：如果用户线程已经全部退出运行了，只剩下守护线程存在了，
 * 虚拟机也就退出了。 因为没有了被守护者，守护线程也就没有工作可做了，也就没有继续运行程序的必要了。
 *
 * 1、 如果workerCount < corePoolSize，则创建并启动一个线程来执行新提交的任务；
 * 2、 如果workerCount >= corePoolSize，且线程池内的阻塞队列未满，则将任务添加到该阻塞队列中；
 * 3、 如果workerCount >= corePoolSize && workerCount < maximumPoolSize，且线程池内的阻塞队列已满，则创建并启动一个线程来执行新提交的任务；
 * 4、 如果workerCount >= maximumPoolSize，并且线程池内的阻塞队列已满, 则根据拒绝策略来处理该任务, 默认的处理方式是直接抛异常。
 */
public class ThreadUtils {

    //获取一个ThreadMXBean
    private static final ThreadMXBean threadBean =  ManagementFactory.getThreadMXBean();

    //栈的深度
    private static final int STACK_DEPTH = 20;

    /**
     *
     *
     * 使用线程工厂类创建一个线程工厂并作为工具类Executors创建一个缓存线程池的参数的线程池
     * 线程名称的格式为prefix- ID，其中ID是唯一的
     *
     * 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     *
     * 	一个核心线程池大小为0，最大线程池大小不受限，工作者线程允许的最大空闲时间60s,内部以同步队列为工作队列的一个线程池
     * 	该线程池中所有工作者线程在空闲了指定的时间后都可以被自动清理，由于该线程池的核心线程池大小0，故提交给线程池的第一个任务导致
     * 	该线程池中的第一个工作者线程被创建并启动，后续向该线程池提交任务的时候，由于当前线程池大小已经超过核心线程池大小，因此ThreadPoolExecutor此时会将任务缓存到工作队列中(调用workerQueue.offer方法)；
     *
     *  SynchronousQueue内部 并不维护用于存储 队列元素的实际存储空间。一 个线
     * 	程（生产者线程） 在执行 SynchronousQueue.offer(E)的时候， 如果没有其他线程
     * 	（消费者线程） 因执行SynchronousQueue.take(）而被暂停（即有可用的线程），那么SynchronousQueue.offer(E)调用会直接返回false,即入队列失败。
     * 	因此，在该线程池中的所有工作者线程 都在执行任务，即无空闲工作者线程的情况下给其提交任务会导致该任务无
     * 法被缓存成功。 而 ThreadPoolExecutor在任务缓存失败且线程池当前大小未达到最大线程池大小（这里的最大线程池大小实际上相当于不限）
     * 的情况下会创建并启动新的工作者线程
     *
     * 	在极端的情况下，给该线程池每提交1个任务都 会导致
     * 个新的工作者线程被创建并启动，而这最终会导致系统中的线程过多，从而导致过多的上下文切换而使得整个系统被拖慢。
     * 因此 ，Executors.newCachedThreadPool( ) 所返回的线程池适合于用来执行大量耗时较短且提交频率较高的任务。
     *
     *
     *
     **/
    public static ThreadPoolExecutor newDaemonCachedThreadPool(String prefix){

        ThreadFactory threadFactory = namedDaemonThreadFactory(prefix);

        //java的向下转型：父类转为子类
        return (ThreadPoolExecutor) Executors.newCachedThreadPool(threadFactory);
    }

    /**
     *
     * 以一个前缀名并设置守护线程创建一个线程工厂
     *
     **/
    private static ThreadFactory namedDaemonThreadFactory(String prefix){

        return new ThreadFactoryBuilder().setDaemon(true).setNameFormat(prefix + "-%d").build();
    }

    /**
     *
     * 创建一个初始核心线程数并设置存活时间，并当线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
     * 的线程池
     *
     **/
    public static ThreadPoolExecutor newDaemonCachedThreadPool(String prefix,
                                                               int maxThreadNumber,
                                                               int keepAliveSeconds){

        ThreadFactory threadFactory = namedDaemonThreadFactory(prefix);
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                //核心线程数：未进入等待队列之前的最大线程数量
                maxThreadNumber,
                //最大线程数：缓存线程池用的是LinkedBlockingQueue无界的队列，这里最大线程数无效
                maxThreadNumber,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(),
                threadFactory
        );
        //线程池中corePoolSize线程空闲时间达到keepAliveTime也将关闭
        threadPool.allowCoreThreadTimeOut(true);

        return threadPool;
    }

    /**
     *
     * 创建一个固定数量的线程池
     *
     * 一个以 无界队列为工作队列， 核心线程池大小与最大线程池大小均为nThreads且线程池中的空闲工作者线程不会被自动清理的线程池，
     * 这是一种线程池大小一旦达到其核心线程池大小就既不会增加也不会减少工作者线程的固定大小的线程池。
     * 因此 ， 这样的线程池实例 一旦不再需要，必须主动将其关闭
     *
     * 可控制线程最大并发数，超出的线程会在队列中等待
     *
     * 缺点：
     * 	主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM
     *
     * 优点：
     * newFixedThreadPool的线程数是可以进行控制的，因此我们可以通过控制最大线程来使我们的服务器打到最大的使用率，
     * 同时又可以保证及时流量突然增大也不会占用服务器过多的资源。
     *
     **/
    public static ThreadPoolExecutor newDaemonFixedThreadPool(int nThreads,String prefix){

        ThreadFactory threadFactory = namedDaemonThreadFactory(prefix);

        return (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads,threadFactory);
    }

    public static ExecutorService newDaemonFixedThreadPool(String threadName,int threadsNum){

        // ThreadFactoryBuilder用到建造者模式实现构造函数的多态，保障原子性
        // 可以一直set
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat(threadName)
                .build();
        return Executors.newFixedThreadPool(threadsNum,threadFactory);
    }

    /**
     *
     * 创建单个线程的线程池
     **/
    public static ExecutorService newDaemonSingleThreadExecutor(String threadName){
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat(threadName)
                .build();
        return Executors.newSingleThreadExecutor(threadFactory);
    }

    /**
     *
     * 固定线程数，适用于需要限制后台线程数的场景
     * 使用DelayQueue延迟队列作为工作队列，调度任务被封装成ScheduledFutureTask，工作队列为有限队列，按照执行时间和序列号排序；
     *
     * 获取任务：先Lock上锁，获取第一个元素，若为空则Condition.await，否则则获取元素并signalAll所有等待线程；
     *
     * 添加任务：先Lock上锁，再添加上元素，如果添加的是头元素则signalAll；
     *
     **/
    public static ScheduledExecutorService newDaemonScheduledExecutorService(String threadName, int corePoolSize){

        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setDaemon(true)
                .setNameFormat(threadName)
                .build();
        //默认最大线程数为int的最大值，存活时间为0，阻塞队列为DelayedWorkQueue
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
        //设定是否将取消后的任务从队列中清除
        executor.setRemoveOnCancelPolicy(true);

        return executor;
    }

    public static ThreadInfo getThreadInfo(Thread t){
        long tid = t.getId();
        return threadBean.getThreadInfo(tid,STACK_DEPTH);
    }

    public static String formatThreadInfo(ThreadInfo threadInfo, String indent) {
        StringBuilder sb = new StringBuilder();
        appendThreadInfo(sb, threadInfo, indent);
        return sb.toString();
    }

    public static void appendThreadInfo(StringBuilder sb,
                                        ThreadInfo info,
                                        String indent) {
        boolean contention = threadBean.isThreadContentionMonitoringEnabled();

        if (info == null) {
            sb.append(indent).append("Inactive (perhaps exited while monitoring was done)\n");
            return;
        }
        String taskName = getTaskName(info.getThreadId(), info.getThreadName());
        sb.append(indent).append("Thread ").append(taskName).append(":\n");

        Thread.State state = info.getThreadState();
        sb.append(indent).append("  State: ").append(state).append("\n");
        sb.append(indent).append("  Blocked count: ").append(info.getBlockedCount()).append("\n");
        sb.append(indent).append("  Waited count: ").append(info.getWaitedCount()).append("\n");
        if (contention) {
            sb.append(indent).append("  Blocked time: " + info.getBlockedTime()).append("\n");
            sb.append(indent).append("  Waited time: " + info.getWaitedTime()).append("\n");
        }
        if (state == Thread.State.WAITING) {
            sb.append(indent).append("  Waiting on ").append(info.getLockName()).append("\n");
        } else  if (state == Thread.State.BLOCKED) {
            sb.append(indent).append("  Blocked on ").append(info.getLockName()).append("\n");
            sb.append(indent).append("  Blocked by ").append(
                    getTaskName(info.getLockOwnerId(), info.getLockOwnerName())).append("\n");
        }
        sb.append(indent).append("  Stack:").append("\n");
        for (StackTraceElement frame: info.getStackTrace()) {
            sb.append(indent).append("    ").append(frame.toString()).append("\n");
        }
    }

    private static String getTaskName(long id, String name) {
        if (name == null) {
            return Long.toString(id);
        }
        return id + " (" + name + ")";
    }
}