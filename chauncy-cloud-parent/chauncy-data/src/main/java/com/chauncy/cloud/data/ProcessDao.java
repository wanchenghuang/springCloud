package com.chauncy.cloud.data;

import com.chauncy.cloud.data.mapper.organization.RolesMapper;
import com.chauncy.cloud.data.mapper.organization.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author cheng
 * @create 2020-07-15 14:41
 */
@Component
@Slf4j
public class ProcessDao {

    @Autowired
    private UsersMapper userMapper;

    @Autowired
    private RolesMapper rolesMapper;

    public void test(){
        userMapper.selectById(1);
    }
}
