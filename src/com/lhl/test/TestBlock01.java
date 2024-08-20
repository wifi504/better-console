package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompBlock;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/20_16:19
 */
public class TestBlock01 {

    public static String s = "double";

    public static void main(String[] args) throws InterruptedException {
        CompBlock compBlock = new CompBlock("动态块儿：\n$v$").ref(v -> v.bind(s))
                .beforeEach(() -> s = s.repeat(2));
        BConsole.getScreen().reg(compBlock).turnON();
        Thread.sleep(5000);
        BConsole.getScreen().turnOFF();
    }
}
