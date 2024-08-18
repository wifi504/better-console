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
        return setHr(s, w);
    }

    /**
     * 设置水平线的字符
     *
     * @param symbol 设置水平线的字符
     * @return 可以链式调用
     */
    public CompHr setSymbol(String symbol) {
        s = symbol;
        return setHr(s, w);
    }

    private CompHr setHr(String symbol, int width) {
        this.data.put(Comp.TEXT, symbol.repeat(width).substring(0, width) + "\n");
        return submitSet();
    }

    public CompHr() {
        this.setWidth(w);
    }

    public CompHr(int width) {
        this.setWidth(width);
    }

    public CompHr(String symbol) {
        this.setSymbol(symbol);
    }

    @Override
    @Deprecated(since = "0 水平线不支持绑定更新回调")
    @InvalidUsage(reason = "水平线不支持绑定更新回调")
    public CompHr ref(ObjectRefresh refresh) {
        return this;
    }
}
