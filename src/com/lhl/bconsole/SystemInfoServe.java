package com.lhl.bconsole;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;

/**
 * 系统信息服务
 * 可以使用本类中API获取当前系统信息
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/22_15:47
 */
public class SystemInfoServe {

    private static Thread sysInfoThread = null; // 系统状态信息守护线程

    private static final int processors = Runtime.getRuntime().availableProcessors(); // 可用的处理器数量

    private static double cpuUsage = 0; // JVM 总处理器使用率
    private static double freeMemory = 0; // JVM 空闲内存
    private static double totalMemory = 0; // JVM 总内存
    private static double maxMemory = 0; // JVM 最大内存

    /**
     * 获取系统基础属性信息
     *
     * @return 系统基础属性信息
     */
    public static String getProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("java.runtime.name")).append("\n")
                .append(System.getProperty("java.vm.name")).append("\n")
                .append("版本：").append(System.getProperty("java.version")).append("\n")
                .append("路径：").append(System.getProperty("java.home")).append("\n")
                .append("当前操作系统：").append(System.getProperty("os.name")).append("\n")
                .append("用户名：").append(System.getProperty("user.name"));
        return sb.toString();
    }

    /**
     * 可用的处理器数量
     *
     * @return 可用的处理器数量
     */
    public static int getProcessors() {
        initServe();
        return processors;
    }

    /**
     * JVM 总处理器使用率
     *
     * @return JVM 总处理器使用率
     */
    public static double getCpuUsage() {
        initServe();
        return cpuUsage;
    }

    /**
     * JVM 空闲内存
     *
     * @return JVM 空闲内存
     */
    public static double getFreeMemory() {
        initServe();
        return freeMemory;
    }

    /**
     * JVM 总内存
     *
     * @return JVM 总内存
     */
    public static double getTotalMemory() {
        initServe();
        return totalMemory;
    }

    /**
     * JVM 最大内存
     *
     * @return JVM 最大内存
     */
    public static double getMaxMemory() {
        initServe();
        return maxMemory;
    }

    /**
     * 初始化实时更新系统信息的守护线程
     */
    private static void initServe() {
        if (sysInfoThread == null) {
            sysInfoThread = new Thread(() -> {
                // 获取操作系统管理接口
                OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
                // 获取初始的 CPU 时间和系统时间
                long prevJvmCpuTime = osBean.getProcessCpuTime();
                long prevSystemTime = System.nanoTime();
                while (true) { // 线程执行
                    // 更新 此JVM 的处理器占用
                    // 获取当前的 CPU 时间和系统时间
                    long jvmCpuTime = osBean.getProcessCpuTime();
                    long systemTime = System.nanoTime();
                    // 计算 CPU 使用率
                    long elapsedCpu = jvmCpuTime - prevJvmCpuTime;
                    long elapsedTime = systemTime - prevSystemTime;
                    // CPU 使用百分比 = (CPU时间 / 系统时间) * 100 / 核心数
                    cpuUsage = (elapsedCpu * 100.0) / elapsedTime / processors;
                    // 获取JVM的内存信息
                    freeMemory = (double) Runtime.getRuntime().freeMemory() / (1024 * 1024);
                    totalMemory = (double) Runtime.getRuntime().totalMemory() / (1024 * 1024);
                    maxMemory = (double) Runtime.getRuntime().maxMemory() / (1024 * 1024);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {
                    }
                }
            });
            sysInfoThread.setDaemon(true);
            sysInfoThread.setName("System-Info-Serve");
            sysInfoThread.start();
        }
    }
}
