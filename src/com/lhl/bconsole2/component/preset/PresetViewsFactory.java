package com.lhl.bconsole2.component.preset;

import com.lhl.bconsole2.callback.*;
import com.lhl.bconsole2.component.*;

/**
 * 预设视图组件工厂
 * <hr />
 * 可以从这里获取 better-console 框架预先设计好的视图组件
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_1:50
 */
public class PresetViewsFactory {

    private static final ComponentFactory cf = new ComponentFactory();

    // ---------------- 屏幕时钟 ---------------- //

    /**
     * 实例化一个屏幕时钟视图组件 <br />
     * 使用默认标题 “屏 幕 时 钟”
     *
     * @return View
     */
    public View newClockView() {
        return newClockView(v -> v.bind("屏 幕 时 钟"));
    }

    /**
     * 实例化一个屏幕时钟视图组件 <br />
     * 可以传入一个字符串作为静态标题
     *
     * @param title 标题
     * @return View
     */
    public View newClockView(String title) {
        return newClockView(v -> v.bind(title));
    }

    /**
     * 实例化一个屏幕时钟视图组件 <br />
     * 可以传入一个绑定器去绑定动态标题
     *
     * @param titleCallback 绑定器 v -> v.bind(值)
     * @return View
     */
    public View newClockView(ObjectCallback titleCallback) {
        return new ClockView(cf.newSpanText("$v$").ref(titleCallback));
    }

    // ---------------- 错误页 ---------------- //

    /**
     * 实例化一个空的错误页视图组件
     *
     * @return View
     */
    public View newErrorView() {
        return new ErrorView("<null>", "<null>");
    }

    /**
     * 实例化一个错误页视图组件
     *
     * @param title  错误名称
     * @param detail 错误详细信息
     * @return View
     */
    public View newErrorView(String title, String detail) {
        return new ErrorView(title, detail);
    }
}
