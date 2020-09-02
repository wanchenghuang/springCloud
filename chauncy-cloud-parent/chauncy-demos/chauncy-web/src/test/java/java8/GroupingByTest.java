package java8;

import bean.UsersPO;
import com.chauncy.cloud.data.mapper.test.TbUsersMapper;
import java8.common.OptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-08-22 20:24
 *
 * group by生成一个拥有分组功能的Collector，有三个重载方法。
 *
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class GroupingByTest {

    @Mock
    private TbUsersMapper userMapper;

    //@InjectMocks
    //private ITbUsersService usersService;

    @Before
    public void setUp() {

    }


    @After
    public void after() {

    }

    /**
     * 一个参数,按照该参数进行分组。结果返回一个Map集合，每个Map的key默认是分组参数的类型，value是一个List集合
     *
     * 根据对象的某几个字段值查重
     */
    @Test
    public void duplicateCheck() {

        List<UsersPO> users = Lists.newArrayList();
        List<UsersPO> userList = Lists.newArrayList();

        UsersPO cheng1 = new UsersPO().setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng2 = new UsersPO().setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng3 = new UsersPO().setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng4 = new UsersPO().setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng5 = new UsersPO().setAge(26).setName("cheng5").setSalary(2000.00);

        //重复
        users.add(cheng1);users.add(cheng2);users.add(cheng3);users.add(cheng4);users.add(cheng5);
        //不重复
        userList.add(cheng1);userList.add(cheng3);userList.add(cheng5);

        //查重
        List<Map.Entry<String, List<UsersPO>>> duplicate = users.stream().collect(Collectors.groupingBy(e -> fetchGroupKey(e)))
                .entrySet().stream().filter(a->a.getValue().size()>1).collect(Collectors.toList());

        List<String> keys = Lists.newArrayList();
        if (duplicate.size() > 1){
            /*duplicate.forEach(a->{
                keys.add(a.getKey());
            });*/
            keys = duplicate.stream().map(a->a.getKey()).collect(Collectors.toList());

            log.info(String.format("存在重复的数据：%s",keys.toString()));
        }

        List<Map.Entry<String, List<UsersPO>>> noDuplicate = userList.stream().collect(Collectors.groupingBy(e -> fetchGroupKey(e)))
                .entrySet().stream().filter(a->a.getValue().size()>1).collect(Collectors.toList());

        System.out.println(noDuplicate);

        //使用自定义函数式方法--统一查重，只需要传递参数即可，不需要指定特定的类
        List<UsersPO> duplicates = OptionUtils.getDuplicates(users,UsersPO::uniqueAttributes);
        List<String> duplicateKeys = OptionUtils.getDuplicateKeys(users, UsersPO::uniqueAttributes);

        duplicates.forEach(System.out::println);
        duplicateKeys.forEach(System.out::println);

    }

    /**
     * 根据对象的某几个字段去重
     */
    @Test
    public void deDuplication(){

        List<UsersPO> users = Lists.newArrayList();
        List<UsersPO> userList = Lists.newArrayList();
        List<UsersPO> userList1 = Lists.newArrayList();
        List<UsersPO> userList2 = Lists.newArrayList();
        List<UsersPO> userList3 = Lists.newArrayList();
        List<UsersPO> userList4 = Lists.newArrayList();

        UsersPO cheng1 = new UsersPO().setAge(23).setName("cheng").setSalary(2001.00);

        UsersPO cheng2 = new UsersPO().setAge(23).setName("cheng").setSalary(2002.00);

        UsersPO cheng3 = new UsersPO().setAge(25).setName("cheng3").setSalary(2003.00);

        UsersPO cheng4 = new UsersPO().setAge(25).setName("cheng3").setSalary(2004.00);

        UsersPO cheng5 = new UsersPO().setAge(26).setName("cheng5").setSalary(2005.00);

        //重复
        users.add(cheng1);users.add(cheng2);users.add(cheng3);users.add(cheng4);users.add(cheng5);
        //不重复
        userList.add(cheng1);userList.add(cheng3);userList.add(cheng5);

        //全部字段去重
        List<UsersPO> usersPos = new ArrayList<>(new TreeSet<>(users));
        Set<UsersPO> usersPos2 = users.stream().collect(Collectors.toSet());
        List<UsersPO> usersPOs3 = users.stream().distinct().collect(Collectors.toList());

        /**
         * 根据多个字段去重
         *
         * Collectors.collectingAndThen()--收集之后继续做一些处理
         *
         * Collectors.toCollection()--把流中所有元素收集到给定的供应源创建的集合中
         *
         * Comparator.comparing()比较排序
         *
         */

        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                System.out.println(o);
            }
        };


        Consumer s = System.out::println;
        Consumer ss = (a)->System.out.println(a);

        s.accept("cheng");

        userList1.add(cheng2);userList1.add(cheng1);
        List<UsersPO> usersPoList = userList1.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                ()-> new TreeSet<>(Comparator.comparing(user->user.getAge()+"#"+user.getName()))),ArrayList::new));

        System.out.println(usersPoList);

        userList1.sort(Comparator.comparing(user->user.getAge()+"#"+user.getName()));

        // keyComparator 是创建一个自定义的比较器
        Collections.sort(userList1,Comparator.comparing(UsersPO::getAge,(s1,s2)-> {
            return s1.compareTo(s2);
        }));

    }

    /**
     * 分组
     *
     * @param tbUsersPo
     * @return
     */
    public static String fetchGroupKey(UsersPO tbUsersPo) {

        return tbUsersPo.getAge() + "#" + tbUsersPo.getName();
    }

    /**
     * 2个参数,第二参数是Collector类型，可以对value进行处理。
     */
    @Test
    public void groupingBy(){

        List<UsersPO> users = Lists.newArrayList();
        List<UsersPO> userList = Lists.newArrayList();

        UsersPO cheng1 = new UsersPO().setId(1).setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng2 = new UsersPO().setId(2).setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng3 = new UsersPO().setId(3).setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng4 = new UsersPO().setId(4).setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng5 = new UsersPO().setId(5).setAge(26).setName("cheng5").setSalary(2000.00);

        //重复
        users.add(cheng1);users.add(cheng2);users.add(cheng3);users.add(cheng4);users.add(cheng5);
        //不重复
        userList.add(cheng1);userList.add(cheng3);userList.add(cheng5);

        List<Map.Entry<String, List<UsersPO>>> duplicate = users.stream().collect(Collectors.groupingBy(e -> fetchGroupKey(e)))
                .entrySet().stream().filter(a->a.getValue().size()>1).collect(Collectors.toList());

        //可以对结果进行映射
        Map<String, List<Integer>> collect = users.stream().collect(Collectors.groupingBy(e -> fetchGroupKey(e),
                ////第二个参数对Map的value进行处理（映射）
                Collectors.mapping(user -> user.getId(), Collectors.toList())));

        log.info(collect.toString());

        //可以对结果进行
        Map<String, Long> collect1 = users.stream().collect(Collectors.groupingBy(UsersPO::getName,
                //获取count数量
                Collectors.counting()));
        log.info(collect1.toString());

        //对结果进行求和
        Map<String, Integer> collect2 = users.stream().collect(Collectors.groupingBy(UsersPO::getName,
                //对结果进行求和
                Collectors.summingInt(UsersPO::getAge)));
        log.info(collect2.toString());

    }

    /**
     * 3个参数，第三个参数添加了对结果Map的生成方式，默认是HashMap
     */
    @Test
    public void groupingBy3() {

        List<UsersPO> users = Lists.newArrayList();
        List<UsersPO> userList = Lists.newArrayList();

        UsersPO cheng1 = new UsersPO().setId(1).setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng2 = new UsersPO().setId(2).setAge(23).setName("cheng").setSalary(2000.00);

        UsersPO cheng3 = new UsersPO().setId(3).setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng4 = new UsersPO().setId(4).setAge(25).setName("cheng3").setSalary(2000.00);

        UsersPO cheng5 = new UsersPO().setId(5).setAge(26).setName("cheng5").setSalary(2000.00);

        //重复
        users.add(cheng1);
        users.add(cheng2);
        users.add(cheng3);
        users.add(cheng4);
        users.add(cheng5);
        //不重复
        userList.add(cheng1);
        userList.add(cheng3);
        userList.add(cheng5);

        List<Map.Entry<String, List<UsersPO>>> duplicate = users.stream().collect(Collectors.groupingBy(e -> fetchGroupKey(e)))
                .entrySet().stream().filter(a -> a.getValue().size() > 1).collect(Collectors.toList());

        TreeMap<String, Integer> collect = users.stream().collect(Collectors.groupingBy(UsersPO::getName,
                //决定map的生成方式，使用TreeMap
                TreeMap::new,
                //求和
                Collectors.summingInt(UsersPO::getAge)
        ));

        log.info(collect.toString());
    }


}
