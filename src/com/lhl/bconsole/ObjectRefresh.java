package com.lhl.bconsole;

/**
 * 字符串更新回调
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_9:48
 */

@FunctionalInterface
public interface ObjectRefresh {
    void refresh(VariablePool<Object> v);
}
