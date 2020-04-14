package com.chauncy.cloud.auth.authentication.events;

import com.chauncy.cloud.auth.authentication.service.IResourceService;
import com.chauncy.cloud.data.domain.po.organization.ResourcePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2020-04-14 10:12
 */
@Component
@Slf4j
public class BusReceiver {

    @Autowired
    private IResourceService resourceService;

    public void handleMessage(ResourcePo resource) {
        log.info("Received Message:<{}>", resource);
        resourceService.saveResource(resource);
    }
}
