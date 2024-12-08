package com.lhl.bconsole2.component;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * 组件组合，可以便于批量管理同类组件
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/9_4:05
 */
public class ComponentsGroup<T extends Component<?>> {
    private final ArrayList<T> group = new ArrayList<>();

    public ComponentsGroup() {
    }

    // 组件调用自己的 joinGroup 方法后会增加进去
    protected void add(T t) {
        group.add(t);
    }


    /**
     * 批量应用操作到组内的所有组件
     * @param action 消费型接口
     */
    public void applyToAll(Consumer<T> action) {
        group.forEach(action);
    }
}
