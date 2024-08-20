package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompBar;
import com.lhl.bconsole.CompView;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/20_16:38
 */
public class TestBar {

    public static double i = 7;

    public static void main(String[] args) throws InterruptedException {
        CompBar onlyBarToRight = new CompBar().setFace(CompBar.LEFT).setAllTask(89).ref(v -> v.bind(i));
        CompBar infoBarToRight = new CompBar().setAllTask(128).showPercent(2).ref(v -> v.bind(i));
        CompBar goodInfoBarToRight = new CompBar().setAllTask(188).setLength(38)
                .showPercent(2).setUnit("项").ref(v -> v.bind(i));
        CompBar borderBarToLeft = new CompBar().setAllTask(60).setFace(CompBar.LEFT)
                .setShowBorder(true).ref(v -> v.bind(i)).showPercent(0).setBold(true);
        CompBar bigBorderBarToTop = new CompBar().setAllTask(38).setFace(CompBar.TOP)
                .setLength(5).setShowBorder(true).ref(v -> v.bind(i)).setUnit("项");

        CompView view = new CompView();
        view.reg(onlyBarToRight).wrap()
                .reg(infoBarToRight).wrap()
                .reg(goodInfoBarToRight).wrap()
                .reg(borderBarToLeft).wrap()
                .reg(bigBorderBarToTop);
        BConsole.getScreen().reg(view).beforeEach(() -> i += 3).turnON();
        Thread.sleep(60 * 1000);
        BConsole.getScreen().turnOFF();
    }
}
