package com.chauncy.cloud.thread.executor.server.master.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2020-07-15 14:51
 */
@Component
@Data
public class MasterConfig {

    @Value("${master.exec.threads}")
    private int masterExecThreads;

    @Value("${master.exec.task.num}")
    private int masterExecTaskNum;

    @Value("${master.heartbeat.interval}")
    private int masterHeartbeatInterval;

    @Value("${master.task.commit.retryTimes}")
    private int masterTaskCommitRetryTimes;

    @Value("${master.task.commit.interval}")
    private int masterTaskCommitInterval;

    @Value("${master.max.cpuload.avg}")
    private double masterMaxCpuloadAvg;

    @Value("${master.reserved.memory}")
    private double masterReservedMemory;
}