package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

import java.util.Random;
import java.util.logging.Logger;

/**
 * @Author cheng
 * @create 2020-04-04 21:23
 *
 * 负载均衡算法抽象实现类，所有负载均衡算法实现类的父类
 */
public abstract class AbstractLoadBalancer implements LoadBalancer {

    private final static Logger LOGGER = Logger.getAnonymousLogger();
    // 使用volatile变量替代锁（有条件替代）
    protected volatile Candidate candidate;

    protected final Random random;

    // 心跳线程
    private Thread heartbeatThread;

    public AbstractLoadBalancer(Candidate candidate) {
        if (null == candidate || 0 == candidate.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        this.candidate = candidate;
        random = new Random();
    }

    //只创建一次，使用synchronized是为了消除if语句和其语句体会引起的竞态
    //竞态：导致创建多个实例
    public synchronized void init() throws Exception {
        if (null == heartbeatThread) {
            heartbeatThread = new Thread(new HeartbeatTask(), "LB_Heartbeat");
            heartbeatThread.setDaemon(true);
            heartbeatThread.start();
        }
    }

    @Override
    public void updateCandidate(final Candidate candidate) {
        if (null == candidate || 0 == candidate.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + candidate);
        }
        // 更新volatile变量candidate
        //如果要变更下游部件的节点信息(如删除一个节点),那么配置管理器(一个单独的工作者线程)只需要
        //调用该方法()直接更新candidate变量的值
        this.candidate = candidate;
    }

    /*
     * 留给子类实现的抽象方法
     *
     * @see com.chauncy.cloud.thread.primary.ch3.loadBalancer.WeightedRoundRobinLoadBalancer#nextEndpoint
     */
    @Override
    public abstract Endpoint nextEndpoint();

    //监控节点并更新
    protected void monitorEndpoints() {
        // 读取volatile变量,设置currCandidate引用不可变
        final Candidate currCandidate = candidate;
        boolean isTheEndpointOnline;

        // 检测下游部件状态是否正常，并更新状态
        for (Endpoint endpoint : currCandidate) {
            isTheEndpointOnline = endpoint.isOnline();
            if (doDetect(endpoint) != isTheEndpointOnline) {
                endpoint.setOnline(!isTheEndpointOnline);
                if (isTheEndpointOnline) {
                    LOGGER.log(java.util.logging.Level.SEVERE, endpoint
                            + " offline!");
                } else {
                    LOGGER.log(java.util.logging.Level.INFO, endpoint
                            + " is online now!");
                }
            }
        }// for循环结束

    }

    // 检测指定的节点是否在线
    private boolean doDetect(Endpoint endpoint) {
        boolean online = true;
        // 模拟待测服务器随机故障
        int rand = random.nextInt(1000);
        if (rand <= 500) {
            online = false;
        }
        return online;
    }

    private class HeartbeatTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // 检测节点列表中所有节点是否在线
                    monitorEndpoints();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                // 什么也不做
            }
        }
    }// HeartbeatTask类结束
}