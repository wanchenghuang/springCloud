package java8;

import bean.UsersPO;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-08-23 10:05
 *
 * map操作
 *
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class MapTest {


    /**
     * map操作
     */
    @Test
    public void entrySet(){

        Map<String,Object> usersMap1 = new HashMap();
        usersMap1.put("id",1);
        usersMap1.put("name","cheng1");
        usersMap1.put("age",18);
        usersMap1.put("salary",1000.00);
        usersMap1.put("money",new BigDecimal(100));

        Map<String,Object> usersMap2 = new HashMap();
        usersMap2.put("id",2);
        usersMap2.put("name","cheng2");
        usersMap2.put("age",28);
        usersMap2.put("salary",2000.00);
        usersMap2.put("money",new BigDecimal(200));

        Map<String,Object> usersMap3 = new HashMap();
        usersMap3.put("id",3);
        usersMap3.put("name","cheng3");
        usersMap3.put("age",39);
        usersMap3.put("salary",3000.00);
        usersMap3.put("money",new BigDecimal(300));

        List<Map<String,Object>> mapList = Lists.newArrayList();

        mapList.add(usersMap1);mapList.add(usersMap2);mapList.add(usersMap3);

        //遍历map
        usersMap1.forEach((k,v) -> System.out.println("key:value = " + k + ":" + v));

        //转为list<Object>
        List<UsersPO> collect = mapList.stream().map(a -> change(a)).collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    private UsersPO change(Map<String, Object> map) {

        UsersPO bean = new UsersPO();
        bean.setId((Integer) map.get("id"));
        bean.setName((String) map.get("name"));
        bean.setAge((Integer) map.get("age"));
        bean.setSalary((Double) map.get("salary"));
        bean.setMoney((BigDecimal) map.get("money"));

        return bean;
    }

}
