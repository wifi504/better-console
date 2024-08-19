package com.lhl.test;

import com.lhl.bconsole.*;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/19_17:51
 */
public class TestTable01 {
    public static void main(String[] args) throws InterruptedException {
        String text = """
                I0密集型
                涉及到网络调用或磁盘IO、等待IO完成时无需CPU的场景(IOBound)，线程池大小取决于流量和任务处理耗时。
                吞吐量依赖线程数的同步调用场景，核心线程数可通过公式估算：每秒提交的任务／每秒能处理的任务数。
                异步调用的场景，建议线程数等于2倍的进程可用CPU，比如 NettyEventLoop，异步IO。
                举个例子，若任务平均处理耗时20mS，那么单个线程每秒能处理50个（1000／20)，每秒提交200个任务则同时需要4个线程来处理。
                举个例子，若任务平均处理耗时20 ms，那么单个线程每秒能处理50个(1000／20)，每秒提交200个任务则同时需要4个线程来处理。
                考虑到流量突发或长尾处理耗时，一般会额外冗余几个线程。
                除了增加线程数之外，也可选择合适的阻塞队列来缓冲流量或抖动。
                IO密集型任务，最大线程数既可以和核心线程数一致，也可以基于突发流量来估算。另外，线程不建议过多，现有节点处理不过来，可适
                当扩容，也可尝试 Reactive Stream 或者异步IO。
                """;
        String text1 = """
                看起来您的渲染问题依然存在这表明仅仅通过
                调整字符宽度计算可能还不足以解决所有的对齐问题
                我们需要采取进一步的策略特别是关注
                异步调用的场景建议线程数等于2倍的进程
                可用CPU比如 NettyEventLoop异步IO
                rhjgrahgoarhjngoijroi;gjo;aiwjefjm
                渲染过程中的实际字符占位和控制台输出的行为
                这里有几个进一步的步骤可以尝试
                """;
        CompTable table = new CompTable().setCell(text);
        CompBlock block = new CompBlock(text);
        CompView view = new CompView();
        view.reg(table).reg(new CompText("\n")).reg(block);
        BetterConsole screen = BetterConsole.getScreen();
        screen.reg(view);
        screen.turnON();
        Thread.sleep(5000);
        screen.turnOFF();
    }
}
