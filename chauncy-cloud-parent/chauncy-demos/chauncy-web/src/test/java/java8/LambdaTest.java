package java8;

import bean.Employee;
import java8.interfaces.MyPredicate;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-08-24 22:51
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class LambdaTest {

    List<Employee> employees = Arrays.asList(
            new Employee("张三",18,9999.99),
            new Employee("李四",38,5555.99),
            new Employee("王五",50,6666.66),
            new Employee("赵六",16,3333.33),
            new Employee("天启",8,7777.77)
    );

    @Test
    public void test1(){
        //lambda表达式,自定义函数式编程
        List<Employee> employeeList = filterEmployee(employees, e -> e.getAge() > 18);
        employeeList.forEach(System.out::println);

        //使用流stream API
        employees.stream().filter(e->e.getAge() > 18)
                .limit(2)
                .forEach(System.out::println);
    }

    public static List<Employee> filterEmployee(List<Employee> list, MyPredicate<Employee> mp){

        List<Employee> emps = Lists.newArrayList();
        list.forEach(a->{
            if (mp.test(a)){
                emps.add(a);
            }
        });
        return emps;
    }


}
