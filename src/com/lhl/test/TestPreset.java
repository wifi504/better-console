package com.lhl.test;

import com.lhl.bconsole.*;

/**
 * 这是一个综合测试，完美
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/22_10:49
 */
public class TestPreset {
    public static void main(String[] args) throws InterruptedException {
        // 设置4个页面，分别是欢迎、时钟、系统信息、系统输出
        Router router = new Router().put("home", new CompArtText("\n hello\n b-console").setVerticalSpace(1))
                .put("clock", PresetViews.getClockView())
                .put("sysInfo", PresetViews.getSysInfoView())
                .put("stdOut", PresetViews.getStdOutView()).navigate("home");
        BConsole.getScreen().reg(router).turnON(); // 启用控制台显示器
        Thread.sleep(1000); // 一秒后
        new Thread(() -> { // 开一个线程，让他一直用原来的System.out.println输出信息
            for (char c = 'A'; c <= 'Z'; c++) {
                System.out.println("系统的 System.out.println 打印了字母：" + c);
                try {
                    Thread.sleep((int) (Math.random() * 5000));
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        for (int i = 0; i < 20; i++) { // 每隔5秒切换页面
            Thread.sleep(5000);
            router.navigate("clock");
            Thread.sleep(5000);
            router.navigate("sysInfo");
            Thread.sleep(5000);
            router.navigate("stdOut");
            Thread.sleep(5000);
            router.navigate("home");
        }
        // 切换 80 次就自动关闭屏幕退出吧
        BConsole.getScreen().turnOFF();
    }
}
