package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/20_0:57
 */
public class Test {
    static String name = "张老三";
    static int age = 8;
    static int game = 0;
    static int reallyGame = 0;
    static boolean allowPlayGames = true;
    static String msg = "你不管我我就继续玩！";

    public static void main(String[] args) throws InterruptedException {
        // 拿到屏幕控制器
        BConsole screen = BConsole.getScreen();
        // 使用 CompText 存储带变量的文本内容
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$");
        // 绑定变量
        text.ref(v -> v.bind(name, age, game, msg));
        // 注册文本内容到屏幕
        screen.reg(text);
        // 把显示期间的所有 System.out.println 存下来
        screen.saveSystemOut("game-log.txt");
        // 启用控制台显示屏
        screen.turnON();
        // 等待3秒后
        Thread.sleep(3 * 1000);
        // 张老三委托全局前置守卫来让你每次看他的游戏情况都是0
        screen.beforeEach(() -> {
            reallyGame = game;
            game = 0;
        });
        // 张老三统计自己输赢的工作还得继续，于是委托全局后置守卫在你不看他的时候把游戏情况变回去
        screen.afterEach(() -> {
            game = reallyGame;
        });
        // 张老三去打王者了
        new Thread(() -> {
            // 张老三每200毫秒就能玩儿一把！
            while (allowPlayGames) {
                game++;
                // 把这把游戏的情况打出来
                System.out.println("今天的第" + game + "把游戏，" + (Math.random() < 0.2 ? "赢了！" : "输了..."));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        // 等待用户中断（继续）：waitUserInterrupt();
        BConsole.getScreen().waitUserInterrupt();
        // 张老三，快停下！
        allowPlayGames = false;
        // 再等待3秒后
        Thread.sleep(3 * 1000);
        // 关闭控制台显示屏，退出程序
        screen.turnOFF();
    }
}
