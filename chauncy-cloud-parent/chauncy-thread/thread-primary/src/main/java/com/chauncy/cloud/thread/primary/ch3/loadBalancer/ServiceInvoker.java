package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

import com.chauncy.cloud.thread.common.Debug;

/**
 * @Author cheng
 * @create 2020-04-04 23:46
 *
 * singleton
 */
public class ServiceInvoker {

    // 负载均衡器实例，使用volatile变量保障可见性
    private volatile LoadBalancer loadBalancer;

    // 私有构造器
    private ServiceInvoker(){};
    // 保存当前类的唯一实例
    private static final ServiceInvoker instance = new ServiceInvoker();

    //提供给外部获取当前类的唯一实例
    public static ServiceInvoker getInstance(){
        return instance;
    }

    /**
     * 根据指定的负载均衡器派发请求到特定的下游部件。
     *
     * @param request
     *          待派发的请求
     */
    public void dispatchRequest(Request request) {
        // 这里读取volatile变量loadBalancer
        Endpoint endpoint = getLoadBalancer().nextEndpoint();

        if (null == endpoint) {
            // 省略其他代码

            return;
        }

        // 将请求发给下游部件
        dispatchToDownstream(request, endpoint);
    }

    // 真正将指定的请求派发给下游部件
    private void dispatchToDownstream(Request request, Endpoint endpoint) {
        Debug.info("Dispatch request to " + endpoint + ":" + request);
        // 省略其他代码
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
