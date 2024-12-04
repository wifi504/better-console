package com.lhl.bconsole2.component;

import com.lhl.bconsole2.callback.ObjectCallback;

/**
 * 组件实现类 - 视图组件
 * 此组件自身不携带任何数据，将多个组件注册形成一个视图
 * <hr />
 * ref 在此组件中无效
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_1:41
 */
public class View extends Component<View> {

    private static final Text l = cf.newSpanText("\n");

    // 构造方法，实例对象从工厂获取
    protected View() {
    }

    /**
     * 在组件后添加一个“换行”
     *
     * @return 可以链式调用
     */
    public View wrap() {
        this.reg(l);
        return this;
    }

    @Override
    @Deprecated(since = "0 视图组件不支持绑定更新回调")
    public View ref(ObjectCallback callback) {
        return this;
    }
}
