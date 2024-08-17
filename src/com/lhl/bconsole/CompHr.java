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

    private String s = "-"; // 水平线用的字符
    private int c = 60; // 水平线默认长度

    public CompHr() {
        super();
        this.setComp(c);
        this.isDirty = false; // 默认是常量组件
    }

    public CompHr(int count) {
        super();
        this.setComp(count);
        this.isDirty = false; // 默认是常量组件
    }

    public CompHr(String text) {
        super();
        this.setComp(text);
        this.isDirty = false; // 默认是常量组件
    }

    /**
     * 设置水平线的长度
     *
     * @param count 长度
     * @return 可以链式调用
     */
    @Override
    public CompHr setComp(int count) {
        c = count;
        this.data.put(Comp.TEXT, s.repeat(count) + "\n");
        this.cache = this.forceRender();
        return this;
    }

    /**
     * 设置水平线的字符
     *
     * @param text 设置水平线的字符
     * @return 可以链式调用
     */
    @Override
    public CompHr setComp(String text) {
        s = text;
        this.data.put(Comp.TEXT, s.repeat(c) + "\n");
        this.cache = this.forceRender();
        return this;
    }

    @Override
    @Deprecated(since = "0 水平线不支持绑定更新回调")
    @InvalidUsage(reason = "水平线不支持绑定更新回调")
    public CompHr ref(StringRefresh refresh) {
        return this;
    }
}
