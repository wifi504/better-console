package com.lhl.bconsole2.callback;

import com.lhl.bconsole2.component.VariablePool;

/**
 * 变量对象更新回调
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_9:48
 */

@FunctionalInterface
public interface ObjectCallback {
    void call(VariablePool<Object> v);
}
