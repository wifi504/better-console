package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;
import com.lhl.bconsole.CompView;
import com.lhl.bconsole.PresetViews;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/22_18:23
 */
public class TestInput {

    static String userInput = "";
    public static void main(String[] args) throws InterruptedException {
        CompView myView = new CompView();

        CompText text = new CompText("userInput=$v$").ref(v -> v.bind(userInput));

        myView.reg(text).wrap().reg(PresetViews.getClockView());

        BConsole.getScreen().reg(myView).turnON();

        Thread.sleep(2000);

        userInput = BConsole.getScreen().getUserInput();

        Thread.sleep(5000);

        BConsole.getScreen().turnOFF();
    }
}
