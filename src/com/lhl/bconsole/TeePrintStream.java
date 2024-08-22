package com.lhl.bconsole;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 将给定的输出流推送到多个打印流
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/17_1:01
 */
class TeePrintStream extends PrintStream {
    private final PrintStream[] additionalStreams;

    public TeePrintStream(OutputStream out, PrintStream... additionalStreams) {
        super(out);
        this.additionalStreams = additionalStreams;
    }

    @Override
    public void write(int b) {
        super.write(b);
        for (PrintStream stream : additionalStreams) {
            stream.write(b);
        }
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        for (PrintStream stream : additionalStreams) {
            stream.write(buf, off, len);
        }
    }

    @Override
    public void flush() {
        super.flush();
        for (PrintStream stream : additionalStreams) {
            stream.flush();
        }
    }

    @Override
    public void close() {
        super.close();
        for (PrintStream stream : additionalStreams) {
            stream.close();
        }
    }
}
