package com.lhl.test;

import com.lhl.bconsole.*;

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


    public static void main(String[] args) throws InterruptedException {

        Comp c1 = new TextComp("测试组件1，携带了一个s1:$v$和s2:$v$\n").ref(v -> v.bind(s1, s2));
        Comp c2 = new TextComp("测试组件2，携带了一个s3:$v$和s4:$v$\n").ref(v -> v.bind(s3, s4));
        Comp c3 = new TextComp("测试组件3，携带了一个s5:$v$和s5:$v$\n").ref(v -> v.bind(s5, s5));
        Comp c4 = new TextComp("测试组件4，携带了一个s3:$v$和s4:$v$\n");


        c2.reg(c4);
        c4.reg(new HrComp());
        c1.reg(c2).reg(c3);
        BetterConsole console = BetterConsole.getInstance(c1);

        console.beforeRef(() -> System.out.println("前置守卫回调了"));
        console.afterRef(() -> System.out.println("后置守卫回调了"));
        console.turnON();


        Thread.sleep(2000);
        s1 = "①";
        s2 = "②";
        s3 = "③";
        s4 = "④";
        s5 = "⑤";
        console.setRefInterval(10);
        Thread.sleep(3000);

        console.turnOFF();

    }
}
