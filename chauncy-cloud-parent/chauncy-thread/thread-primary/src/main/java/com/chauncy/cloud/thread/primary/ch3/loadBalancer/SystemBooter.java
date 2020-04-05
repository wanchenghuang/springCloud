package com.chauncy.cloud.thread.primary.ch3.loadBalancer;


import java.util.HashSet;
import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-04 23:12
 *
 * 负载均衡启动
 */
public class SystemBooter {

    public static void main(String[] args) throws Exception {

        SystemBooter systemBooter = new SystemBooter();
        //负责调用下游部件的服务调用
        ServiceInvoker rd = ServiceInvoker.getInstance();

        LoadBalancer lb = systemBooter.createLoadBalancer();

        // 在main线程中设置负载均衡器实例
        rd.setLoadBalancer(lb);

        lb.updateCandidate(new Candidate(loadEndpoints2()));
    }

    // 根据系统配置创建负载均衡器实例
    private LoadBalancer createLoadBalancer() throws Exception {

        LoadBalancer lb;
        Candidate candidate = new Candidate(loadEndpoints());
        //创建负载均衡实例
        lb = WeightedRoundRobinLoadBalancer.newInstance(candidate);

        return lb;

    }


    private Set<Endpoint> loadEndpoints(){
        Set<Endpoint> endpoints = new HashSet<>();

        // 模拟从数据库加载以下信息
        endpoints.add(new Endpoint("192.168.101.100", 8080, 3));
        endpoints.add(new Endpoint("192.168.101.101", 8080, 2));
        endpoints.add(new Endpoint("192.168.101.102", 8080, 5));
        endpoints.add(new Endpoint("192.168.101.103", 8080, 7));
        return endpoints;
    }

    private static Set<Endpoint> loadEndpoints2(){
        Set<Endpoint> endpoints = new HashSet<>();

        // 模拟从数据库加载以下信息
        endpoints.add(new Endpoint("192.168.101.104", 8080, 3));
        endpoints.add(new Endpoint("192.168.101.105", 8080, 2));
        endpoints.add(new Endpoint("192.168.101.106", 8080, 5));
        endpoints.add(new Endpoint("192.168.101.107", 8080, 7));
        return endpoints;
    }
}
