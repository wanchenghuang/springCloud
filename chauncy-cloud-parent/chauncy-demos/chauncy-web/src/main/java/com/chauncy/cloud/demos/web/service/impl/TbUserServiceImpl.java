package com.chauncy.cloud.demos.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.common.annotation.TargetDataSource;
import com.chauncy.cloud.common.enums.system.TargetDataSourceEnum;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.exception.BusinessException;
import com.chauncy.cloud.common.utils.CollectionUtils;
import com.chauncy.cloud.data.domain.dto.SaveTestUsersDto;
import com.chauncy.cloud.data.domain.dto.SearchUsersDto;
import com.chauncy.cloud.data.domain.po.test.TbUserPo;
import com.chauncy.cloud.data.domain.po.test.TbUsersPo;
import com.chauncy.cloud.data.domain.vo.test.SearchUsersVo;
import com.chauncy.cloud.data.mapper.test.TbUserMapper;
import com.chauncy.cloud.core.config.base.AbstractService;
import com.chauncy.cloud.data.mapper.test.TbUsersMapper;
import com.chauncy.cloud.demos.web.service.ITbUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-03-06
 */
@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class TbUserServiceImpl extends AbstractService<TbUserMapper, TbUserPo> implements ITbUserService {

    @Autowired
    private TbUserMapper mapper;

    @Autowired
    private TbUsersMapper usersMapper;

    @Override
    public List<SearchUsersVo> queryAllUsers() {
        List<SearchUsersVo> usersList = mapper.queryAllUsers();
        return usersList;
    }

    @Override
    public void test(String username) {
        TbUserPo user = mapper.selectOne(new QueryWrapper<TbUserPo>()
                .lambda().like(TbUserPo::getName,username));
        System.out.println(new Gson().toJson(user));
    }

    @Override
    @TargetDataSource(value = TargetDataSourceEnum.SLAVE1)
    public TbUsersPo queryUser(String username) {

        TbUsersPo tbUsersPo = usersMapper.queryUser(username);
        //测试事务
//        usersMapper.deleteById(27L);
//        int a= 1/0;
        return tbUsersPo;
    }

    @Override
    public void delByIds(List<Long> ids) {

        ids.forEach(id->{
            if (mapper.selectById(id) == null){
                throw new BusinessException(Code.ERROR,String.format("数据库不能再ID为[%s]的用户！",id));
            }
        });

        mapper.deleteBatchIds(ids);
    }

    @Override
    public PageInfo<SearchUsersVo> searchUsers(SearchUsersDto searchUsersDto) {

        //测试mybatis-plus结合lambda表达式使用
        List<TbUserPo> userPos = mapper.selectList(new QueryWrapper<TbUserPo>().lambda()
                .and(obj->obj.eq(TbUserPo::getType,searchUsersDto.getType())
                        .like(TbUserPo::getName,searchUsersDto.getName())));

        log.info(userPos.toString());

        Integer pageNo = searchUsersDto.getPageNo()==null ? defaultPageNo : searchUsersDto.getPageNo();
        Integer pageSize = searchUsersDto.getPageSize()==null ? defaultPageSize : searchUsersDto.getPageSize();

        //使用mybatis-plus分页插件进行分页查询
        PageInfo<SearchUsersVo> searchUsersVoPageInfo = PageHelper.startPage(pageNo,pageSize,defaultSoft)
                .doSelectPageInfo(()->mapper.searchUsers(searchUsersDto));

        //对分页后的数据进行处理
        searchUsersVoPageInfo.getList().forEach(a->{
            //逻辑处理
            if (a.getId() == 1184461168891789314L){
                a.setAge(1000);
            }
        });

        //去掉其中一个字段
        Set<String> exclusionSet = new HashSet<String>(){{
            add("id");
        }};
        List instanceList = CollectionUtils.getListByExclusion(searchUsersVoPageInfo.getList(),exclusionSet);
        searchUsersVoPageInfo.setList(instanceList);

        return searchUsersVoPageInfo;
    }

    @Override
    public void saveUser(SaveTestUsersDto saveTestUsersDto) {

        TbUserPo userPo = new TbUserPo();
        BeanUtils.copyProperties(saveTestUsersDto,userPo);

        mapper.insert(userPo);

        //测试事务回滚
//        int a = 1/0;

    }
}
