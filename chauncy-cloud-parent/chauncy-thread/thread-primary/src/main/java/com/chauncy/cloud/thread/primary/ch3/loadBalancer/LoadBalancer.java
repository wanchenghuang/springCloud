package com.chauncy.cloud.thread.primary.ch3.loadBalancer;

/**
 * @Author cheng
 * @create 2020-04-04 21:23
 */
public interface LoadBalancer {

    void updateCandidate(final Candidate candidate);
    Endpoint nextEndpoint();

}
