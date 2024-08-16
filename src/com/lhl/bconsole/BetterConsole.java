package com.lhl.bconsole;

import java.io.File;

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
    private File systemOut = null; // 重定向系统输出

    /**
     * 获取单例的屏幕对象<br /><br />
     * 调用对象的 turnON()、turnOFF() 方法以启用和关闭屏幕输出
     *
     * @param c 组件
     * @return BetterConsole 实例
     */
    public static BetterConsole getInstance(Comp c) {
        if (bc != null) {
            return bc;
        }
        bc = getInstance(new Router().put("", c));
        renderThread = new Thread(new Render());
        renderThread.setName("Console-Screen-Render");
        return bc;
    }

    /**
     * 获取单例的屏幕对象<br /><br />
     * 调用对象的 turnON()、turnOFF() 方法以启用和关闭屏幕输出
     *
     * @param r 路由
     * @return BetterConsole 实例
     */
    public static BetterConsole getInstance(Router r) {
        if (bc != null) {
            return bc;
        }
        bc = new BetterConsole(r);
        renderThread = new Thread(new Render());
        renderThread.setName("Console-Screen-Render");
        return bc;
    }

    /**
     * 启动控制台显示屏
     */
    public void turnON() {
        renderThread.start();
    }

    /**
     * 停止控制台显示屏
     */
    public void turnOFF() {
        Render.enabled = false;
        renderThread.interrupt();
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
        if (renderThread != null) {
            renderThread.interrupt();
        }
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
        renderThread.interrupt();
    }

    // 构造方法
    private BetterConsole(Router r) {
        // 默认一秒更新一次
        setRefInterval(1000);
        Render.router = r;
    }
}
