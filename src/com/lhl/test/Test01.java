package com.lhl.test;

import java.util.LinkedList;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/18_14:09
 */
public class Test01 {
    public static void main(String[] args) {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(0);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        System.out.println(integers);
        integers.add(2, 114514);
        System.out.println(integers);
        integers.remove(2);
        System.out.println(integers);
//        integers.add(10, 10);



    }
}
