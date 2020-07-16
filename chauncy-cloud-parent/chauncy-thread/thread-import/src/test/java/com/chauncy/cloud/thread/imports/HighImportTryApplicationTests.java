package com.chauncy.cloud.thread.imports;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chauncy.cloud.common.constant.Constants;
import com.chauncy.cloud.common.utils.DateUtil;
import com.chauncy.cloud.common.utils.ThreadUtils;
import com.chauncy.cloud.data.domain.po.thread.ImportDataStepPo;
import com.chauncy.cloud.data.domain.po.thread.ImportDataTaskPo;
import com.chauncy.cloud.data.domain.po.thread.PointPo;
import com.chauncy.cloud.data.mapper.thread.ImportDataStepMapper;
import com.chauncy.cloud.data.mapper.thread.ImportDataTaskMapper;
import com.chauncy.cloud.data.mapper.thread.PointMapper;
import com.chauncy.cloud.thread.imports.runner.HighImportDataPointFuture;
import com.chauncy.cloud.thread.imports.service.IPointService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author cheng
 * @create 2020-07-16 09:53
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class} )
@SpringBootTest
@Slf4j
public class HighImportTryApplicationTests {

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private IPointService pointService;

    private ExecutorService queryExecutorService;

    @Autowired
    private ImportDataStepMapper importDataStepMapper;

    @Autowired
    private ImportDataTaskMapper importDataTaskMapper;

    private int threadCount = 5; //子线程数

    private CountDownLatch countDownLatch = new CountDownLatch(threadCount) ;

    /**
     * 多线程插入数据
     *
     * select count(*) from point;
     * select count(*) from  import_point_201908 ;
     * delete from import_point_201908 ;
     * delete from point ;
     * delete from import_data_step ;
     * delete from import_data_task ;
     *
     * 共 1000000 万 数据
     * @throws InterruptedException
     */
    @Test
    public void createMillionData() throws InterruptedException {

        ExecutorService executorService  = Executors.newFixedThreadPool(5);
        for (int i = 1; i < 6; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        List<PointPo> points = new ArrayList<>();
                        for (int i = 1; i <100000 ; i++) {
                            PointPo point =new PointPo();
                            point.setUser(i);
                            point.setAvailablePoints(new BigDecimal(100000));
                            point.setDelayUpdateMode(1);
                            point.setFrozenPoints(new BigDecimal(100000));
                            point.setLastUpdateTime(LocalDateTime.now());
                            point.setLatestPointLogId(i);
                            point.setVersion(0);
                            points.add(point);
                        }
                        pointService.saveBatch(points);
                    }finally {
                        countDownLatch.countDown();
                    }

                }
            });
        }
        System.out.println("== 处理中 == ");
        countDownLatch.await();
        System.out.println("== 插入结束 == ");
    }

    /**
     * 多线程查询
     */
    @Test
    public void queryTask(){
        log.info("billNewRunnerImport start 导入step  and task 数据开始 2 ！");
        long start = System.currentTimeMillis();
        Date date = new Date();
        String day = DateUtil.format(DateUtil.parse("20190430"),DateUtil.DATE_FORMAT_DAY_SHORT);
        List<ImportDataTaskPo> importDataTasks = importDataTaskMapper.selectList(new QueryWrapper<ImportDataTaskPo>().lambda()
                .eq(ImportDataTaskPo::getType,Constants.POINT)
                .eq(ImportDataTaskPo::getDay,day));
        log.info("每天需要处理 importDataTasks size : {}", importDataTasks.size());

        for (ImportDataTaskPo task:importDataTasks) {
            List<ImportDataStepPo> steps = importDataStepMapper.selectList(new QueryWrapper<ImportDataStepPo>().lambda()
                    .eq(ImportDataStepPo::getDay,day)
                    .eq(ImportDataStepPo::getType,Constants.POINT)
                    .eq(ImportDataStepPo::getTaskId,task.getId()));
            recordHandlePoints(steps);
        }
        long end = System.currentTimeMillis();
        log.info("导入生成全部 task .step import_x_x_x 需要时间 ！time  = {}", end - start);
    }

    /**
     * 多线程查询
     */
    public List<PointPo> recordHandlePoints(List<ImportDataStepPo> steps){

        log.info("多线程查询======== start");
        queryExecutorService = ThreadUtils.newDaemonFixedThreadPool("query-thread",10);

        List<PointPo> points = new ArrayList<>();
        for (ImportDataStepPo step : steps) {
            Future<List<PointPo>> future = queryExecutorService.submit(new HighImportDataPointFuture(step, pointMapper));
            try {
                points.addAll(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        log.info("多线程查询======== point " + points.size() + " 条数==============");
        return points;
    }
}
