package com.lhl.test2;

import com.lhl.bconsole2.component.View;
import com.lhl.bconsole2.component.preset.PresetViewsFactory;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/9_2:58
 */
public class Test07 {
    public static void main(String[] args) {
        PresetViewsFactory pvf = new PresetViewsFactory();
        View view = pvf.newErrorView();
        System.out.println(view);
    }
}
