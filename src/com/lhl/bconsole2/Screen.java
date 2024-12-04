package com.lhl.bconsole2;

import com.lhl.bconsole2.callback.Callback;
import com.lhl.bconsole2.component.Component;
import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.route.Router;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * 控制台屏幕控制器
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/4_21:45
 */
public class Screen {

    private static Screen screen = null; // 声明单例对象
    private static Thread renderThread = null; // 渲染线程
    private static Scanner scanner = null; // 获取控制台输入
    protected static Component<?> inputTips = null; // 输入提示组件（只有输入期间会显示）
    protected static boolean isTyping = false; // 在输入期间吗
    protected static Component<?> waitTips = null; // 等待提示组件（只有等待期间会显示）
    protected static boolean isWaiting = false; // 在等待期间吗
    private PrintStream filePrintStream = null; // 重定向系统输出
    private static final ComponentFactory cf = new ComponentFactory(); // 组件工厂

    // 构造方法私有化，自行管理单例
    private Screen() {
    }

    /**
     * 获取单例的屏幕对象<br /><br />
     * 调用对象的 turnON()、turnOFF() 方法以启用和关闭屏幕输出
     *
     * @return Screen 实例
     */
    public static Screen getScreen() {
        if (screen != null) {
            return screen;
        }
        screen = new Screen();
        renderThread = new Thread(new Render());
        renderThread.setName("Console-Screen-Render");
        return screen;
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
     * 注册单个组件用作渲染且立即生效
     *
     * @param component 组件
     * @return 可以链式调用
     */
    public Screen reg(Component<?> component) {
        // 随机获得一个路由地址
        String path = UUID.randomUUID().toString();
        Router.root.addChildRoute(path, component);
        Router.root.navigate(path);
        return this;
    }

    /**
     * 立即设置屏幕内容渲染间隔<br /><br />
     * 接收值表示多少毫秒更新一次，默认值为1秒<br />
     * 你可以使用 Long.MAX_VALUE 来禁用屏幕内容自动更新，每次内容更新时手动使用 doRefresh() 进行更新<br /><br />
     * 注意：这是大概的刷新频率，实际速度取决于刷新回调的处理时间
     *
     * @param interval 刷新间隔
     * @return 可以链式调用
     */
    public Screen setRefInterval(long interval) {
        Render.sleepMillisecond = interval;
        doRefresh();
        return this;
    }

    /**
     * 设置渲染线程生命周期钩子<br /><br />
     * 每次屏幕显示更新前回调
     *
     * @param callback 需要重写ref()方法
     * @return 可以链式调用
     */
    public Screen onBeforeRender(Callback callback) {
        Render.beforeRenderCallback = callback;
        return this;
    }

    /**
     * 设置渲染线程生命周期钩子<br /><br />
     * 每次屏幕显示更新后回调
     *
     * @param callback 需要重写ref()方法
     * @return 可以链式调用
     */
    public Screen onAfterRender(Callback callback) {
        Render.afterRenderCallback = callback;
        return this;
    }

    /**
     * 手动刷新一次显示
     *
     * @return 可以链式调用
     */
    public Screen doRefresh() {
        if (renderThread != null) {
            renderThread.interrupt();
        }
        return this;
    }

    /**
     * 获取控制台用户输入
     * 会阻塞当前线程并暂停显示器刷新
     *
     * @param tips 输入提示组件
     * @return scanner.next() 返回值
     */
    public String getUserInput(Component<?> tips) {
        inputTips = tips;
        return getUserInput();
    }

    /**
     * 获取控制台用户输入
     * 会阻塞当前线程并暂停显示器刷新
     * <p>
     * 会沿用上次的输入提示组件，并且会自动更新，如从未设置，则使用默认输入提示组件
     *
     * @return scanner.next() 返回值
     */
    public String getUserInput() {
        // 如果在等待中断，不允许输入
        if (isWaiting) throw new RuntimeException("当前处在等待用户中断期间，不允许输入");
        inputTips = cf.newDivText("请输入内容以继续：（输入结束使用 Enter 键确认）");
        isTyping = true;
        long temp = Render.sleepMillisecond;
        setRefInterval(Long.MAX_VALUE);
        String input = getScanner().nextLine();
        isTyping = false;
        setRefInterval(temp);
        return input;
    }

    /**
     * 等待用户中断
     * 阻塞当前线程，直到用户在控制台敲下 Enter，不会暂停刷新
     *
     * @param tips 等待提示组件
     */
    public void waitUserInterrupt(Component<?> tips) {
        waitTips = tips;
        waitUserInterrupt();
    }

    /**
     * 等待用户中断
     * 阻塞当前线程，直到用户在控制台敲下 Enter，不会暂停刷新
     * <p>
     * 会沿用上次的输入提示组件，并且会自动更新，如从未设置，则使用默认输入提示组件
     */
    public void waitUserInterrupt() {
        // 如果在输入期间，不允许等待
        if (isTyping) throw new RuntimeException("当前处在输入期间，不允许等待用户中断");
        waitTips = cf.newDivText("按下 Enter 键继续...");
        isWaiting = true;
        getScanner().nextLine();
        isWaiting = false;
    }

    /**
     * 保存系统输出流到文件
     * <hr />
     * 包括 System.out 和 System.err
     *
     * @param savePath 流输出路径，若文件存在则默认追加
     * @return 可以链式调用
     */
    public Screen saveSystemOut(String savePath) {
        saveSystemOut(new File(savePath));
        return this;
    }

    /**
     * 保存系统输出流到文件
     * <hr />
     * 包括 System.out 和 System.err
     *
     * @param file 流输出路径，若文件存在则默认追加
     * @return 可以链式调用
     */
    public Screen saveSystemOut(File file) {
        // 如果已经启动渲染了，什么都不做
        if (Render.enabled) return this;
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            filePrintStream = new PrintStream(fos);
        } catch (FileNotFoundException e) {
            if (filePrintStream != null) {
                filePrintStream = null;
            }
        }
        return this;
    }

    /**
     * 保存系统输出流到文件
     * <hr />
     * 包括 System.out 和 System.err
     * <p>
     * 无参方法将自动在当前路径生成日志文件
     *
     * @return 可以链式调用
     */
    public Screen saveSystemOut() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS");
        String format = sdf.format(date);
        File file = new File("system-log " + format + ".txt");
        saveSystemOut(file);
        return this;
    }

    protected PrintStream getFilePrintStream() {
        return filePrintStream;
    }

    private Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
}

