package com.lhl.test2;

import com.lhl.bconsole2.Screen;
import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.component.Hr;
import com.lhl.bconsole2.component.TableSymbol;
import com.lhl.bconsole2.component.Text;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/4_22:53
 */
public class Test03 {
    public static void main(String[] args) throws InterruptedException {
        ComponentFactory cf = new ComponentFactory();
        Text text = cf.newDivText("这是一个组件");
        Text home = cf.newDivText("这是HOME");

        Router.root.addChildRoute("home", home);

        Screen.getScreen().reg(text).turnON();

        Thread.sleep(3000);

        Router.root.navigate("/home");

        Thread.sleep(3000);

        Router.root.addChildRoute("h/r", cf.newBoldHr(10));
        Router.root.navigate("hr");
    }
}
