package com.lhl.bconsole2.component.preset;

import com.lhl.bconsole2.component.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 屏幕时钟
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_1:52
 */
class ClockView extends View {

    static Date date = new Date(); // 每一刻的日期时间，伴随着组件被更新而重复实例化

    // 构造传参标题
    protected ClockView(Component<?> title) {
        // 时间格式化器
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        // 日期格式化器
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy年M月d日 EEEE");
        // 艺术字动态时间
        ArtText timeText = cf.newArtText("$v$").ref(v -> v.bind(sdfTime.format(date)));
        // 动态日期
        Text dateText = cf.newDivText("$v$").ref(v -> v.bind(sdfDate.format(date)));
        // 钟的 Block 链式调用配置样式
        Block block = cf.newBoldBlock(" \n$v$\n \n$v$\n \n$v$")
                .ref(v -> v.bind(title, timeText, dateText)) // 设置标题、时间、日期
                .setAlignment(Block.CENTER) // 设置居中对齐
                .setWidth(80) // 块宽度 80 字符
                .setHeight(12) // 块高度 12 行
                .onBeforeRender(() -> date = new Date()); // 生命周期回调，刷新前获取最新时间
        reg(block);
    }
}
