package com.lhl.test;

import com.lhl.bconsole.*;

import java.io.File;
import java.util.Scanner;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_9:51
 */
public class TestMain {

    public static String s1;
    public static String s2;
    public static String s3;
    public static String s4;
    public static String s5;
    public static int i = 0;


    public static void main(String[] args) throws InterruptedException {

        CompText c1 = new CompText("测试组件1，携带了一个s1:$v$和s2:$v$\n").ref(v -> v.bind(s1, s2));
        CompText c2 = new CompText("测试组件2，携带了一个s3:$v$和s4:$v$\n").ref(v -> v.bind(s3, s4));
        CompText c3 = new CompText("测试组件3，携带了一个s5:$v$和s5:$v$\n").ref(v -> v.bind(s5, s5));
        CompText c4 = new CompText("测试组件4，携带了一个s3:$v$和s4:$v$\n");
        CompText c5 = new CompText("测试组件5，携带了一个int：$v$")
                .ref(v -> v.bind(i))
                .beforeEach(() -> i++);


        CompHr compHr = new CompHr();
        /*
         * 1
         *   2
         *       4
         *           3
         *   4
         *       3+
         * */
        c2.reg(c4);
        c4.reg(compHr).reg(c3);
        c1.reg(c2).reg(c4).reg(c5);


        CompView homeView = new CompView();
        homeView.reg(c1);
        BConsole console = BConsole.getScreen().reg(homeView);

        console.beforeEach(() -> System.out.println("全局前置守卫回调了"));
        console.afterEach(() -> System.out.println("全局后置守卫回调了"));

        console.saveSystemOut(new File("log.txt"));
        console.turnON();
        Thread.sleep(5000);
        console.setRefInterval(500);
        s1 = "①";
        Thread.sleep(1000);
        s2 = "②";


        console.setRefInterval(Long.MAX_VALUE);
        s5 = new Scanner(System.in).next();
        console.setRefInterval(500);
        Thread.sleep(1000);
        s3 = "③";
        Thread.sleep(1000);
        s4 = "④";
        Thread.sleep(1000);
        s5 = "⑤";
        Thread.sleep(3000);
        console.turnOFF();


        System.out.println("┌─────┬─────┐");
        System.out.println("│ Col1│ Col2│");
        System.out.println("├─────┼─────┤");
        // 打印数据行
        System.out.println("│ Val1│ Val2│");
        System.out.println("│ Val3│ Val4│");
        // 结束表格
        System.out.println("└─────┴─────┘");
    }
}
