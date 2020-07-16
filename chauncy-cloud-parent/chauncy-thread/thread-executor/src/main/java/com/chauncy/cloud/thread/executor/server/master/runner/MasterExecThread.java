package com.chauncy.cloud.thread.executor.server.master.runner;

import com.chauncy.cloud.common.holder.SpringContextHolder;
import com.chauncy.cloud.data.ProcessDao;
import com.chauncy.cloud.thread.executor.server.master.config.MasterConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author cheng
 * @create 2020-07-15 15:00
 */
@Slf4j
public class MasterExecThread implements Runnable{

    private MasterConfig masterConfig;

    private ProcessDao processDao;

    public MasterExecThread(ProcessDao processDao){

        this.masterConfig = SpringContextHolder.getBean(MasterConfig.class);
        this.processDao = processDao;

    }

    @Override
    public void run() {

        log.info("模拟任务具体处理开始。。。");
    }
}
