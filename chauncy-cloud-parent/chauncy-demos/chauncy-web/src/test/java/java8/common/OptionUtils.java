package java8.common;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * @Author cheng
 * @create 2020-08-27 22:52
 *
 * 利用java8函数式编程特性编写工具类
 *
 */
public class OptionUtils<T,K,R> {

    /**
     * 对list分组去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> duplicateByKey(Function<? super T, Object> keyExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> isNull(seen.putIfAbsent(keyExtractor.apply(t),Boolean.TRUE));
    }

    /**
     * 对list分组
     *
     * @param list
     * @param classifier
     * @param <T> 对象
     * @param <K> 返回值/key
     * @return
     */
    static <T,K> Map<? extends K, List<T>> getDuplicatesMap(List<T> list, Function<? super T, ? extends K> classifier){
        Map<? extends K, List<T>> collect = list.stream().collect(Collectors.groupingBy(classifier));
        return collect;
    }

    /**
     * 查出重复的Bean
     *
     * @param list
     * @param classifier
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T,K> List<T> getDuplicates(final List<T> list,Function<? super T, ? extends K> classifier){

        Map<? extends K, List<T>> duplicatesMap = getDuplicatesMap(list, classifier);

        List<T> duplicateBeans = duplicatesMap.values()
                .stream().filter(duplicates -> duplicates.size() > 1)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return duplicateBeans;
    }

    /**
     * 查出重复的key
     *
     * @param list
     * @param classifier
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T,K> List<K> getDuplicateKeys(final List<T> list,Function<? super T, ? extends K> classifier) {

        Map<? extends K, List<T>> duplicatesMap = getDuplicatesMap(list, classifier);
        List<? extends Map.Entry<? extends K, List<T>>> duplicateBeans = duplicatesMap.entrySet()
                .stream()
                .filter(entrySet -> entrySet.getValue().size() > 1)
                .collect(Collectors.toList());

        List<K> keys = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(duplicateBeans)){
            keys = duplicateBeans.stream().map(a -> a.getKey()).collect(Collectors.toList());
        }
        return keys;
    }

}
