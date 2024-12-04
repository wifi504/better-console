package com.lhl.test2;

import com.lhl.bconsole2.Screen;
import com.lhl.bconsole2.component.View;
import com.lhl.bconsole2.component.preset.PresetViewsFactory;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_2:15
 */
public class Test05 {
    static String title = "";

    public static void main(String[] args) throws InterruptedException {
        PresetViewsFactory preset = new PresetViewsFactory();
        View clock = preset.newClockView(v -> v.bind(title));
        Router.root.addChildRoute("clock", clock);

        Screen.getScreen().turnON();

        for (int i = 1; i <= 10; i+=1) {
            title = "会变" + "长".repeat(i) + "的时钟标题";
            Thread.sleep(500);
        }
    }
}
