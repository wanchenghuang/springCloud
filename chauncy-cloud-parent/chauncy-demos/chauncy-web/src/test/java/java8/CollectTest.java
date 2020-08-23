package java8;

import bean.UsersPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author cheng
 * @create 2020-08-23 12:46
 *
 * 对集合处理
 *
 * 收集结果collect
 *
 *   当你处理完流时，通常只是想查看一下结果，而不是将他们聚合为一个值。先看collect的基础方法，它接受三个参数：
 *
 * R collect(Suppliersupplier, BiConsumer<r,? t="" super="">accumulator, BiConsumer<r,r>combiner)
 * supplier：一个能创造目标类型实例的方法。accumulator：一个将当元素添加到目标中的方法。combiner：一个将中间状态的多个结果整合到一起的方法（并发的时候会用到）。接着看代码：
 *
 * Stream stream = Stream.of(1, 2, 3, 4).filter(p -> p > 2);
 * List result = stream.collect(() -> new ArrayList<>(), (list, item) -> list.add(item), (one, two) -> one.addAll(two));
 *   或者使用方法引用
 *
 * result=stream.collect(ArrayList::new,List::add,List::addAll);
 *
 *这个例子即为过滤大于2的元素，将剩余结果收集到一个新的list中。
 *
 *第一个方法生成一个新的ArrayList；
 *第二个方法中第一个参数是前面生成的ArrayList对象，第二个参数是stream中包含的元素，方法体就是把stream中的元素加入ArrayList对象中。第二个方法被反复调用直到原stream的元素被消费完毕；
 *第三个方法也是接受两个参数，这两个都是ArrayList类型的，方法体就是把第二个ArrayList全部加入到第一个中；
 *代码有点繁琐，或者使用collect的另一个重载方法:
 *
 *<r, a>R collect(Collector collector)
 *注意到Collector其实是上面supplier、accumulator、combiner的聚合体。那么上面代码就变成：
 *
 *Listlist=Stream.of(1,2,3,4).filter(p->p>2).collect(Collectors.toList());
 *
 *
 *
 * Collector主要包含五个参数，它的行为也是由这五个参数来定义的，如下所示：
 *
 * // supplier参数用于生成结果容器，容器类型为A。
 * Supplier<A> supplier();
 *
 * // accumulator用于归纳元素，泛型T就是元素，它会将流中的元素同结果容器A发生操作。
 * BiConsumer<A, T> accumulator();
 *
 * // combiner用于合并两个并行执行的结果，将其合并为最终结果A。
 * BinaryOperator<A> combiner();
 *
 * // finisher用于将之前完整的结果R转为A。
 * Function<A, R> finisher();
 *
 * // characteristics表示当前Collector的特征值，是一个不可变的Set。
 * Set<Characteristics> characteristics();
 *
 * 四参方法，用于生成一个Collector，T代表流中的元素，R代表最终的结果。因为没有finisher参数，所以需要有IDENTITY_FINISH特征值
 *
 * 五参方法，用于生成一个Collector，T代表流中的元素，A代表中间结果，R代表最终结果，finisher用于将A转换为R。
 *  CollectorImpl(Supplier<A> supplier,
 *                BiConsumer<A, T> accumulator,
 *                BinaryOperator<A> combiner,
 *                Function<A,R> finisher,
 *                Set<Characteristics> characteristics)
 *
 * 具体看源码 比如Collectors.toList()
 *
 * downstream：终止操作--完成对数据集中数据的处理--》求和、平均值、reduce、collect等
 *
 * Function：函数式-》user->user.getAge、User::getAge
 *
 * Supplier<A> supplier(): 供给型接口-》ArrayList::new,supplier参数用于生成结果容器，容器类型为A。
 *
 * BiConsumer<A, T> accumulator():消费型接口-》List::add [(list,item)->list.add(item)]
 *
 * BinaryOperator<A> combiner(): (left, right) -> { left.addAll(right); return left; },(m, n) -> m,Integer::sum
 * ,combiner用于合并两个并行执行的结果，将其合并为最终结果A。
 *
 */
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CollectTest {

    /**
     * toCollection方法
     *
     * 将流中的元素全部放置到一个集合中返回，这里使用Collection，泛指多种集合
     * 平时用的toList,toMap,toSet。。。（Collectors.toList()...）
     *
     * 自定义返回集合的类型,结合源码理解
     *
     * public static <T, C extends Collection<T>>
     * Collector<T, ?, C> toCollection(Supplier<C> collectionFactory) {
     *         return new CollectorImpl<>(collectionFactory, Collection<T>::add,
     *                                    (r1, r2) -> { r1.addAll(r2); return r1; },
     *                                    CH_ID);
     *     }
     *  参数是供给型参数
     */
    @Test
    public void toCollect(){
        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]
        LinkedList<String> newList = list.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println(newList);// [123, 521, 100, 228, 838, 250, 345
    }

    /**
     * 将流中的元素放置到一个List集合中返回，默认为ArrayList
     *
     * public static <T>
     *     Collector<T, ?, List<T>> toList() {
     *         return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
     *                                    (left, right) -> { left.addAll(right); return left; },
     *                                    CH_ID);
     *     }
     */
    @Test
    public void toList() {

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]
        List<String> newList = list.stream().collect(Collectors.toList());
        System.out.println(newList);// [123, 521, 100, 228, 838, 250, 345]
    }

    /**
     * 将流中的元素放置到一个Set集合中返回，默认为HashSet。
     *
     *   public static <T>
     *   Collector<T, ?, Set<T>> toSet() {
     *      return new CollectorImpl<>((Supplier<Set<T>>) HashSet::new, Set::add,
     *              (left, right) -> { left.addAll(right); return left; },
     *              CH_UNORDERED_ID);
     *   }
     */
    @Test
    public void toSet(){
        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]
        Set<String> newSet = list.stream().collect(Collectors.toSet());
        System.out.println(newSet);// [100, 123, 521, 345, 228, 838, 250]
    }

    /**
     * toMap方法
     *
     * 根据传入的键生成器和值生成器，将生成的键和值保存到一个Map中返回，键和值的生成都依赖于元素，可以指定出现重复键时的处理方案和保存结果的Map。
     *
     * 还有支持并发toConcurrentMap方法，同样有三种重载方法，与toMap基本一致，只是它最后使用的Map是并发ConcurrentHashMap。
     *
     *
     * // 指定键和值的生成方式，遇到键冲突的情况默认抛出异常，默认使用HashMap。
     * public static <T, K, U> Collector<T, ?, Map<K,U>> toMap(
     *             Function<? super T, ? extends K> keyMapper,
     *             Function<? super T, ? extends U> valueMapper) {
     *     return toMap(keyMapper, valueMapper, throwingMerger(), HashMap::new);
     * }
     *
     * // 指定键和值的生成方式，遇到键冲突的情况使用传入的方法处理，默认使用HashMap。
     * public static <T, K, U> Collector<T, ?, Map<K,U>> toMap(
     *             Function<? super T, ? extends K> keyMapper,
     *             Function<? super T, ? extends U> valueMapper,
     *             BinaryOperator<U> mergeFunction) {
     *     return toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
     * }
     *
     * // 指定键和值的生成方式，遇到键冲突的情况使用传入的方法处理，使用传入的Map类型返回数据。前两种方式最终还是调用此方法来返回Map数据。
     * public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(
     *             Function<? super T, ? extends K> keyMapper,
     *             Function<? super T, ? extends U> valueMapper,
     *             BinaryOperator<U> mergeFunction,
     *             Supplier<M> mapSupplier) {
     *     BiConsumer<M, T> accumulator = (map, element) -> map.merge(
     *             keyMapper.apply(element),
     *             valueMapper.apply(element),
     *             mergeFunction);
     *     return new CollectorImpl<>(mapSupplier, accumulator, mapMerger(mergeFunction), CH_ID);
     * }
     */
    @Test
    public void toMap(){

        Map<String, String> newMap = null;
        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        // 123和100的键都是1，导致冲突，默认抛出异常，使用limit截取前两个元素。
        newMap = list.stream().limit(2).collect(Collectors.toMap(e -> e.substring(0, 1), e -> e));
        System.out.println(newMap);// {1=123, 5=521}

        // 传入主键冲突时的处理方法，保留先插入的值，默认使用HashMap，对主键由小到大排序。
        newMap = list.stream().collect(Collectors.toMap(e -> e.substring(0, 1), e -> e, (m, n) -> m));
        System.out.println(newMap);// {1=123, 2=228, 3=345, 5=521, 8=838}

        // 传入主键冲突时的处理方法，相同key将value值相加，默认使用HashMap，对主键由小到大排序
        Map<String, Integer> collect = list.stream().collect(Collectors.toMap(e -> e.substring(0, 1), v ->Integer.valueOf(v) , Integer::sum));
        System.out.println(collect);// {1=223, 2=478, 3=345, 5=521, 8=838}

        // 传入主键冲突时的处理方法，保留新插入的值，默认使用LinkedHashMap，对主键按照插入顺序排序。
        newMap = list.stream().collect(Collectors.toMap(e -> e.substring(0, 1), e -> e, (m, n) -> n, LinkedHashMap::new));
        System.out.println(newMap);// {1=100, 5=521, 2=250, 8=838, 3=345}

    }

    /**
     * joining 将流中的元素全部以字符串的方式连接到一起，可以指定连接符，也可以指定前后缀
     *
     *
     * // 将流中的元素全部以字符串的方式连接到一起，不使用连接符，也不指定前后缀。
     * public static Collector<CharSequence, ?, String> joining() {
     *     return new CollectorImpl<CharSequence, StringBuilder, String>(
     *             StringBuilder::new, StringBuilder::append,
     *             (r1, r2) -> { r1.append(r2); return r1; },
     *             StringBuilder::toString, CH_NOID);
     * }
     *
     * // 将流中的元素全部以字符串的方式连接到一起，使用指定的连接符，不指定前后缀。
     * public static Collector<CharSequence, ?, String> joining(CharSequence delimiter) {
     *     return joining(delimiter, "", "");
     * }
     *
     * // 将流中的元素全部以字符串的方式连接到一起，使用指定的连接符，使用指定的前后缀。
     * public static Collector<CharSequence, ?, String> joining(CharSequence delimiter,
     *                                                          CharSequence prefix,
     *                                                          CharSequence suffix) {
     *     return new CollectorImpl<>(
     *             () -> new StringJoiner(delimiter, prefix, suffix),
     *             StringJoiner::add, StringJoiner::merge,
     *             StringJoiner::toString, CH_NOID);
     * }
     *
     */
    @Test
    public void joining(){

        String str = null;
        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        str = list.stream().collect(Collectors.joining());
        System.out.println(str);// 123521100228838250345

        str = list.stream().collect(Collectors.joining("-"));
        System.out.println(str);// 123-521-100-228-838-250-345

        str = list.stream().collect(Collectors.joining("-", "<", ">"));
        System.out.println(str);// <123-521-100-228-838-250-345>

    }

    /**
     * 将流中的元素按照传入的方法进行处理，并将结果按照指定的格式返回。
     *
     * public static <T, U, A, R>
     * Collector<T, ?, R> mapping(
     *             Function<? super T, ? extends U> mapper,
     *             Collector<? super U, A, R> downstream) {
     *     BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
     *     return new CollectorImpl<>(
     *             downstream.supplier(),
     *             (r, t) -> downstreamAccumulator.accept(r, mapper.apply(t)),
     *             downstream.combiner(),
     *             downstream.finisher(),
     *             downstream.characteristics());
     * }
     *
     *
     *
     */
    @Test
    public void mapping(){

        List<UsersPO> usersPoList = new ArrayList<UsersPO>();

        usersPoList.add(new UsersPO().setId(1).setAge(23).setName("cheng1").setSalary(2000.00));
        usersPoList.add(new UsersPO().setId(2).setAge(23).setName("cheng2").setSalary(3000.00));
        usersPoList.add(new UsersPO().setId(3).setAge(23).setName("cheng3").setSalary(4000.00));

        List<String> names = usersPoList.stream().collect(Collectors.mapping(UsersPO::getName, Collectors.toList()));

        List<String> names2 = usersPoList.stream().map(a->a.getName()).collect(Collectors.toList());

        System.out.println(names);// [cheng1, cheng2, cheng3]

    }

    /**
     * 该方法是按照传入的collector处理完之后，对归纳的结果进行再处理。
     *
     * public static<T,A,R,RR> Collector<T,A,RR> collectingAndThen(
     *             Collector<T,A,R> downstream,
     *             Function<R,RR> finisher) {
     *     Set<Collector.Characteristics> characteristics = downstream.characteristics();
     *     if (characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
     *         if (characteristics.size() == 1)
     *             characteristics = Collectors.CH_NOID;
     *         else {
     *             characteristics = EnumSet.copyOf(characteristics);
     *             characteristics.remove(Collector.Characteristics.IDENTITY_FINISH);
     *             characteristics = Collections.unmodifiableSet(characteristics);
     *         }
     *     }
     *     return new CollectorImpl<>(downstream.supplier(),
     *                                downstream.accumulator(),
     *                                downstream.combiner(),
     *                                downstream.finisher().andThen(finisher),
     *                                characteristics);
     * }
     *
     * downstream: 终止操作
     * Function<R,RR>,对结果的处理
     *
     * 场景：结合comparator.comparing()和treeSet去重
     */
    @Test
    public void collectAndThen(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]
        Integer size = list.stream().collect(Collectors.collectingAndThen(Collectors.toList(), List::size));
        System.out.println(size);// 7
    }

    /**
     * counting方法
     * 该方法主要用来计数。
     *
     * public static <T> Collector<T, ?, Long> counting() {
     *     return reducing(0L, e -> 1L, Long::sum);
     *  }
     */
    @Test
    public void count(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        Long count = list.stream().collect(Collectors.counting());
        System.out.println(count);// 7
    }

    /**
     * 对流中的元素做统计归纳，有三个重载方法，和Stream里的三个reduce方法对应，二者是可以替换使用的，作用完全一致。
     *
     * // 返回一个可以直接产生Optional类型结果的Collector，没有初始值。
     * public static <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op) {
     *     class OptionalBox implements Consumer<T> {
     *         T value = null;
     *         boolean present = false;
     *
     *         @Override
     *         public void accept(T t) {
     *             if (present) {
     *                 value = op.apply(value, t);
     *             }
     *             else {
     *                 value = t;
     *                 present = true;
     *             }
     *         }
     *     }
     *     return new CollectorImpl<T, OptionalBox, Optional<T>>(
     *             OptionalBox::new, OptionalBox::accept,
     *             (a, b) -> { if (b.present) a.accept(b.value); return a; },
     *             a -> Optional.ofNullable(a.value), CH_NOID);
     * }
     *
     * // 返回一个可以直接产生结果的Collector，指定初始值。
     * public static <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op) {
     *     return new CollectorImpl<>(
     *             boxSupplier(identity),
     *             (a, t) -> { a[0] = op.apply(a[0], t); },
     *             (a, b) -> { a[0] = op.apply(a[0], b[0]); return a; },
     *             a -> a[0],
     *             CH_NOID);
     * }
     *
     * // 返回一个可以直接产生结果的Collector，指定初始值，在返回结果之前先使用传入的方法将流进行转换。
     * public static <T, U> Collector<T, ?, U> reducing(
     *             U identity,
     *             Function<? super T, ? extends U> mapper,
     *             BinaryOperator<U> op) {
     *     return new CollectorImpl<>(
     *             boxSupplier(identity),
     *             (a, t) -> { a[0] = op.apply(a[0], mapper.apply(t)); },
     *             (a, b) -> { a[0] = op.apply(a[0], b[0]); return a; },
     *             a -> a[0], CH_NOID);
     * }
     *
     */
    @Test
    public void reducing(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        Optional<Integer> optional = list.stream().limit(4).map(String::length).collect(Collectors.reducing(Integer::sum));
        //等价
        Optional<Integer> optional1 = list.stream().limit(4).map(String::length).reduce(Integer::sum);

        System.out.println(optional);// Optional[12]

        Integer integer = list.stream().limit(3).map(String::length).collect(Collectors.reducing(0, Integer::sum));
        System.out.println(integer);// 9

        Integer sum = list.stream().limit(4).collect(Collectors.reducing(0, String::length, Integer::sum));
        System.out.println(sum);// 12
    }

    /**
     *
     * minBy方法和maxBy方法
     * 生成一个用于获取最小值或者最大值的Optional结果的Collector。
     *
     *  public static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator) {
     *      return reducing(BinaryOperator.minBy(comparator));
     *  }
     *
     *  public static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator) {
     *      return reducing(BinaryOperator.maxBy(comparator));
     */
    @Test
    public void minOrMaxBy(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        Optional<String> max = list.stream().collect(Collectors.maxBy((m, n) -> Integer.valueOf(m) - Integer.valueOf(n)));
        System.out.println(max);// Optional[838]

        Optional<String> min = list.stream().collect(Collectors.minBy((m, n) -> Integer.valueOf(m) - Integer.valueOf(n)));
        System.out.println(min);// Optional[100]
    }

    /**
     *
     * summingInt方法、summingLong方法和summingDouble方法
     *
     * 这三个方法适用于汇总的，返回值分别是IntSummaryStatistics、LongSummaryStatistics和DoubleSummaryStatistics。
     *在这些返回值中包含有流中元素的指定结果的数量、和、最大值、最小值、平均值
     *
     * public static <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper) {
     *     return new CollectorImpl<T, IntSummaryStatistics, IntSummaryStatistics>(
     *             IntSummaryStatistics::new,
     *             (r, t) -> r.accept(mapper.applyAsInt(t)),
     *             (l, r) -> { l.combine(r); return l; }, CH_ID);
     * }
     *
     * public static <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper) {
     *     return new CollectorImpl<T, LongSummaryStatistics, LongSummaryStatistics>(
     *             LongSummaryStatistics::new,
     *             (r, t) -> r.accept(mapper.applyAsLong(t)),
     *             (l, r) -> { l.combine(r); return l; }, CH_ID);
     * }
     *
     * public static <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper) {
     *     return new CollectorImpl<T, DoubleSummaryStatistics, DoubleSummaryStatistics>(
     *             DoubleSummaryStatistics::new,
     *             (r, t) -> r.accept(mapper.applyAsDouble(t)),
     *             (l, r) -> { l.combine(r); return l; }, CH_ID);
     * }
     *
     */
    @Test
    public void summarizing(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        IntSummaryStatistics intSummaryStatistics = list.stream().collect(Collectors.summarizingInt(Integer::parseInt));
        System.out.println(intSummaryStatistics);// {count=7, sum=2405, min=100, average=343.571429, max=838}

        LongSummaryStatistics longSummaryStatistics = list.stream().collect(Collectors.summarizingLong(Long::parseLong));
        System.out.println(longSummaryStatistics);// {count=7, sum=2405, min=100, average=343.571429, max=838}

        DoubleSummaryStatistics doubleSummaryStatistics = list.stream().collect(Collectors.summarizingDouble(Double::parseDouble));
        System.out.println(doubleSummaryStatistics);// {count=7, sum=2405.000000, min=100.000000, average=343.571429, max=838.000000}

    }

    /**
     * averagingInt方法、averagingLong方法和averagingDouble方法
     * 生成一个用于求元素平均值的Collector，首先将元素转换类型，然后再求平均值。
     *
     * 参数的作用就是将元素转换为指定的类型，求平均值涉及到除法操作，结果一律为Double类型。
     *
     * public static <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper) {
     *     return new CollectorImpl<>(
     *             () -> new long[2],
     *             (a, t) -> { a[0] += mapper.applyAsInt(t); a[1]++; },
     *             (a, b) -> { a[0] += b[0]; a[1] += b[1]; return a; },
     *             a -> (a[1] == 0) ? 0.0d : (double) a[0] / a[1], CH_NOID);
     * }
     *
     * public static <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> mapper) {
     *     return new CollectorImpl<>(
     *             () -> new long[2],
     *             (a, t) -> { a[0] += mapper.applyAsLong(t); a[1]++; },
     *             (a, b) -> { a[0] += b[0]; a[1] += b[1]; return a; },
     *             a -> (a[1] == 0) ? 0.0d : (double) a[0] / a[1], CH_NOID);
     * }
     *
     * public static <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> mapper) {
     *     return new CollectorImpl<>(
     *             () -> new double[4],
     *             (a, t) -> { sumWithCompensation(a, mapper.applyAsDouble(t)); a[2]++; a[3]+= mapper.applyAsDouble(t); },
     *             (a, b) -> { sumWithCompensation(a, b[0]); sumWithCompensation(a, b[1]); a[2] += b[2]; a[3] += b[3]; return a; },
     *             a -> (a[2] == 0) ? 0.0d : (computeFinalSum(a) / a[2]), CH_NOID);
     * }
     *
     */
    @Test
    public void averaging(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        double intAverage = list.stream().collect(Collectors.averagingInt(Integer::parseInt));
        System.out.println(intAverage);// 343.57142857142856

        double longAverage = list.stream().collect(Collectors.averagingLong(Long::parseLong));
        System.out.println(longAverage);// 343.57142857142856

        double doubleAverage = list.stream().collect(Collectors.averagingDouble(Double::parseDouble));
        System.out.println(doubleAverage);// 343.57142857142856

    }


    /**
     * groupingBy方法
     * 生成一个拥有分组功能的Collector，有三个重载方法。
     *
     * // 只需一个分组参数classifier，内部自动将结果保存到一个Map中，每个Map键的类型即classifier的结果类型，默认将组的元素保存在List中。
     * public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(
     *             Function<? super T, ? extends K> classifier) {
     *     return groupingBy(classifier, toList());
     * }
     *
     *
     * // 在上面方法的基础上增加了对流中元素的处理方式的Collector，默认是List。
     * public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(
     *             Function<? super T, ? extends K> classifier,
     *             Collector<? super T, A, D> downstream) {
     *     return groupingBy(classifier, HashMap::new, downstream);
     * }
     *
     *
     * // 在第二个方法的基础上再添加了结果Map的生成方法，默认是HashMap。
     * public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(
     *             Function<? super T, ? extends K> classifier,
     *             Supplier<M> mapFactory,
     *             Collector<? super T, A, D> downstream) {
     *     Supplier<A> downstreamSupplier = downstream.supplier();
     *     BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
     *     BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
     *         K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
     *         A container = m.computeIfAbsent(key, k -> downstreamSupplier.get());
     *         downstreamAccumulator.accept(container, t);
     *     };
     *     BinaryOperator<Map<K, A>> merger = Collectors.<K, A, Map<K, A>>mapMerger(downstream.combiner());
     *     @SuppressWarnings("unchecked")
     *     Supplier<Map < K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;
     *
     *     if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
     *         return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_ID);
     *     }
     *     else {
     *         @SuppressWarnings("unchecked")
     *         Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
     *         Function<Map<K, A>, M> finisher = intermediate -> {
     *             intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
     *             @SuppressWarnings("unchecked")
     *             M castResult = (M) intermediate;
     *             return castResult;
     *         };
     *         return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_NOID);
     *     }
     * }
     */
    @Test
    public void groupingBy(){

        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        Map<String, List<String>> groupByFirst = list.stream().collect(Collectors.groupingBy(e -> e.substring(0, 1)));
        System.out.println(groupByFirst);// {1=[123, 100], 2=[228, 250], 3=[345], 5=[521], 8=[838]}

        Map<String, Set<String>> groupByLast = list.stream().collect(Collectors.groupingBy(e -> e.substring(e.length() - 1), Collectors.toSet()));
        System.out.println(groupByLast);// {0=[100, 250], 1=[521], 3=[123], 5=[345], 8=[228, 838]}

        Map<Integer, Set<String>> groupByLength = list.stream().collect(Collectors.groupingBy(String::length, HashMap::new, Collectors.toSet()));
        System.out.println(groupByLength);// {3=[100, 123, 521, 345, 228, 838, 250]}

    }

    /**
     * partitioningBy方法
     * 将流中的元素按照给定的校验规则的结果分为两个部分，放到Map中返回，键是Boolean类型，值为元素的列表List
     *
     * // 只需一个校验参数predicate。
     * public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate) {
     *     return partitioningBy(predicate, toList());
     * }
     *
     * // 在上面方法的基础上增加了对流中元素的处理方式的Collector，默认的处理方法就是Collectors.toList()。
     * public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate,
     *                                                 Collector<? super T, A, D> downstream) {
     *     BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
     *     BiConsumer<Partition<A>, T> accumulator = (result, t) ->
     *             downstreamAccumulator.accept(predicate.test(t) ? result.forTrue : result.forFalse, t);
     *     BinaryOperator<A> op = downstream.combiner();
     *     BinaryOperator<Partition<A>> merger = (left, right) ->
     *             new Partition<>(op.apply(left.forTrue, right.forTrue),
     *                             op.apply(left.forFalse, right.forFalse));
     *     Supplier<Partition<A>> supplier = () ->
     *             new Partition<>(downstream.supplier().get(),
     *                             downstream.supplier().get());
     *     if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
     *         return new CollectorImpl<>(supplier, accumulator, merger, CH_ID);
     *     }
     *     else {
     *         Function<Partition<A>, Map<Boolean, D>> finisher = par ->
     *                 new Partition<>(downstream.finisher().apply(par.forTrue),
     *                                 downstream.finisher().apply(par.forFalse));
     *         return new CollectorImpl<>(supplier, accumulator, merger, finisher, CH_NOID);
     *     }
     * }
     */
    @Test
    public void partioningBy(){
        List<String> list = Arrays.asList("123", "521", "100", "228", "838", "250", "345");
        System.out.println(list);// [123, 521, 100, 228, 838, 250, 345]

        Map<Boolean, List<String>> moreThan = list.stream().collect(Collectors.partitioningBy(e -> Integer.parseInt(e) > 300));
        System.out.println(moreThan);// {false=[123, 100, 228, 250], true=[521, 838, 345]}

        Map<Boolean, Set<String>> lessThan = list.stream().collect(Collectors.partitioningBy(e -> Integer.parseInt(e) < 300, Collectors.toSet()));
        System.out.println(lessThan);// {false=[521, 345, 838], true=[100, 123, 228, 250]}
    }
}


