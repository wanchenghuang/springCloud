package com.chauncy.cloud.strualgorithme.algorithme.simple;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @Author cheng
 * @create 2020-07-28 17:47
 *
 *  简单选择排序
 *
 *  选择排序使用了线性查找来寻找最小值，因此在第1 轮中需要比较n - 1 个数字，第
 * 2 轮需要比较n - 2 个数字……到第n - 1 轮的时候就只需比较1 个数字了。因此，总的比
 * 较次数与冒泡排序的相同，都是(n - 1) + (n - 2) + … + 1 ≈ n2/2 次。
 * 每轮中交换数字的次数最多为1 次。如果输入数据就是按从小到大的顺序排列的，
 * 便不需要进行任何交换。选择排序的时间复杂度也和冒泡排序的一样，都为O(n2)。
 *
 *  相对冒泡排序，将交换次数从O(n2)降到O(n)，但是比较次数仍是O(n2)
 *
 *  思路：默认最小值是a[index],从index开始循环查找比较找出最小值最后替换。
 */
@Slf4j
public class SelectionSort {

    public static void main(String[] agrs){
        int[] a = {2,3,5,1,23,6,78,34};
        selectSort(a);
    }

    //从小到大
    public static void selectSort(int[] a){
        int n = a.length-1;
        for (int i=0;i<n;i++){
            int mindex = i;
            for (int j=i+1;j<n;j++){
                if (a[j]<a[i]){
                    mindex = j;
                }
            }
            if (mindex != i){
                int temp;
                temp = a[i];
                a[i] = a[mindex];
                a[mindex] = temp;
            }
            log.info("第{}次排序：{}",i+1, Arrays.toString(a));
        }
        log.info("最终排序:{}",Arrays.toString(a));
    }


}
