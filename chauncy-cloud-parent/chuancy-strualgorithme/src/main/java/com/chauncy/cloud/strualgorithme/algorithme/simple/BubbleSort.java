package com.chauncy.cloud.strualgorithme.algorithme.simple;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author cheng
 * @create 2020-07-28 16:50
 *
 * 冒泡排序 从小到大
 *
 * （1）比较前后相邻的二个数据，如果前面数据大于后面的数据，就将这二个数据交换。
 * （2）这样对数组的第0个数据到N-1个数据进行一次遍历后，最大的一个数据就“沉”到数组第N-1个位置。
 */
@Slf4j
public class BubbleSort {

    public static void main(String[] args){

        int a[] = {5,9,3,1,2,8,4,7,6};
        bubbleSort(a);
    }

    public static void bubbleSort(int[] a){
        int count = 0;

        for (int i = 0; i < a.length;i++){//表示n次排序过程

            for (int j = 1; j< a.length-i;j++){
                if (a[j-1] > a[j]){//前面的数字大于后面的数字就交换
                    int temp;
                    temp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = temp;
                }
                count++;
            }
            log.info("第 {} 轮排序结果：{}",i+1,a);
        }
        log.info("排序结果:{}",a);
        log.info("循环次数:{}",count);
    }
}
