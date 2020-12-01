package mix;

import bean.UsersPO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.TimeZone;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

/**
 * @Author: wanchenghuang
 * @DateTime: 2020/9/2 7:01 下午
 * @Version: 2.0.0
 * @description: 静态对象赋值
 **/
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class StaticObjectTest {

	private static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
			.configure(READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
			.setTimeZone(TimeZone.getDefault())
			;

	@Test
	public void generateObject(){
		int i = 10;
		List<UsersPO> users = Lists.newArrayList();
		while (i<=100){
			users.add(UsersPO.generate().setAge(i));
			users.add(UsersPO.generate().setId(i));
			i++;
		}
		users.forEach(System.out::println);
	}

	/**
	 * ArrayNode:[]
	 * ObjectNode:{}
	 */
	@Test
	public void test1(){
		ObjectNode readerConn = objectMapper.createObjectNode();//{}
		//readerConn:{"querySql":[]},ArrayNode:[]
		ArrayNode sqlArr = readerConn.putArray("querySql");
		for (String sql : new String[]{"select * from t_ds_user"}) {
			sqlArr.add(sql);
		}
		System.out.println(sqlArr);
	}
}
