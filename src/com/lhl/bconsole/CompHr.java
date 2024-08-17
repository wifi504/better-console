package com.lhl.bconsole;

/**
 * 组件实现类 - 水平线组件
 * <hr />
 * ref 在此组件中无效
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_17:24
 */
public class CompHr extends Comp<CompHr> {

    private String s = TableSymbol.HORIZONTAL.toString(); // 水平线默认符号
    private int w = 60; // 水平线默认宽度


    /**
     * 设置水平线的宽度
     *
     * @param width 长度
     * @return 可以链式调用
     */
    public CompHr setWidth(int width) {
        w = width;
        this.data.put(Comp.TEXT, s.repeat(width) + "\n");
        return submitSet();
    }

    /**
     * 设置水平线的字符
     *
     * @param symbol 设置水平线的字符
     * @return 可以链式调用
     */
    public CompHr setSymbol(String symbol) {
        s = symbol;
        this.data.put(Comp.TEXT, s.repeat(w).substring(0, w) + "\n");
        return submitSet();
    }

    public CompHr() {
        super();
        this.setWidth(w);
    }

    public CompHr(int width) {
        super();
        this.setWidth(width);
    }

    public CompHr(String symbol) {
        super();
        this.setSymbol(symbol);
    }

    @Override
    @Deprecated(since = "0 水平线不支持绑定更新回调")
    @InvalidUsage(reason = "水平线不支持绑定更新回调")
    public CompHr ref(StringRefresh refresh) {
        return this;
    }
}
