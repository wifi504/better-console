package com.lhl.bconsole;

/**
 * 组件实现类 - 进度条组件
 * <hr />
 * ref 绑定 public static 修饰的变量，变量的意义是完成的任务量
 * 本类的ref是自行实现的
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/20_15:42
 */
public class CompBar extends Comp<CompBar> {

    public static final int RIGHT = 0;
    public static final int TOP = 1;
    public static final int LEFT = 2;
    public static final int BOTTOM = 3;

    private int length = 60; // 进度条长度
    private int face = CompBar.RIGHT; // 进度条增长方向朝向
    private double allTask = 0; // 总任务有多少
    private double finishTask = 0; // 已经完成了多少 使用ref绑定以动态更新
    private String unit; // 这个任务的单位，不设置就直接不显示，没有单位且要显示就设置 ""
    private int showPercent = -1; // 显示百分比吗，-1不显示，其他值代表百分比的小数位数
    private String remainingSymbol = "-"; // 无框进度条未完成的符号
    private String completeSymbol = "#"; // 无框进度条已完成的符号
    private boolean showBorder = false; // 要绘制有框进度条吗
    private boolean isBold = false; // 有框的框要加粗吗

    private ObjectRefresh barAction = null; // 本类自行实现更新事件

    /**
     * 设置进度条长度
     *
     * @param length 长度 int
     * @return 可以链式调用
     */
    public CompBar setLength(int length) {
        this.length = length;
        return this;
    }

    /**
     * 设置进度条增长方向朝向
     *
     * @param face 进度条增长方向朝向
     * @return 可以链式调用
     */
    public CompBar setFace(int face) {
        this.face = face;
        return this;
    }

    /**
     * 设置总的任务量
     *
     * @param allTask 总任务有多少 double
     * @return 可以链式调用
     */
    public CompBar setAllTask(double allTask) {
        this.allTask = allTask;
        return this;
    }

    /**
     * 设置已经完成了多少任务量
     * 推荐使用 ref() 绑定以动态更新
     *
     * @param finishTask 已经完成了多少 double
     * @return 可以链式调用
     */
    @Deprecated(since = "0 推荐使用 ref() 绑定以动态更新")
    @InvalidUsage(reason = "推荐使用 ref() 绑定以动态更新")
    public CompBar setFinishTask(double finishTask) {
        this.finishTask = finishTask;
        return this;
    }

    /**
     * 这个任务的单位
     * 不设置就直接不显示，没有单位且要显示就设置 ""
     *
     * @param unit 单位
     * @return 可以链式调用
     */
    public CompBar setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    /**
     * 显示百分比吗
     * -1不显示，其他值代表百分比的小数位数
     *
     * @param showPercent 显示百分比吗
     * @return 可以链式调用
     */
    public CompBar showPercent(int showPercent) {
        this.showPercent = showPercent;
        return this;
    }

    /**
     * 设置无框进度条未完成的符号
     *
     * @param remainingSymbol 默认"-"
     * @return 可以链式调用
     */
    public CompBar setRemainingSymbol(String remainingSymbol) {
        this.remainingSymbol = remainingSymbol;
        return this;
    }

    /**
     * 设置无框进度条已完成的符号
     *
     * @param completeSymbol 默认"#"
     * @return 可以链式调用
     */
    public CompBar setCompleteSymbol(String completeSymbol) {
        this.completeSymbol = completeSymbol;
        return this;
    }

    /**
     * 要绘制有框进度条吗
     *
     * @param showBorder 要绘制有框进度条吗
     * @return 可以链式调用
     */
    public CompBar setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    /**
     * 有框的框要加粗吗
     *
     * @param bold 加粗吗
     * @return 可以链式调用
     */
    public CompBar setBold(boolean bold) {
        isBold = bold;
        return this;
    }

