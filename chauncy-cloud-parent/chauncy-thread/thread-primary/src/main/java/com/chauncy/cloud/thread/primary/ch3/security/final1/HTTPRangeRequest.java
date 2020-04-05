package com.chauncy.cloud.thread.primary.ch3.security.final1;

/**
 * @Author cheng
 * @create 2020-04-05 14:23
 *
 * final关键字保障对象初始化完毕
 *
 * 当一个线程看到HTTPRangeRequest对象的时候，线程看到的实例变脸分range所引用的对象必然是初始化完毕的，
 * url仍然可能为null(默认值)
 */
public class HTTPRangeRequest {
    private final Range range;
    private String url;

    public HTTPRangeRequest(String url, int lowerBound, int upperBound) {
        this.url = url;
        this.range = new Range(lowerBound, upperBound);
    }

    public static class Range {
        private long lowerBound;
        private long upperBound;

        public Range(long lowerBound, long upperBound) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        public long getLowerBound() {
            return lowerBound;
        }

        public void setLowerBound(long lowerBound) {
            this.lowerBound = lowerBound;
        }

        public long getUpperBound() {
            return upperBound;
        }

        public void setUpperBound(long upperBound) {
            this.upperBound = upperBound;
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Range getRange() {
        return range;
    }
}
/**
 *instance = new HTTPRangeReques七("h七tp://xyz.com/download/big.tar",0,1048576);
 * 可能会被编译成与如下伪代码等效的指令：
 * objRef=allocate(HTTPRangeReques七．class) ;／／子操作1： 分配对象所需的存储空间
   objRef.url = "http://xyz.com/download/big.tar";
 * objRange = allocate(Range.class);
 * objRange.lowerBound =0;／／子操作2： 初始化对象objRange
 * objRange.upperBound = 1048576;／／子操作3： 初始化对象objRange
 * objRef.range=objRange;／／子操作44： 发布对象objRange
 * instance=objRef; ／／子操作5： 发布对象objRef
 *
 * 由于实例变队range（引用型变虽）采用final关键字修饰，
 * 因此Java语言会保障构造器中对该变撒的初始化（赋值）操作（子操作4）
 * 以及该变量值所引用的对象(Range实 例）的初始化（子操作2和子操作3）被限定在子操作5前完成。
 * 这就保障了 HTTPRangeRequest实例对外可见的时候，
 * 该实例 的range字段所引用的对象已经初始化 完毕。
 * 而url字段由千没有采用final修饰，因此Java虚拟机仍然可能将其重排序到子操 作5之后。
 **/