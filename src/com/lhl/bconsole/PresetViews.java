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

    // -----------------------------屏幕时钟------------------------------- //

    static Date clockViewDate; // clockView 的动态变量

    /**
     * 屏幕时钟
     *
     * @return 获取屏幕时钟
     */
    public static CompView getClockView() {
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
        return new CompView().reg(block);
    }

    // -----------------------------系统输出------------------------------- //

    /**
     * 系统输出
     *
     * @param fullShow 要显示全部的系统输出吗
     * @return 获取系统输出
     */
    public static CompView getStdOutView(boolean fullShow) {
        CompBlock block = new CompBlock("$v$")
                .ref(v -> v.bind(Render.stdOut.toString().replace("\r", "") + "\n")) // 绑定系统输出流
                .setWidth(80).setHeight(fullShow ? -1 : 12) // 设置宽高
                .showLineNumber(true)  // 要显示行号
                .alwaysShowEnd(!fullShow); // 永远显示最后一页
        return new CompView().reg(block);
    }

    /**
     * 系统输出，默认只显示最后
     *
     * @return 获取系统输出
     */
    public static CompView getStdOutView() {
        return getStdOutView(false);
    }

    // -----------------------------系统状态------------------------------- //

    static String cpuUsage = ""; // 处理器占用百分比

    /**
     * 显示当前系统状态
     *
     * @return 获取系统状态
     */
    public static CompView getSysInfoView() {
        // 信息表
        CompTable table = new CompTable();

        // 处理器信息
        CompText processors = new CompText("系统可用处理器数量：$v$\n \n当 前 CPU 占 用\n \n").ref(v -> v.bind(SystemInfoServe.getProcessors()));
        CompArtText compArtText = new CompArtText("$v$").ref(v -> v.bind(cpuUsage))
                .beforeEach(() -> cpuUsage = String.format("%.2f%%", SystemInfoServe.getCpuUsage()));
        processors.reg(compArtText);
        CompBlock processorsBlock = new CompBlock(processors).setAlignment(CompBlock.CENTER).setBorder(CompBlock.NONE);
        processorsBlock.setWidth(80);

        // 内存信息
        CompText memory = new CompText("JVM 内存占用：$v$ MB")
                .ref(v -> v.bind(SystemInfoServe.getTotalMemory()));

        // 属性信息（内存为其子组件）
        CompText prop = new CompText("$v$\n").ref(v -> v.bind(SystemInfoServe.getProperties())).reg(memory);
        CompText propText = new CompText("$v$").ref(v -> v.bind(" " + prop.toString().replace("\n", "\n ")));

        table.setCell(processorsBlock).setCell(propText).drawLine(1);

        return new CompView().reg(table);
    }
}
