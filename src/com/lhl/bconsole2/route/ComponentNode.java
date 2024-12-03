package com.lhl.bconsole2.route;

import com.lhl.bconsole2.component.Component;

/**
 * 组件节点（路由树的叶子节点）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_15:41
 */
public class ComponentNode extends RouteNode {

    // 组件
    private final Component<?> component;

    // 构造方法
    public ComponentNode(String path, Component<?> component) {
        super(path);
        this.component = component;
    }

    @Override
    public String render() {
        return this.component.toString();
    }
}

