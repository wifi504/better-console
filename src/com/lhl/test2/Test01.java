package com.lhl.test2;

import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.component.Text;
import com.lhl.bconsole2.route.ComponentNode;
import com.lhl.bconsole2.route.RouteGroupNode;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_13:42
 */
public class Test01 {

    static String a;

    public static void main(String[] args) {
        ComponentFactory cf = new ComponentFactory();
        Text homePage = cf.newDivText("This is home page");
        Text apple = cf.newDivText("This is my page, with child page apple");
        Text banana = cf.newDivText("This is my page, with child page banana");

        RouteGroupNode node = new RouteGroupNode("my-page")
                .addChildRoute("apple", apple)
                .addChildRoute("banana", banana);

        Router.root.addChildRoute("home-page", homePage)
                .addChildRoute(node);


        Router.root.navigateChildRoute("my-page");
        System.out.print(Router.root.render());
        node.navigateChildRoute("banana");
        System.out.print(Router.root.render());
        Router.root.navigateChildRoute("home-page");
        System.out.print(Router.root.render());

        System.out.println(Router.getNodeAbsolutePath(node.getChildRoute("apple")));
        Router.root.navigate("a");
        System.out.println(Router.root.render());
    }
}
