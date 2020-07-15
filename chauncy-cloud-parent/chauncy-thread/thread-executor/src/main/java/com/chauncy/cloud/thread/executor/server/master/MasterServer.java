package com.chauncy.cloud.thread.executor.server.master;

import com.chauncy.cloud.thread.executor.IStoppable;
import com.chauncy.cloud.thread.executor.constant.Constants;
import com.chauncy.cloud.thread.executor.utils.Stopper;
import com.chauncy.cloud.thread.executor.utils.ThreadUtils;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @Author cheng
 * @create 2020-07-11 23:12
 *
 * 1、创建线程工厂
 * 2、使用executors框架创建线程池
 * 3、设置线程池属性
 *
 */
//@ComponentScan("com.chauncy.cloud")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
public class MasterServer implements IStoppable {

    private ScheduledExecutorService heartbeatMasterService;

    private ExecutorService masterSchedulerService;

    public static void main(String[] args){
        System.setProperty("spring.profiles.active","worker");
        Thread.currentThread().setName(Constants.THREAD_NAME_MASTER_SERVER);
        new SpringApplicationBuilder(MasterServer.class).web(WebApplicationType.NONE).run(args);
    }

    /**
     * 1、创建线程池实例
     * 2、创建一个线程实例
     * 3、将线程实例提交给线程池实例执行（1、submit 2、execute 3、future）
     */
    @PostConstruct
    public void run(){
        //创建一个线程池实例
        masterSchedulerService = ThreadUtils.newDaemonSingleThreadExecutor("Master-Scheduler-Thread");
        heartbeatMasterService = ThreadUtils.newDaemonScheduledExecutorService("Master-Main-Thread",Constants.defaulMasterHeartbeatThreadNum);

        TaskExecThread taskExecThread = new TaskExecThread();
        Future<Boolean> future = masterSchedulerService.submit(taskExecThread);
        System.out.println(future);

        Callable<Boolean> callable = callableThread();
        Future<Boolean> futures = masterSchedulerService.submit(callable);
        System.out.println(futures);

         //heartbeat thread implement
        Runnable heartBeatThread = heartBeatThread();
        masterSchedulerService.execute(heartBeatThread);

        heartbeatMasterService.scheduleAtFixedRate(heartBeatThread,5,10, TimeUnit.SECONDS);

        SchedulerThread schedulerThread = new SchedulerThread();
        masterSchedulerService.execute(schedulerThread);
        Future future2 = masterSchedulerService.submit(schedulerThread);
    }

    private Runnable heartBeatThread() {

        Runnable heartBeatThread = new Runnable() {
            @Override
            public void run() {
                if (Stopper.isRunning()) {
                    System.out.println("心跳检测");
                }
                System.out.println("心跳检测222");

            }
        };
        return heartBeatThread;
    }

    private Callable<Boolean> callableThread() {
        Callable<Boolean> callable = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return true;
            }
        };
        return callable;
    }

    static class BaseTaskThread implements Callable<Boolean>{


        @Override
        public Boolean call() throws Exception {
            return submit();
        }

        protected Boolean submit(){
            return true;
        }
    }

    static class TaskExecThread extends BaseTaskThread{

        /**
         * 重写submit方法
         *
         * @return
         */
        @Override
        public Boolean submit(){
            Boolean result = false;
            if(1==1){
                result = true;
            }

            return result;
        }

    }

    static class SchedulerThread implements Runnable{

        @Override
        public void run() {
            while (Stopper.isRunning()){
                System.out.println("模仿每秒执行");
            }

            System.out.println("master server stopped...");
        }
    }

    /**
     *
     * 优雅停止
     *
     **/
    @Override
    public synchronized void stop(String cause) {

    }

}
