package com.chauncy.cloud.thread.imports.runner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.data.domain.po.thread.ImportDataStepPo;
import com.chauncy.cloud.data.domain.po.thread.PointPo;
import com.chauncy.cloud.data.mapper.thread.PointMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Author cheng
 * @create 2020-07-16 11:43
 *
 * 多线程查询
 */
@Slf4j
public class HighImportDataPointFuture implements Callable<List<PointPo>> {

    private PointMapper pointMapper;

    private ImportDataStepPo step;

    public HighImportDataPointFuture(ImportDataStepPo step,PointMapper pointMapper){

        this.step = step;
        this.pointMapper = pointMapper;
    }


    @Override
    public List<PointPo> call() throws Exception {
        log.info("start :{} ==  end :{}",step.getRangeStart(),step.getRangeEnd());
        List<PointPo> lists = pointMapper.selectList(new QueryWrapper<PointPo>().lambda()
                .between(PointPo::getPointId,step.getRangeStart(),step.getRangeEnd()));

        return lists;
    }
}
