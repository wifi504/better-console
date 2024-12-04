package com.lhl.test2;

import com.lhl.bconsole2.Render;
import com.lhl.bconsole2.Screen;
import com.lhl.bconsole2.component.ComponentFactory;
import com.lhl.bconsole2.component.Text;
import com.lhl.bconsole2.component.View;
import com.lhl.bconsole2.route.Router;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_2:34
 */
public class Test06 {

    static int i = 0;

    public static void main(String[] args) {
        ComponentFactory cf = new ComponentFactory();
        Text text = cf.newDivText("组件被渲染了 $v$ 次！").ref(v -> v.bind(i));
        text.onBeforeRender(() -> i++);

        View view = cf.newView();
        view.reg(text).reg(text).reg(text).reg(text);

        Screen.getScreen().reg(view).turnON();

        // Router.root.navigate("a");
    }
}
