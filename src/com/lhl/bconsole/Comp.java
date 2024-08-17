package com.lhl.bconsole;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 根组件抽象类
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_10:02
 */
public abstract class Comp<T extends Comp<T>> {

    protected static final String TEXT = "text";
    protected static final String VALUES = "values";
    protected static final String CHILDREN = "children";

    protected final HashMap<String, Object> data = new HashMap<>(); // 组件数据

    protected boolean isDirty = true; // 是否为脏数据，默认为true
    protected String cache = ""; // 组件渲染缓存，非脏不二次渲染
    protected StringRefresh refAction = null; // 刷新回调

    Comp() {
        this.data.put(TEXT, ""); // 初始化内容，默认值 ""
        this.data.put(VALUES, null); // 初始化变量池
        this.data.put(CHILDREN, null); // 初始化子组件池
        this.initValues(new VariablePool<>()); // 分配变量池
        this.initChildren(new VariablePool<>()); // 分配子组件池
    }

    public abstract T setComp(int prop); // 更新组件

    public abstract T setComp(String text); // 更新组件

    public abstract T ref(StringRefresh refresh); // 更新绑定器

    /**
     * 组件注册
     *
     * @param comp 被注册组件
     * @return 可以链式调用
     */
    public Comp<T> reg(Comp<?> comp) {
        @SuppressWarnings("unchecked")
        VariablePool<Comp<?>> children = (VariablePool<Comp<?>>) this.data.get(Comp.CHILDREN);
        children.add(comp);
        return this;
    }

    protected void initValues(VariablePool<String> v) {
        this.data.put("values", v);
    }

    protected void initChildren(VariablePool<Comp<?>> c) {
        this.data.put(Comp.CHILDREN, c);
    }

    protected String render() {
        // 如果数据是干净的，直接返回缓存
        if (!this.isDirty) return this.cache + renderChildren();
        // 否则执行渲染
        return this.forceRender() + renderChildren();
    }

    // 渲染当前组件的内容
    protected String forceRender() {
        this.cache = "";
        String text = (String) this.data.get(Comp.TEXT);
        @SuppressWarnings("unchecked")
        VariablePool<String> variablePool = (VariablePool<String>) this.data.get(Comp.VALUES);
        if (refAction != null && variablePool != null) {
            // 绑定了ref的动态组件
            refAction.refresh(variablePool);
            List<String> texts = Arrays.asList(text.split("\\$v\\$"));
            texts.forEach(t -> this.cache += t + (!variablePool.isEnd() ? variablePool.next() : ""));
            variablePool.reset();
        } else {
            // 静态组件
            this.cache = text;
        }
        return this.cache;
    }

    // 递归渲染子组件
    protected String renderChildren() {
        AtomicReference<String> childrenCache = new AtomicReference<>("");
        @SuppressWarnings("unchecked")
        VariablePool<Comp<T>> children = (VariablePool<Comp<T>>) this.data.get(Comp.CHILDREN);
        if (children.isEnd()) return ""; // 无子组件
        children.forEach(comp -> childrenCache.updateAndGet(v -> v + comp));
        return childrenCache.get();
    }

    @Override
    public final String toString() {
        return this.render();
    }
}
