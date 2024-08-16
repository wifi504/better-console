package com.lhl.bconsole;

import java.util.HashMap;

/**
 * 组件路由
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_16:03
 */
public class Router {

    private HashMap<String, Comp> routerMap = null;  // 路由列表

    private String path = ""; // 当前路由

    /**
     * 添加路由
     *
     * @param path 路由地址
     * @param comp 渲染视图
     * @return 可以链式调用
     */
    public Router put(String path, Comp comp) {
        if (routerMap == null) {
            routerMap = new HashMap<>();
        }
        routerMap.put(path, comp);
        return this;
    }


    /**
     * 移除路由
     *
     * @param path 路由地址
     * @return 可以链式调用
     */
    public Router remove(String path) {
        routerMap.remove(path);
        return this;
    }

    /**
     * 路由跳转
     *
     * @param path 目标地址
     * @return 可以链式调用
     */
    public Router navigate(String path) {
        this.path = path;
        return this;
    }


    protected String getPath() {
        return this.path;
    }

    protected Comp getComp(String path) {
        return routerMap.get(path);
    }
}
