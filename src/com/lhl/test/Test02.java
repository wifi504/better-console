package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompHr;
import com.lhl.bconsole.CompTable;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/18_15:08
 */
public class Test02 {
    public static void main(String[] args) throws InterruptedException {
        CompTable compTable1 = new CompTable()
                .setAlignment(1, CompTable.CENTER)
                .setCell(1, 1, "子组件名字")
                .setCell(1, 2, "compTable1")
                .setCell(1, 3, new CompHr())
                .drawLine(1).drawNoneBorder(true);

        CompTable compTable = new CompTable()
                .setCell(1, 1, "姓名")
                .setCell(2, 1, "年龄")
                .drawLine(1)//注意数组越界异常
                .setCell(1, 2, "张三")
                .setCell(2, 2, "18")
                .setCell(2, 5, "第五行数据\n第五行数据的第二行")
                .setCell(1, 6, "第六行数据\n又换行了")
                .setCell(3, 1, compTable1)
                .setCell(3,2, new CompTable().setCell(1,1,"好好好"))
                .setAlignment(1, CompTable.RIGHT)
                .setAlignment(2, CompTable.RIGHT)
                .setAlignment(3, CompTable.LEFT)
                .setAlignment(4, CompTable.RIGHT)
                .setAlignment(5, CompTable.RIGHT)
                .setAlignment(6, CompTable.RIGHT);


//        BetterConsole.getScreen().saveSystemOut(new File("log.txt"));
        BConsole.getScreen().setRefInterval(500);
        BConsole.getScreen().reg(compTable.reg(compTable1)).turnON();
        for (int i = 0; i < 10; i++) {
            Thread.sleep(5000);
            compTable.transpositional(true);
//            Thread.sleep(5000);
//            compTable.drawFullBorder(true);
//            Thread.sleep(3000);
//            compTable.drawFullBorder(false);
        }
        BConsole.getScreen().turnOFF();
    }
}
