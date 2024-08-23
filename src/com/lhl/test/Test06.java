package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompText;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/23_18:12
 */
public class Test06 {
    public static void main(String[] args) {
        CompText c1 = new CompText("c1");
        CompText c2 = new CompText("c2");
        c1.reg(c2);
        c2.reg(c1);
        BConsole.getScreen().saveSystemOut().reg(c1).turnON();
    }
}
