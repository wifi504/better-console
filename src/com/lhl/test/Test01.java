package com.lhl.test;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.Properties;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/18_14:09
 */
public class Test01 {
    public static void main(String[] args) throws InterruptedException {
        LinkedList<Integer> integers = new LinkedList<>();
        integers.add(0);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        integers.add(4);
        System.out.println(integers);
        integers.add(2, 114514);
        System.out.println(integers);
        integers.remove(2);
        System.out.println(integers);
//        integers.add(10, 10);

        Properties properties = System.getProperties();
//        StringBuilder sb = new StringBuilder();
//        sb.append(System.getProperty("java.runtime.name")).append("\n")
//                .append(System.getProperty("java.vm.name")).append("\n")
//                .append("版本：").append(System.getProperty("java.version")).append("\n")
//                .append("路径：").append(System.getProperty("java.home")).append("\n")
//                .append("当前操作系统：").append(System.getProperty("os.name")).append("\n")
//                .append("用户名：").append(System.getProperty("user.name"));
//        System.out.println(sb);
        // 获取可用的处理器数量
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("可用的处理器数量: " + processors);

        // 获取JVM的内存信息
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();

        System.out.println("JVM的空闲内存: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("JVM的总内存: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("JVM的最大内存: " + maxMemory / (1024 * 1024) + " MB");


        // 获取操作系统管理接口
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        // 获取初始的 CPU 时间和系统时间
        long prevJvmCpuTime = osBean.getProcessCpuTime();
        long prevSystemTime = System.nanoTime();

        // 休眠一段时间，模拟运行中的 JVM
        Thread.sleep(1000);

        // 获取当前的 CPU 时间和系统时间
        long jvmCpuTime = osBean.getProcessCpuTime();
        long systemTime = System.nanoTime();

        // 计算 CPU 使用率
        long elapsedCpu = jvmCpuTime - prevJvmCpuTime;
        long elapsedTime = systemTime - prevSystemTime;

        // CPU 使用百分比 = (CPU时间 / 系统时间) * 100
        double cpuUsage = (elapsedCpu * 100.0) / elapsedTime;

        System.out.printf("JVM的CPU使用率: %.2f%%\n", cpuUsage);
    }
}
