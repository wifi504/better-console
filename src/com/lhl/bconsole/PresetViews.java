package com.lhl.bconsole;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 预设视图
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/22_10:18
 */
public class PresetViews {


    static Date clockViewDate; // clockView 的动态变量

    public static CompView getClockView() {
        CompView view = new CompView();
        // 时间格式化
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        // 日期格式化
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy年M月d日 EEEE");
        // 动态时间
        CompArtText artText = new CompArtText("$v$").ref(v -> v.bind(sdfTime.format(clockViewDate)));
        // 动态日期
        CompText compText = new CompText("$v$").ref(v -> v.bind(sdfDate.format(clockViewDate)));
        // 钟的块儿
        CompBlock block = new CompBlock(" \n屏 幕 时 钟\n \n$v$\n \n$v$")  // 设置标题、时间、日期
                .ref(v -> v.bind(artText, compText))  // 绑定时间、日期
                .setBorder(CompBlock.BOLD)  // 设置边框样式为粗
                .setAlignment(CompBlock.CENTER) // 内容居中对齐
                .setWidth(80)  // 设置宽度为80
                .setHeight(12) // 设置高度为12
                .beforeEach(() -> clockViewDate = new Date()); // 使用组件局部前置守卫更新时间
        view.reg(block);
        return view;
    }
}
