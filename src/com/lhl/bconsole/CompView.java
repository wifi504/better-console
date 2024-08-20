package com.lhl.bconsole;

/**
 * 组件实现类 - 视图组件
 * 此组件自身不携带任何数据，将多个组件注册形成一个视图
 * <hr />
 * ref 在此组件中无效
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/17_2:03
 */
public class CompView extends Comp<CompView> {

    private static final CompText c = new CompText("\n");

    public CompView() {
        this.isDirty = false;
    }

    /**
     * 在组件后添加一个“换行”
     *
     * @return 可以链式调用
     */
    public CompView wrap() {
        this.reg(c);
        return this;
    }

    @Override
    @Deprecated(since = "0 视图组件不支持绑定更新回调")
    @InvalidUsage(reason = "视图组件不支持绑定更新回调")
    public CompView ref(ObjectRefresh refresh) {
        return this;
    }
}
