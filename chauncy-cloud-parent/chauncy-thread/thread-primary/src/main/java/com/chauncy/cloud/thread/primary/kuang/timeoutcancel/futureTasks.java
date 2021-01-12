package com.chauncy.cloud.thread.primary.kuang.timeoutcancel;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description TODO
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/8 22:18
 *
 * 使用Future+callable实现线程超时取消
 */
public class futureTasks {

    public static void main(String[] args) {
        //通过线程池提交任务
        ThreadPoolExecutor exec = new ThreadPoolExecutor(5, 10,10, TimeUnit.MINUTES, new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
        Future<String> future = null;
        try{
            future = exec.submit(new Task());
            String obj = future.get(1000 * 3, TimeUnit.MILLISECONDS); // 任务处理超时时间设为 10 秒
            System.out.println(obj+"aaa");
            System.out.println("任务成功返回:" + obj);
        }catch (TimeoutException e){
            System.out.println("处理超时啦....");
            System.out.println(future);
            if(future != null){
                future.cancel(true);
            }
        } catch (Exception e) {
            System.out.println("处理失败.");
        }finally{
            System.out.println("关闭线程池");
            // 关闭线程池
            exec.shutdown();
        }
        System.out.println("程序结束");
    }

}
class Task implements Callable<String> {

    @Override
    public String call() throws Exception {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        //模拟线程执行任务
        TimeUnit.SECONDS.sleep(18);
        System.out.println("程序早结束了，已经没用了。");
        return "线程执行完成";
    }
}
