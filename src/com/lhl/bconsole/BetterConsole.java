package com.lhl.bconsole;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制台
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_14:50
 */
public class BetterConsole {

    private static BetterConsole bc = null; // 声明单例对象
    private static Thread renderThread = null; // 渲染线程
    private PrintStream ps = null; // 重定向系统输出

    /**
     * 获取单例的屏幕对象<br /><br />
     * 调用对象的 turnON()、turnOFF() 方法以启用和关闭屏幕输出
     *
     * @return BetterConsole 实例
     */
    public static BetterConsole getScreen() {
        if (bc != null) {
            return bc;
        }
        bc = new BetterConsole();
        renderThread = new Thread(new Render());
        renderThread.setName("Console-Screen-Render");
        return bc;
    }

    /**
     * 启动控制台显示屏
     */
    public void turnON() {
        Render.enabled = true;
        renderThread.start();
    }

    /**
     * 停止控制台显示屏
     */
    public void turnOFF() {
        Render.enabled = false;
        doRefresh();
    }

    /**
     * 注册单个组件用作渲染
     *
     * @param c 组件
     * @return 可以链式调用
     */
    public BetterConsole reg(Comp c) {
        Render.router = new Router().put("", c);
        return this;
    }


    /**
     * 注册路由用作渲染
     *
     * @param r 路由
     * @return 可以链式调用
     */
    public BetterConsole reg(Router r) {
        Render.router = r;
        return this;
    }

    /**
     * 立即设置屏幕内容渲染间隔<br /><br />
     * 接收值表示多少毫秒更新一次，默认值为1秒<br />
     * 你可以使用 Long.MAX_VALUE 来禁用屏幕内容自动更新，每次内容更新时手动使用 doRefresh() 进行更新<br /><br />
     * 注意：这是大概的刷新频率，实际速度取决于刷新回调的处理时间
     *
     * @param interval 刷新间隔
     */
    public void setRefInterval(long interval) {
        Render.sleepMillisecond = interval;
        doRefresh();
    }

    /**
     * 设置渲染前置守卫<br /><br />
     * 每次屏幕显示更新前回调
     *
     * @param refresh 需要重写ref()方法
     */
    public void beforeRef(Refresh refresh) {
        Render.before = refresh;
    }

    /**
     * 设置渲染后置守卫<br /><br />
     * 每次屏幕显示更新后回调
     *
     * @param refresh 需要重写ref()方法
     */
    public void afterRef(Refresh refresh) {
        Render.after = refresh;
    }

    /**
     * 手动刷新一次显示
     */
    public void doRefresh() {
        if (renderThread != null) {
            renderThread.interrupt();
        }
    }

    /**
     * 保存系统输出流到文件
     * <hr />
     * 包括 System.out 和 System.err
     *
     * @param savePath 流输出路径，若文件存在则默认追加
     */
    public void saveSystemOut(File savePath) {
        if (Render.enabled) return;
        try {
            FileOutputStream fos = new FileOutputStream(savePath, true);
            ps = new PrintStream(fos);
        } catch (FileNotFoundException e) {
            if (ps != null) {
                ps = null;
            }
            // TODO 系统脚注提示信息
        }
    }

    /**
     * 保存系统输出流到文件
     * <hr />
     * 包括 System.out 和 System.err
     * <p>
     * 无参方法将自动在当前路径生成日志文件
     */
    public void saveSystemOut() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS");
        String format = sdf.format(date);
        File file = new File("system-log " + format + ".txt");
        saveSystemOut(file);
    }

    protected PrintStream getPs() {
        return ps;
    }

    // 构造方法
    private BetterConsole() {
        // 默认一秒更新一次
        setRefInterval(1000);
    }
}
