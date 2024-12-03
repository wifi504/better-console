package com.lhl.bconsole2.route;

/**
 * 路由节点抽象类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_16:03
 */
public abstract class RouteNode {

    // 该节点的父节点，为空说明是根节点
    protected RouteNode superNode;

    // 节点的路径名
    protected String path;

    // 构造方法
    protected RouteNode(String path) {
        // 避免路径中存在斜杠导致多级路径匹配出错
        this.path = path.replaceAll("/", "");
    }


    /**
     * 获取此节点的绝对路径
     *
     * @return 绝对路径
     */
    public String getAbsolutePath() {
        return Router.getNodeAbsolutePath(this);
    }

    // 渲染方法
    protected abstract String render();
}
