package com.lhl.test2;

import com.lhl.bconsole2.Screen;
import com.lhl.bconsole2.component.Bar;
import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_1:09
 */
public class Test04 {
    static double d = 0;

    public static void main(String[] args) throws InterruptedException {
        ComponentFactory cf = new ComponentFactory();
        Screen.getScreen().turnON();
        Router.root.addChildRoute("bar", cf.newBar().setFace(Bar.TOP)
                .setLength(5).setAllTask(114).ref(v -> v.bind(d))
                .showPercent(5).setUnit("项"));

        while (d <= 100) {
            d++;
            Thread.sleep(100);
        }

        Router.root.addChildRoute("art", cf.newArtText("hello (123) <?"));
        Router.root.navigate("art");
    }
}
