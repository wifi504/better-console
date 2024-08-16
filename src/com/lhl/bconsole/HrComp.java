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
public class HrComp extends Comp {

    private String s = "-"; // 水平线用的字符
    private int c = 60; // 水平线默认长度

    public HrComp() {
        super();
        this.setComp(c);
        this.isDirty = false; // 默认是常量组件
    }

    public HrComp(int count) {
        super();
        this.setComp(count);
        this.isDirty = false; // 默认是常量组件
    }

    public HrComp(String text) {
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
    public Comp setComp(int count) {
        c = count;
        this.data.put(Comp.TEXT, s.repeat(count) + "\n");
        this.cache = this.render();
        return this;
    }

    /**
     * 设置水平线的字符
     *
     * @param text 设置水平线的字符
     * @return 可以链式调用
     */
    @Override
    public Comp setComp(String text) {
        s = text;
        this.data.put(Comp.TEXT, s.repeat(c) + "\n");
        this.cache = this.render();
        return this;
    }

    @Override
    @Deprecated(since = "0 水平线不支持绑定更新回调")
    @InvalidUsage(reason = "水平线不支持绑定更新回调")
    public Comp ref(StringRefresh refresh) {
        return this;
    }
}
