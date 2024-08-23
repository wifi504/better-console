package com.lhl.test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/23_15:18
 */
public class MyScanner {
    private InputStream inputStream;

    public MyScanner(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void scanAndPrint() {
        try {
            int data;
            while ((data = inputStream.read()) != -1) {
                char ch = (char) data;
                System.out.print(ch);  // 立即打印出读取到的字符
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyScanner myScanner = new MyScanner(System.in);
        myScanner.scanAndPrint();
    }
}