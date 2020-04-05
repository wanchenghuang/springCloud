package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

/**
 * @Author cheng
 * @create 2020-04-04 23:38
 *
 * 加权轮询负载均衡算法实现类
 */
public class WeightedRoundRobinLoadBalancer extends AbstractLoadBalancer{


    public WeightedRoundRobinLoadBalancer(Candidate candidate) {
        super(candidate);
    }

    // 通过该静态方法创建该类的实例，来调用实例方法
    public static LoadBalancer newInstance(Candidate candidate) throws Exception {

        WeightedRoundRobinLoadBalancer lb = new WeightedRoundRobinLoadBalancer(candidate);

        lb.init();//没有心跳线程则创建

        return lb;
    }

    // 在该方法中实现相应的负载均衡算法
    @Override
    public Endpoint nextEndpoint() {
        Endpoint selectedEndpoint = null;
        int subWeight = 0;
        int dynamicTotoalWeight;
        final double rawRnd = super.random.nextDouble();
        int rand;

        // 读取volatile变量candidate
        final Candidate candiate = super.candidate;
        dynamicTotoalWeight = candiate.totalWeight;
        for (Endpoint endpoint : candiate) {
            // 选取节点以及计算总权重时跳过非在线节点
            if (!endpoint.isOnline()) {
                dynamicTotoalWeight -= endpoint.weight;
                continue;
            }
            rand = (int) (rawRnd * dynamicTotoalWeight);
            subWeight += endpoint.weight;
            if (rand <= subWeight) {
                selectedEndpoint = endpoint;
                break;
            }
        }
        return selectedEndpoint;
    }
}
