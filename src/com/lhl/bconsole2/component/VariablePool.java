package com.lhl.bconsole2.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * 变量池
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_12:30
 */
public class VariablePool<T> {
    ArrayList<T> v = new ArrayList<>(); // 变量池

    private int iterator = 0; // 迭代器

    protected VariablePool() {
    }

    /**
     * 绑定一组变量池监听变量
     * 覆盖
     *
     * @param t 要绑定的静态变量
     */
    @SafeVarargs
    public final void bind(T... t) {
        v.clear();
        v.addAll(Arrays.asList(t));
        iterator = 0;
    }

    /**
     * 绑定一个变量池监听变量
     * 追加
     *
     * @param t 要追加的对象
     */
    protected final void add(T t) {
        v.add(t);
    }

    /**
     * 获取下一个变量
     *
     * @return 下一个变量
     */
    protected T next() {
        if (iterator < v.size()) return v.get(iterator++);
        return null;
    }

    /**
     * 获取变量池元素个数
     *
     * @return 变量池元素个数
     */
    protected int getSize() {
        return v.size();
    }

    /**
     * 迭代器重置
     */
    protected void reset() {
        iterator = 0;
    }

    /**
     * 是否迭代完毕
     *
     * @return Boolean
     */
    protected boolean isEnd() {
        return iterator >= getSize();
    }

    /**
     * VariablePool 遍历
     *
     * @param action 元素操作
     */
    protected void forEach(Consumer<? super T> action) {
        for (T t : v) {
            action.accept(t);
        }
    }
}
