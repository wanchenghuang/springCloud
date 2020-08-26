package java8.interfaces;

/**
 * @Author cheng
 * @create 2020-08-24 22:49
 *
 * 断言器
 */
@FunctionalInterface
public interface MyPredicate<T> {

    public boolean test(T t);
}
