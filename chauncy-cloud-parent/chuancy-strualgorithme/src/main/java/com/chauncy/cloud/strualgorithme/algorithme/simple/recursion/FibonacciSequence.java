package com.chauncy.cloud.strualgorithme.algorithme.simple.recursion;

/**
 * @Author cheng
 * @create 2020-07-28 22:11
 *
 * 第一类问题：问题的定义是按递归定义的
 *
 *
 */
public class FibonacciSequence {

    public static void main(String[] args){

        System.out.println(add(5));
    }

    /**
     * 阶乘的递归实现
     *
     * @param n
     * @return
     */
    public static long f(int n){
        if(n == 1)   // 递归终止条件
            return 1;    // 简单情景

        return n*f(n-1);  // 相同重复逻辑，缩小问题的规模
    }

    /**
     * 1+2+3.....+n
     * @param n
     * @return
     */
    public static int add(int n){
        if(n==1)
            return 1;
        return  n+add(n-1);
    }

    /**
     * 斐波纳契数列，又称黄金分割数列，指的是这样一个数列：1、1、2、3、5、8、13、21、……
     * 在数学上，斐波纳契数列以如下被以递归的方法定义：F0=0，F1=1，Fn=F(n-1)+F(n-2)（n>=2，n∈N*）。
     *
     * 两种递归解法：经典解法和优化解法
     * 两种非递归解法：递推法和数组法
     *
     * 斐波那契数列如下：
     *
     *  1,1,2,3,5,8,13,21,34,...
     *
     * *那么，计算fib(5)时，需要计算1次fib(4),2次fib(3),3次fib(2)，调用了2次fib(1)*，即：
     *
     *  fib(5) = fib(4) + fib(3)
     *
     *  fib(4) = fib(3) + fib(2) ；fib(3) = fib(2) + fib(1)
     *
     *  fib(3) = fib(2) + fib(1)
     *
     * 这里面包含了许多重复计算，而实际上我们只需计算fib(4)、fib(3)、fib(2)和fib(1)各一次即可，
     * 后面的optimizeFibonacci函数进行了优化，使时间复杂度降到了O(n).
     */

    public static int fibonacci(int n) {
        if (n == 1 || n == 2) {     // 递归终止条件
            return 1;       // 简单情景
        }
        return fibonacci(n - 1) + fibonacci(n - 2); // 相同重复逻辑，缩小问题的规模
    }

    /**
     * @description 递归获取杨辉三角指定行、列(从0开始)的值
     * @x  指定行
     * @y  指定列
     *
     * Title: 杨辉三角形又称Pascal三角形，它的第i+1行是(a+b)i的展开式的系数。
     * 它的一个重要性质是：三角形中的每个数字等于它两肩上的数字相加。
     *
     * 例如，下面给出了杨辉三角形的前4行：
     *    1
     *   1 1
     *  1 2 1
     * 1 3 3 1
     *
     */

    public static int getValue(int x, int y) {
        if(y <= x && y >= 0){
            if(y == 0 || x == y){   // 递归终止条件
                return 1;
            }else{
                // 递归调用，缩小问题的规模
                return getValue(x-1, y-1) + getValue(x-1, y);
            }
        }
        return -1;
    }

}
