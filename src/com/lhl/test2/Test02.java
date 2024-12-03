package com.lhl.test2;

import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.route.RouteGroupNode;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_18:45
 */
public class Test02 {
    public static void main(String[] args) {
        ComponentFactory cf = new ComponentFactory();
        RouteGroupNode childNode = new RouteGroupNode("childNode");
        childNode.addChildRoute("blue", cf.newDivText("blue-div"));

        System.out.println(Router.root.render());
        Router.root.addChildRoute("home", cf.newDivText("home组件"))
                .addChildRoute(childNode);
        System.out.println(Router.root.render());
        Router.root.navigate("apple");
        System.out.println(Router.root.render());
        Router.root.navigate("home/bdddd");
        System.out.println(Router.root.render());
        Router.root.navigate("childNode");
        System.out.println(Router.root.render());
        Router.root.navigate("home");
        System.out.println(Router.root.render());
        childNode.navigate("/childN1ode/blue");
        System.out.println(Router.root.render());

    }
}
