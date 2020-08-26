package java8;

import bean.UsersPO;
import com.baomidou.mybatisplus.core.toolkit.support.BiIntFunction;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-08-23 11:09
 *
 * reduce返回单个的结果值，并且reduce操作每处理一个元素总是创建一个新值。
 * 常用的方法有average, sum, min, max, count，使用reduce方法都可实现
 *
 * T reduce(T identity, BinaryOperatoraccumulator)
 *    identity：它允许用户提供一个循环计算的初始值。accumulator：计算的累加器，其方法签名为apply(T t,U u)，在该reduce方法中第一个参数t为上次函数计算的返回值，第二个参数u为Stream中的元素，这个函数把这两个值计算apply，得到的和会被赋值给下次执行这个方法的第一个参数。有点绕看代码：
 *
 * int value = Stream.of(1, 2, 3, 4).reduce(100, (sum, item) -> sum + item);
 * Assert.assertSame(value, 110);
 *
 * 或者使用方法引用
 *value=Stream.of(1,2,3,4).reduce(100,Integer::sum);
 *这个例子中100即为计算初始值，每次相加计算值都会传递到下一次计算的第一个参数。
 *
 *reduce还有其它两个重载方法：
 *
 *Optional reduce(BinaryOperatoraccumulator):与上面定义基本一样，无计算初始值，所以他返回的是一个Optional。
 *U reduce(U identity,BiFunction<u,? u=""super="">accumulator,BinaryOperator combiner):与前面两个参数的reduce方法几乎一致，你只要注意到BinaryOperator其实实现了BiFunction和BinaryOperator两个接口。
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MapReduceTest {

    @Test
    public void reduce(){

        Consumer consumer = System.out::println;

        //BinaryOperator binaryOperator = List::add;

        BiIntFunction<Integer, Integer> sum2 = Integer::sum;

        /**
         * 一个参数
         */
        List<Integer> intList = Arrays.asList(1,2,3);
        Optional<Integer> result1=intList.stream().reduce(Integer::sum);


        UsersPO user1 = new UsersPO().setMoney(new BigDecimal(100)).setId(1).setName("cheng1").setAge(21).setSalary(1000.00);
        UsersPO user2 = new UsersPO().setMoney(new BigDecimal(200)).setId(2).setName("cheng2").setAge(22).setSalary(2000.00);
        UsersPO user3 = new UsersPO().setMoney(new BigDecimal(300)).setId(3).setName("cheng3").setAge(23).setSalary(3000.00);
        UsersPO user4 = new UsersPO().setMoney(new BigDecimal(400)).setId(4).setName("cheng4").setAge(24).setSalary(4000.00);
        UsersPO user5 = new UsersPO().setMoney(new BigDecimal(500)).setId(5).setName("cheng5").setAge(25).setSalary(5000.00);
        UsersPO user6 = new UsersPO().setMoney(new BigDecimal(900)).setId(9).setName("cheng6").setAge(29).setSalary(9000.00);

        List<UsersPO> users = Lists.newArrayList();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        /**
         * 1个参数
         *
         */
        //最大值
        Optional<BigDecimal> moneyMaxOptional = users.stream().map(user -> user.getMoney()).reduce(BigDecimal::max);
        OptionalDouble salaryMaxOptional = users.stream().mapToDouble(user -> user.getSalary()).reduce(Double::max);

        BigDecimal moneyMax = moneyMaxOptional.orElse(BigDecimal.ZERO);
        double salaryMax = salaryMaxOptional.orElse(0.00);

        //求和
        Optional<BigDecimal> moneySumOptional = users.stream().map(user -> user.getMoney()).reduce(BigDecimal::add);
        OptionalDouble salarySumOptional = users.stream().mapToDouble(user -> user.getSalary()).reduce(Double::sum);

        BigDecimal moneySum1 = moneySumOptional.orElse(BigDecimal.ZERO);
        double salarySum1 = salarySumOptional.orElse(0.00);
        log.info(String.format("最大底薪：%s;最大薪资：%s,底薪和：%s;薪资和：%s",moneyMax,salaryMax,moneySum1,salarySum1));



        /**
         * 2个参数
         *
         */
        //对用户列表进行处理
        //求和
        BigDecimal moneySum = users.stream().map(user->user.getMoney()).reduce(BigDecimal.ZERO,BigDecimal::add);
        int ageSum = users.stream().map(user->user.getAge()).reduce(0,Integer::sum);
        //内置函数
        long sum = users.stream().collect(Collectors.summarizingInt(a -> a.getAge())).getSum();
        long sum1 = users.stream().mapToInt(a -> a.getAge()).summaryStatistics().getSum();
        long sum3 = users.stream().mapToInt(UsersPO::getAge).sum();


    }
}