    // 水平进度条绘制
    private String horizontalBarRender(int complete, int remaining) {
        StringBuilder sb = new StringBuilder();
        if (showBorder) {
            // 带框的进度条，完成了就是实心，没完成的就是空心，不再支持自定义了
            StringBuilder temp = new StringBuilder();
            temp.append(" ");
            if (face == CompBar.RIGHT) {
                temp.append("\u2588".repeat(complete));
                temp.append(" ".repeat(remaining));
            } else if (face == CompBar.LEFT) {
                temp.append(" ".repeat(remaining));
                temp.append("\u2588".repeat(complete));
            }
            temp.append(" ");
            CompBlock block = new CompBlock(temp.toString());
            if (isBold) {
                block.setBorder(CompBlock.BOLD);
            }
            sb.append(block);
            // 这是三行的，进度条信息扔第二行后面
            int insert = sb.indexOf("\n", sb.indexOf("\n") + 1);
            sb.insert(insert, " " + barInfo());
        } else {
            // 不带框的进度条
            sb.append("[");
            if (face == CompBar.RIGHT) {
                sb.append(completeSymbol.repeat(complete));
                sb.append(remainingSymbol.repeat(remaining));
            } else if (face == CompBar.LEFT) {
                sb.append(remainingSymbol.repeat(remaining));
                sb.append(completeSymbol.repeat(complete));
            }
            sb.append("] ");
            // 这是单行的，进度条信息直接扔后面
            sb.append(barInfo());
        }
        return sb.toString();
    }

    // 竖直进度条绘制
    private String verticalBarRender(int complete, int remaining) {
        StringBuilder sb = new StringBuilder();
        if (showBorder) {
            // 带框的进度条，完成了就是实心，没完成的就是空心，不再支持自定义了
            StringBuilder temp = new StringBuilder();
            if (face == CompBar.TOP) {
                temp.append(" \n".repeat(remaining));
                temp.append("\u2588\n".repeat(complete));
            } else if (face == CompBar.BOTTOM) {
                temp.append("\u2588\n".repeat(complete));
                temp.append(" \n".repeat(remaining));
            }
            CompBlock block = new CompBlock(temp.toString());
            if (isBold) {
                block.setBorder(CompBlock.BOLD);
            }
            sb.append(block).append("\n");
        } else {
            // 不带框的进度条
            sb.append("=\n");
            if (face == CompBar.TOP) {
                sb.append((completeSymbol + "\n").repeat(complete));
                sb.append((remainingSymbol + "\n").repeat(remaining));
            } else if (face == CompBar.BOTTOM) {
                sb.append((remainingSymbol + "\n").repeat(remaining));
                sb.append((completeSymbol + "\n").repeat(complete));
            }
            sb.append("=\n");
        }
        // 竖着的进度条，进度条信息都直接扔底下
        sb.append(barInfo());
        return sb.toString();
    }

    // 格式化任务
    private String formatDouble(double number) {
        if (number % 1.0 == 0) return String.format("%.0f", number);
        return String.valueOf(number);
    }

    // 进度条信息
    private String barInfo() {
        StringBuilder sb = new StringBuilder();
        // 输出任务具体信息
        if (unit != null) {
            sb.append(formatDouble(finishTask)).append(unit)
                    .append("/").
                    append(formatDouble(allTask)).append(unit).append(" ");
        }
        // 输出百分比显示
        if (showPercent != -1) {
            // 准备要显示的百分比
            double percent = finishTask / allTask * 100.0;
            String format = "%." + showPercent + "f";
            String p = String.format(format, percent);
            if (unit != null) {
                // 如果前面输出了任务信息，那么要带括号
                sb.append("(").append(p).append("%)");
            } else {
                // 否则就是裸着输出百分比的情况
                sb.append(p).append("%");
            }
        }
        return sb.toString();
    }

    @Override
    protected String thisRender() {
        if (barAction != null) {
            updateSchedule(barAction);
        }
        // 渲染多长的已完成
        int complete = (int) Math.round(finishTask / allTask * length);
        // 渲染多长的未完成
        int remaining = length - complete;
        String s = switch (face) {
            case CompBar.LEFT, CompBar.RIGHT -> horizontalBarRender(complete, remaining);
            case CompBar.TOP, CompBar.BOTTOM -> verticalBarRender(complete, remaining);
            default -> "";
        };
        this.data.put(Comp.TEXT, s);
        return super.thisRender();
    }

    // 更新进度条
    private void updateSchedule(ObjectRefresh refresh) {
        if (refresh == null) return;
        @SuppressWarnings("unchecked")
        VariablePool<Object> variablePool = (VariablePool<Object>) this.data.get(Comp.VALUES);
        refresh.refresh(variablePool);
        Object v = variablePool.next();
        if (!(v instanceof Double)) return; // 变量绑定错了，不是 double
        variablePool.reset();
        finishTask = Math.min(allTask, (double) v);
    }

    // 初始化进度条
    private void initBar() {
        this.isDirty = true;
    }

    public CompBar() {
        initBar();
    }

    @Override
    public CompBar ref(ObjectRefresh refresh) {
        barAction = refresh;
        return this;
    }
}
