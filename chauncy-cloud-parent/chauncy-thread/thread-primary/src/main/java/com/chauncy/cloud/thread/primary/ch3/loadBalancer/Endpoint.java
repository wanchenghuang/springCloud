package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

/**
 * @Author cheng
 * @create 2020-04-04 21:18
 *
 * 表示下游部件的节点
 */
public class Endpoint {
    public final String host;
    public final int port;
    public final int weight;
    //节点的服务状态，所有负载均衡算法实现类的抽象父类AbstractLoadBlancer内部会维护一个心跳线程
    //来定时检测下游部件各个节点的状态，并根据检测的结果来更新相应Endpoint的online实例变量的值，具体的
    //负载均衡实现类则根据变量的值决定其动作
    private volatile boolean online = true;

    public Endpoint(String host, int port, int weight) {
        this.host = host;
        this.port = port;
        this.weight = weight;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((host == null) ? 0 : host.hashCode());
        result = prime * result + port;
        result = prime * result + weight;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Endpoint other = (Endpoint) obj;
        if (host == null) {
            if (other.host != null)
                return false;
        } else if (!host.equals(other.host))
            return false;
        if (port != other.port)
            return false;
        if (weight != other.weight)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Endpoint [host=" + host + ", port=" + port + ", weight=" + weight
                + ", online=" + online + "]\n";
    }

}

