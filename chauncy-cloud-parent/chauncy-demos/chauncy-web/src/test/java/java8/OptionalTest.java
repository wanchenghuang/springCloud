package java8;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * @Author cheng
 * @create 2020-08-26 23:14
 *
 * 1、Optional.of();
 * 2、Optional.ofNullable()
 * 3、Optional.empty()
 * 4、isPresent()判断值是否存在
 */
public class OptionalTest {

    @Test
    public void test1(){
        String source = null;
        Assert.notNull(source, "Source must not be null");

    }
}
