package com.chauncy.cloud.thread.executor.server.master.runner;

import com.chauncy.cloud.data.ProcessDao;
import com.chauncy.cloud.thread.executor.utils.Stopper;
import com.chauncy.cloud.thread.executor.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;

/**
 * @Author cheng
 * @create 2020-07-15 14:46
 */
@Slf4j
public class MasterSchedulerThread implements Runnable {

    private final ProcessDao processDao;

    private final ExecutorService masterExecService;

    public MasterSchedulerThread(ProcessDao processDao,int masterExecThreadNum){

        this.processDao = processDao;
        this.masterExecService = ThreadUtils.newDaemonFixedThreadPool("Master-Exec-Thread",masterExecThreadNum);
    }

    @Override
    public void run() {
        while(Stopper.isRunning()){
            log.info("任务扫描开始。。。");
            log.info("模拟任务执行中");
            //任务具体操作
            MasterExecThread masterExecThread = new MasterExecThread(processDao);

            masterExecService.execute(masterExecThread);

            log.info("模拟任务执行结束并提交具体任务处理线程");

            try {
                //模拟线程真实工作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error("master scheduler thread exception",e);
            }finally {
                log.info("模拟释放锁操作");
            }
        }
    }
}
