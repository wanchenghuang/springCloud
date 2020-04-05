package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

import com.chauncy.cloud.thread.common.ReadOnlyIterator;

import java.util.Iterator;
import java.util.Set;

/**
 * @Author cheng
 * @create 2020-04-04 21:19
 *
 * 候选节点信息,实现了Iterable，重写iterator方法
 */
public final class Candidate implements Iterable<Endpoint> {
    // 下游部件节点列表
    private final Set<Endpoint> endpoints;
    // 下游部件节点的总权重
    public final int totalWeight;

    public Candidate(Set<Endpoint> endpoints) {
        int sum = 0;
        for (Endpoint endpoint : endpoints) {
            sum += endpoint.weight;
        }
        this.totalWeight = sum;
        this.endpoints = endpoints;
    }

    public int getEndpointCount() {
        return endpoints.size();
    }

    @Override
    public final Iterator<Endpoint> iterator() {
        return ReadOnlyIterator.with(endpoints.iterator());
    }

    @Override
    public String toString() {
        return "Candidate [endpoints=" + endpoints + ", totalWeight=" + totalWeight
                + "]";
    }

}
