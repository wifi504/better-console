package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompTable;
import com.lhl.bconsole.CompText;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/19_10:51
 */
public class Test03 {
    public static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        CompTable table1 = new CompTable();
        CompText text = new CompText("$v$").ref(v -> v.bind(i++));
        table1.setCell("当前表格渲染次数").setCell(text)
                .setCell(2, 1, "当前系统时间戳")
                .setCell(new CompText("$v$").ref(v -> v.bind(System.currentTimeMillis())));
        table1.appendRows(3).drawLine(1).setAlignment(1, CompTable.RIGHT)
                .setAlignment(2, CompTable.RIGHT);
        BConsole screen = BConsole.getScreen();
        screen.reg(table1);
        screen.turnON();
        Thread.sleep(60 * 1000);
        screen.turnOFF();
    }
}
