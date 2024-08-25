package com.lhl.bconsole;

import java.io.*;

/**
 * 控制台渲染线程
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_14:55
 */
class Render implements Runnable {

    // 启用渲染
    protected static boolean enabled = false;

    // 渲染体
    protected static Router router = new Router().put("", new CompArtText(" \n hello\n B-Console"));

    // 渲染守卫
    protected static Refresh before = null; // 前置守卫
    protected static Refresh after = null; // 后置守卫

    // 更新周期
    protected static long sleepMillisecond = Long.MAX_VALUE;

    // 系统输出流缓存区
    protected static final ByteArrayOutputStream stdOut = new ByteArrayOutputStream();

    // 劫持系统标准输出流
    private static PrintStream renderOut = null;

    // 渲染缓存
    private static final StringBuilder cache = new StringBuilder();

    /**
     * 渲染控制台内容
     */
    private static void refresh() {
        // 前置守卫回调
        if (before != null) {
            before.ref();
        }

        // 渲染新内容
        cache.setLength(0);
        cache.append(router.getComp(router.getPath())) // 渲染路由
                .append("\n")
                .append(BConsole.isTyping ? BConsole.inputTips : "") // 渲染输入提示组件
                .append(BConsole.isWaiting ? BConsole.waitTips : "");

        // 清空控制台
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception ignored) {
        }

        // 输出新内容
        renderOut.println(cache);

        // 后置守卫回调
        if (after != null) {
            after.ref();
        }
    }

    @Override
    public void run() {
        // 劫持标准输出流
        renderOut = System.out;
        TeePrintStream tps;
        PrintStream ps = BConsole.getScreen().getPs();
        if (ps == null) {
            // 不用保存输出流到本地
            tps = new TeePrintStream(stdOut);
        } else {
            // 需要保存输出流到本地
            tps = new TeePrintStream(stdOut, ps);
        }
        System.setOut(tps);
        System.setErr(tps);

        // 开始渲染
        while (enabled) {
            refresh();
            try {
                Thread.sleep(sleepMillisecond);
            } catch (InterruptedException ignore) {
            }
        }
        refresh();

        // 释放标准输出流
        System.setOut(renderOut);
        System.setErr(renderOut);
        tps.flush();
        tps.close();

    }
}
