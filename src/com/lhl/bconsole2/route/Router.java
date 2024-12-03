package com.lhl.bconsole2.route;

/**
 * 页面路由（支持路由嵌套）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_15:49
 */
public final class Router {

    // 根节点
    public static final RouteGroupNode root = new RouteGroupNode("");

    // 本类静态调用
    private Router() {
    }


    /**
     * 获取指定节点的绝对路径
     *
     * @param routeNode 指定节点
     * @return 绝对路径
     */
    public static String getNodeAbsolutePath(RouteNode routeNode) {
        if (routeNode.superNode == null) return routeNode.path;
        return getNodeAbsolutePath(routeNode.superNode) + "/" + routeNode.path;
    }
}
