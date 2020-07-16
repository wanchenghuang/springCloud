package com.chauncy.cloud.thread.executor.server.master;

import com.chauncy.cloud.common.holder.SpringContextHolder;
import com.chauncy.cloud.data.ProcessDao;
import com.chauncy.cloud.thread.executor.IStoppable;
import com.chauncy.cloud.thread.executor.constant.Constants;
import com.chauncy.cloud.thread.executor.server.master.config.MasterConfig;
import com.chauncy.cloud.thread.executor.server.master.runner.MasterSchedulerThread;
import com.chauncy.cloud.thread.executor.utils.Stopper;
import com.chauncy.cloud.thread.executor.utils.ThreadUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class} )
//@EnableDiscoveryClient
@ComponentScan("com.chauncy.cloud")
@MapperScan("com.chauncy.cloud.*.mapper.*")
public class MasterServer implements IStoppable {

    @Autowired
    protected ProcessDao processDao;

    @Autowired
    protected MasterConfig masterConfig;

    /**
     * 先依赖注入，防止还没有加载初始化bean就用
     *
     * Spring应用程序上下文只会用它来初始化
     */
    @Autowired
    private SpringContextHolder springApplicationContext;

    private ScheduledExecutorService heartbeatMasterService;

    private ExecutorService masterSchedulerService;

    public static void main(String[] args){
        //System.setProperty("spring.profiles.active","worker");
        Thread.currentThread().setName(Constants.THREAD_NAME_MASTER_SERVER);
        new SpringApplicationBuilder(MasterServer.class).web(WebApplicationType.NONE).run(args);
    }

    /**
     * 1、创建线程池实例
     * 2、创建一个线程实例
     * 3、将线程实例提交给线程池实例执行（1、submit 2、execute 3、future）
     */
    @PostConstruct
    public void rn(){
        processDao.test();
        //创建一个线程池实例
        masterSchedulerService = ThreadUtils.newDaemonSingleThreadExecutor("Master-Scheduler-Thread");
        heartbeatMasterService = ThreadUtils.newDaemonScheduledExecutorService("Master-Main-Thread",Constants.defaulMasterHeartbeatThreadNum);

        //扫面任务
        MasterSchedulerThread masterSchedulerThread = new MasterSchedulerThread(processDao,masterConfig.getMasterExecTaskNum());
        masterSchedulerService.execute(masterSchedulerThread);

         //heartbeat thread implement
        Runnable heartBeatThread = heartBeatThread();
        heartbeatMasterService.scheduleAtFixedRate(heartBeatThread,5,10, TimeUnit.SECONDS);

        //TaskExecThread taskExecThread = new TaskExecThread();
        //Future<Boolean> future = masterSchedulerService.submit(taskExecThread);
        //System.out.println(future);
        //
        //Callable<Boolean> callable = callableThread();
        //Future<Boolean> futures = masterSchedulerService.submit(callable);
        //System.out.println(futures);

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


    /**
     *
     * 优雅停止
     *
     **/
    @Override
    public synchronized void stop(String cause) {

    }

}
