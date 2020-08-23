package com.chauncy.cloud.strualgorithme.algorithme.simple;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Author cheng
 * @create 2020-07-28 20:54
 *
 * 时间复杂度和冒泡排序的一样，都为O(n2)
 *
 * 在插入排序中，需要将取出的数据与其左边的数字进行比较。就跟前面讲的步骤一
 * 样，如果左边的数字更小，就不需要继续比较，本轮操作到此结束，自然也不需要交换
 * 数字的位置。
 */
@Slf4j
public class InsertSort {

    public static void main(String[] args){

        int[] a = {5,3,4,7,2,8,6,9,1};
        insertSort(a);

    }

    public static void insertSort(int[] a){

        for (int i=1;i<a.length;i++){
            int insertVal = a[i];//插入的数
            int index = i-1;//被插入的位置(准备和前一个数比较)
            while(index >=0 && insertVal < a[index]){
                a[index+1] = a[index];//将a[index] 向后移动
                index--;//让index向前移动
            }

            a[index+1]=insertVal;//把插入的数放入合适位置
            log.info("第{}次排序：{}", i,Arrays.toString(a));
        }
        log.info("最终排序:{}",Arrays.toString(a));
    }
}

