package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;
import com.lhl.bconsole.PresetViews;

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
        // 使用 CompText 存储带变量的文本内容并且绑定变量
        CompText text = new CompText("我叫$v$, 今年$v$岁了！\n我今天已经玩儿了$v$把王者了！\n$v$")
                .ref(v -> v.bind(name, age, game, msg));
        // 让屏幕控制器显示 text，然后要保存输出，并且前后守卫都要有动作，打开屏幕
        BConsole.getScreen().reg(text).saveSystemOut("game-log.txt").beforeEach(() -> {
            reallyGame = game;
            game = 0;
        }).afterEach(() -> game = reallyGame).turnON();
        // 三秒后张老三开始玩游戏
        Thread.sleep(3 * 1000);
        new Thread(() -> {
            while (allowPlayGames) {
                game++;
                System.out.println("今天的第" + game + "把游戏，" + (Math.random() < 0.2 ? "赢了！" : "输了..."));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignore) {
                }
            }
        }).start();
        // 我们现在已经不能发现张老三偷偷玩游戏了！
        BConsole.getScreen().waitUserInterrupt();
        allowPlayGames = false;
        Thread.sleep(3 * 1000);
        BConsole.getScreen().turnOFF();
    }
}