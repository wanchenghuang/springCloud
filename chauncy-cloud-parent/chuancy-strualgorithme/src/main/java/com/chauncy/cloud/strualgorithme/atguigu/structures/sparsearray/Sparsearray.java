package com.chauncy.cloud.strualgorithme.atguigu.structures.sparsearray;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 稀疏数组
 * @Author cheng
 * @Version : V0.1
 * @since 2021/1/12 22:10
 *
 * 将二维数组 转 稀疏数组的思
 * 1. 先遍历二维数组 得到有效数据的个数sum
 * 2. 根据sum就可以创建对应的稀疏数组sparseArray int[sum+1][3]
 * 3.将二维数组的有效数据存入到稀疏数组中
 *
 * 将稀疏数组还原为二维数组的思路
 *
 * 1、先读取稀疏数组的第一行，根据第一行的数据创建原始二维数组
 * 2、在读取稀疏数组的后几行的数据，并赋给原始的二位数组即可
 */
public class Sparsearray {

    public static void main(String[] args) {

        //创建一个原始的二维数组 11 * 11
        int chessArr[][] = new int[11][11];
        chessArr[1][2] = 1;
        chessArr[2][3] = 2;
        chessArr[4][5] = 2;

        //输出原始二维数组
        System.out.println("原始的二位数组为：");
        //行
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将二维数组 转 稀疏数组的思想
        // 1. 先遍历二维数组 得到非0数据的个数
        AtomicInteger num = new AtomicInteger();
        Arrays.stream(chessArr).forEach(i ->{
            Arrays.stream(i).forEach(data ->{
                if (data != 0){
                    num.getAndIncrement();
                }
            });
        });

        //创建稀疏数组
        int sum = num.get();
        int sparseArr[][] = new int[sum+1][3];
        //设置第一行数据，行 列 有效值
        // 给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到 sparseArr中
        int count = 0; //count 用于记录是第几个非0数据

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0 ){
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }

        //输出稀疏数组的形式
        System.out.println();
        System.out.println("得到稀疏数组为~~~~");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        System.out.println();

        //将稀疏数组 --》 恢复成 原始的二维数组
		/*
		 *  1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
			2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
		 */

        //1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int chessArr2[][] = new int[sparseArr[0][0]][sparseArr[0][1]];
        ArrayList<Object> objects = Lists.newArrayList();

        //2. 在读取稀疏数组后几行的数据(从第二行开始)，并赋给 原始的二维数组 即可

        for(int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");

        Arrays.stream(chessArr).forEach(i ->{
            Arrays.stream(i).forEach(data -> System.out.printf("%d\t", data));
            System.out.println();
        });
    }
}
