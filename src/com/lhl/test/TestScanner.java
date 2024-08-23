package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;

import java.util.Scanner;


/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/23_13:50
 */
public class TestScanner {

    static String input;

    public static void main(String[] args) throws InterruptedException {
//        CompText text = new CompText("type=$v$").ref(v -> v.bind(input));
//        BConsole.getScreen().reg(text).turnON();
//        Thread.sleep(3000);
////        input = BConsole.getScreen().getUserInput();
//        BConsole.getScreen().waitUserInterrupt();
//        Thread.sleep(6000);
//        BConsole.getScreen().turnOFF();
        System.out.print("请输入年龄：");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        System.out.println("你的年龄是" + input + "岁！");
    }
}
