package com.lhl.test;

import com.lhl.bconsole.*;

import java.io.File;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/19_16:21
 */
public class Test05 {

    public static int i = 1;

    public static void main(String[] args) throws InterruptedException {
        CompText dongtai = new CompText("这是一个动态的块儿，\n现在渲染了$v$次").ref(v -> v.bind(i++));
        CompText longText = new CompText("""
                I0密集型
                涉及到网络调用或磁盘IO、等待IO完成时无需CPU的场景(IOBound)，
                线程池大小取决于流量和任务处理耗时。吞吐量依赖线程数的同步调
                用场景，核心线程数可通过公式估算：每秒提交的任务／每秒能处理
                的任务数。异步调用的场景，建议线程数等于2倍的进程可用CPU，比
                如 NettyEventLoop，异步IO。举个例子，若任务平均处理耗时20mS，
                那么单个线程每秒能处理50个（1000／20)，每秒提交200个任务则同时
                需要4个线程来处理。考虑到流量突发或长尾处理耗时，一般会额外冗余几个线程。
                除了增加线程数之外，也可选择合适的阻塞队列来缓冲流量或抖动。IO密集型任务，
                最大线程数既可以和核心线程数一致，也可以基于突发流量来估算。
                另外，线程不建议过多，现有节点处理不过来，可适当扩容，
                也可尝试 Reactive Stream 或者异步IO。
                """);
        CompBlock block = new CompBlock(longText).setWidth(40)
                .setAlignment(CompBlock.CENTER).setBorder(CompBlock.NORMAL).showLineNumber(true);
        CompBlock compBlock = new CompBlock(dongtai).setAlignment(CompBlock.RIGHT);
        CompTable table = new CompTable();
        table.setCell(dongtai).setCell(2, 1, compBlock);
        table.drawNoneBorder(false);
        table.drawFullBorder(true);
        CompView view = new CompView();
        view.reg(table).reg(new CompText("\n")).reg(block);
        BetterConsole screen = BetterConsole.getScreen();
        screen.reg(view);
        screen.saveSystemOut(new File("log.txt"));
        screen.turnON();
        Thread.sleep(5000);
        screen.turnOFF();
    }
}
