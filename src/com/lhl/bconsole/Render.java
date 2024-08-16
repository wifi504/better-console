package com.lhl.bconsole;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制台渲染线程
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_14:55
 */
class Render implements Runnable {

    // 启用渲染
    protected static boolean enabled = true;

    // 渲染体
    protected static Router router = null;

    // 渲染守卫
    protected static Refresh before = null; // 前置守卫
    protected static Refresh after = null; // 后置守卫

    // 渲染缓存
    private static StringBuilder cache = new StringBuilder();

    // 更新周期
    protected static long sleepMillisecond = Long.MAX_VALUE;

    // 劫持系统标准输出流
    private static PrintStream renderOut = null;


    /**
     * 渲染控制台内容
     */
    private static void refresh() {
        // 前置守卫回调
        if (before != null) {
            before.ref();
        }

        // 渲染新内容
        cache.delete(0, cache.length());
        cache.append(router.getComp(router.getPath()));

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

    /*
     * 时间戳转换
     * */
    public static String formatTimestamp(long l) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(l);
        return simpleDateFormat.format(date);
    }


    @Override
    public void run() {
// 劫持标准输出流
        renderOut = System.out;
        PrintStream ps;
        try {
            ps = new PrintStream("system-log-" +
                    formatTimestamp(System.currentTimeMillis()) + ".txt");
            System.setOut(ps);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        ps.flush();
        ps.close();
    }
}
