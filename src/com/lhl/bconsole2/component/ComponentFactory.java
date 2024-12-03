package com.lhl.bconsole2.component;

/**
 * 组件工厂
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_1:15
 */
public final class ComponentFactory {

    /**
     * 根据 文本内容 实例化行级文本组件
     *
     * @param text 文本内容
     * @return 文本组件
     */
    public Text newSpanText(String text) {
        return new Text(text).setLayout(Component.INLINE);
    }

    /**
     * 根据 文本内容 实例化块级文本组件
     *
     * @param text 文本内容
     * @return 文本组件
     */
    public Text newDivText(String text) {
        return new Text(text).setLayout(Component.BLOCK);
    }

}
