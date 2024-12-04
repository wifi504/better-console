package com.lhl.bconsole2;

import com.lhl.bconsole2.callback.Callback;
import com.lhl.bconsole2.component.Component;
import com.lhl.bconsole2.route.Router;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;

/**
 * 控制台渲染线程
 *
 * @author WIFI连接超时
 * @version 2.0
 * Create Time 2024/12/4_21:46
 */
public class Render implements Runnable {

    // 启用渲染
    protected static boolean enabled = false;

    // 更新周期
    protected static long sleepMillisecond = 1000;

    // 渲染缓存
    private static final StringBuilder cache = new StringBuilder();

    // 已渲染过的组件
    public static final HashSet<Component<?>> rendered = new HashSet<>();

    // 渲染线程生命周期钩子
    protected static Callback beforeRenderCallback = null;
    protected static Callback afterRenderCallback = null;

    // 劫持系统标准输出流
    private static PrintStream renderOut = null;

    // 一个什么都不做的输出流
    private static final PrintStream pointlessPrintStream =
            new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    // 什么都不做
                }
            });

    // 执行一次渲染
    private static void doOnceRender() {
        // 渲染前回调
        if (beforeRenderCallback != null) {
            beforeRenderCallback.call();
        }
        // 渲染组件内容
        cache.setLength(0);
        cache.append(Router.root.render()).append("\n")
                .append(Screen.isTyping ? Screen.inputTips : "")
                .append(Screen.isWaiting ? Screen.waitTips : "");
        rendered.forEach(Component::setDirty);
        rendered.clear();
        // 清空控制台内容
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception ignored) {
        }
        // 执行渲染
        renderOut.print(cache);
        // 渲染后回调
        if (afterRenderCallback != null) {
            afterRenderCallback.call();
        }
    }

    @Override
    public void run() {
        // 劫持标准输出流
        renderOut = System.out;
        PrintStream renderErr = System.err;
        // 如果需要保存系统输出流，则指向文件，否则指向一个空
        PrintStream fps = Screen.getScreen().getFilePrintStream();
        if (fps != null) {
            System.setOut(fps);
            System.setErr(fps);
        } else {
            System.setOut(pointlessPrintStream);
            System.setErr(pointlessPrintStream);
        }
        // 启动渲染
        while (enabled) {
            doOnceRender();
            try {
                Thread.sleep(sleepMillisecond);
            } catch (InterruptedException ignore) {
            }
        }
        doOnceRender();
        // 释放标准输出流
        System.setOut(renderOut);
        System.setErr(renderErr);
    }
}
