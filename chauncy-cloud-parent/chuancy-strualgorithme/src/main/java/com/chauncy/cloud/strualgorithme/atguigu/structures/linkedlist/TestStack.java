package com.chauncy.cloud.strualgorithme.atguigu.structures.linkedlist;

import java.util.Stack;

/**
* @Description TODO
* @Author cheng
* @Version : V0.1
* @since 2021/4/24 16:42
*/
//演示栈Stack的基本使用
public class TestStack {

    public static void main(String[] args) {
        Stack<String> stack = new Stack();
        // 入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        // 出栈
        // smith, tom , jack
        while (stack.size() > 0) {
            System.out.println(stack.pop());//pop就是将栈顶的数据取出
        }
    }

}
