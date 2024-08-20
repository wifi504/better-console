package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/20_0:57
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 准备好你要显示什么
        CompText compText = new CompText("Hello BConsole!");
        // 告诉我你要显示什么
        BConsole screen = BConsole.getScreen();
        screen.reg(compText);
        // 启动输出
        screen.turnON();
        // 模拟你自己的代码逻辑... 浅睡5秒吧
        Thread.sleep(5000);
        // 关闭输出
        screen.turnOFF();
    }
}
