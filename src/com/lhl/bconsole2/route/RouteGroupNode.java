package com.lhl.bconsole2.route;

import com.lhl.bconsole2.component.Component;
import com.lhl.bconsole2.component.ComponentFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 路由节点（路由树的中间节点）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_16:20
 */
public class RouteGroupNode extends RouteNode {

    // 此路由节点存储的子路由
    private final HashMap<String, RouteNode> childRoutes = new HashMap<>();

    // 此路由的导航路径
    private String currentNavigatePath = "";

    // 构造方法
    public RouteGroupNode(String path) {
        super(path);
    }


    /**
     * 在当前路由节点增加一个路由节点
     *
     * @param childRoute 子节点
     * @return 可以链式调用
     */
    public RouteGroupNode addChildRoute(RouteNode childRoute) {
        // 如果是进来的第一个子节点，那么自动导航到这个页面
        if (childRoutes.isEmpty()) navigateChildRoute(childRoute.path);
        childRoute.superNode = this;
        childRoutes.put(childRoute.path, childRoute);
        return this;
    }


    /**
     * 在当前路由节点增加一个组件作为一个叶子节点
     *
     * @param path      组件路径名
     * @param component 组件
     * @return 可以链式调用
     */
    public RouteGroupNode addChildRoute(String path, Component<?> component) {
        addChildRoute(new ComponentNode(path, component));
        return this;
    }


    /**
     * 根据单级路径取出子节点
     *
     * @param path 单级路径
     * @return 子节点
     */
    public RouteNode getChildRoute(String path) {
        return childRoutes.get(path);
    }


    /**
     * 根据单级路径删除指定子节点
     *
     * @param path 单级路径
     * @return 可以链式调用
     */
    public RouteGroupNode removeChildRoute(String path) {
        childRoutes.remove(path);
        return this;
    }


    /**
     * 根据单级路径导航当前节点的子节点
     *
     * @param path 单级路径
     * @return 可以链式调用
     */
    public RouteGroupNode navigateChildRoute(String path) {
        this.currentNavigatePath = path;
        return this;
    }


    /**
     * 根据路径自动匹配导航对应节点 <hr />
     * 如果是单级路径，效果等同于 navigateChildRoute() <br />
     * 如果是多级路径，且不以 "/" 开头，将视作相对路径从当前节点开始导航，这个导航不一定立即可见 <br />
     * 如果以 "/" 开头，将视作绝对路径，从根节点开始导航，这个导航必将立即可见 <br />
     * <br />
     * 关于导航效果的可见性，如果当前路由地址并没有渲染发起导航的节点，那对应节点的导航路径
     * 将被修改，且在下一次该节点被渲染时才会生效，如果是绝对路径导航，那么必然从根节点出发，
     * 相应的会改变当前的整个导航地址，自然就会即时生效
     *
     * @param path 路径
     * @return 可以链式调用
     */
    public RouteGroupNode navigate(String path) {
        if (path.charAt(0) != '/') {
            // 相对路径匹配
            int i = path.indexOf('/');
            if (i != -1) {
                // 存在多级路径，导航单级，继续匹配
                String thisPath = path.substring(0, i);
                String nextPath = path.substring(i + 1);
                navigateChildRoute(thisPath);
                RouteNode childRoute = getChildRoute(thisPath);
                if (childRoute instanceof RouteGroupNode) {
                    // 继续匹配
                    ((RouteGroupNode) childRoute).navigate(nextPath);
                } else {
                    // 拿到了个组件，直接强行用非法单级路径抛错
                    navigateChildRoute(path);
                }
            } else {
                // 达到叶子节点，导航
                navigateChildRoute(path);
            }
        } else {
            // 绝对路径匹配
            Router.root.navigate(path.substring(1));
        }
        return this;
    }


    /**
     * 获取此路由的当前导航路径
     *
     * @return 当前导航路径
     */
    public String getCurrentNavigatePath() {
        return this.currentNavigatePath;
    }

    @Override
    public String render() {
        try {
            return childRoutes.get(getCurrentNavigatePath()).render();
        } catch (Exception e) {
            // TODO 跳转到路由错误页
            ArrayList<String> paths = new ArrayList<>();
            traverseAllRoutePath(paths, Router.root);
            StringBuilder sb = new StringBuilder();
            paths.forEach(s -> sb.append(s).append("\n"));
            return new ComponentFactory()
                    .newDivText("""
                            路由导航错误地指向了一个不存在的路由地址！
                            导航目标地址：$v$
                            当前全部可用地址如下：
                            $v$""")
                    .ref(v -> v.bind(
                            getAbsolutePath() + "/" + getCurrentNavigatePath(),
                            sb))
                    .toString();
        }
    }


    /**
     * 遍历指定节点下全部路由地址
     *
     * @param paths     存放路由地址的 ArrayList < String >
     * @param routeNode 指定起始遍历节点
     */
    public void traverseAllRoutePath(ArrayList<String> paths, RouteNode routeNode) {
        if (routeNode instanceof ComponentNode) {
            paths.add(routeNode.getAbsolutePath());
            return;
        }
        RouteGroupNode node = (RouteGroupNode) routeNode;
        node.childRoutes.forEach((s, childNode) -> traverseAllRoutePath(paths, childNode));
    }
}
