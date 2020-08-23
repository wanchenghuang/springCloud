package java8;

import bean.UsersPO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-08-23 09:44
 *
 * 统计功能
 *
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class SummaryStatisticsTest {

    /**
     *
     *
     * 统计测试
     *
     * Collectors.summarizing(Int/Long/Double)
     *
     * 平均值averaging、总数couting、最小值minBy、最大值maxBy、求和suming
     *
     */
    @Test
    public void summary(){

        UsersPO user1 = new UsersPO().setId(1).setName("cheng1").setAge(21).setSalary(1000.00);
        UsersPO user2 = new UsersPO().setId(2).setName("cheng2").setAge(22).setSalary(2000.00);
        UsersPO user3 = new UsersPO().setId(3).setName("cheng3").setAge(23).setSalary(3000.00);
        UsersPO user4 = new UsersPO().setId(4).setName("cheng4").setAge(24).setSalary(4000.00);
        UsersPO user5 = new UsersPO().setId(5).setName("cheng5").setAge(25).setSalary(5000.00);
        UsersPO user6 = new UsersPO().setId(9).setName("cheng6").setAge(29).setSalary(9000.00);

        List<UsersPO> users = Lists.newArrayList();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);


        DoubleSummaryStatistics salary = users.stream().collect(Collectors.summarizingDouble(UsersPO::getSalary));
        IntSummaryStatistics age = users.stream().collect(Collectors.summarizingInt(UsersPO::getAge));
        //求和
        Double salarySum = salary.getSum();
        //最大值
        Double salaryMax = salary.getMax();
        //最小值
        Double salaryMin = salary.getMin();
        //平均值
        Double salaryAvg = salary.getAverage();
        //个数
        long count = salary.getCount();
        log.info(String.format("和：%s;最大值：%s;最小值:%s;平均值:%s;个数：%s",salarySum,salaryMax,salaryMin,salaryAvg,count));

        //求和
        long ageSum = age.getSum();
        //最大值
        long ageMax = age.getMax();
        //最小值
        long ageMin = age.getMin();
        //平均值
        Double ageAvg = age.getAverage();
        //个数
        long ageCount = age.getCount();

        log.info(String.format("和：%s;最大值：%s;最小值:%s;平均值:%s;个数：%s",ageSum,ageMax,ageMin,ageAvg,ageCount));

    }
}
