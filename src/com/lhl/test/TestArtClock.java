package com.lhl.test;

import com.lhl.bconsole.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/21_16:36
 */
public class TestArtClock {

    public static String sdfNow;

    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        CompText compText = new CompText("$v$").ref(v -> v.bind(sdfNow));
        CompArtText artText = new CompArtText("$v$").ref(v -> v.bind(sdfNow));

        CompBlock compBlock = new CompBlock(" \n屏 幕 时 钟\n \n$v$")
                .setBorder(CompBlock.BOLD).setHeight(12).setWidth(80)
                .setAlignment(CompBlock.CENTER).ref(v -> v.bind(artText));

        CompTable table = new CompTable().setCell(compBlock).setCell(compBlock).setCell(compBlock)
                .setCell(2, 1, compBlock).setCell(compBlock).setCell(compBlock)
                .setCell(3, 1, compText).setCell(compText).setCell(compText)
                .drawFullBorder(true).drawNoneBorder(true);

        BConsole.getScreen().reg(table).beforeEach(() -> sdfNow = sdf.format(new Date())).turnON();
//
//        Thread.sleep(60 * 1000);
//
//        BConsole.getScreen().turnOFF();
    }
}
