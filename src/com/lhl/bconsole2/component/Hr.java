package com.lhl.bconsole2.component;

import com.lhl.bconsole2.callback.ObjectCallback;

/**
 * 组件实现类 - 水平线组件
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/4_23:06
 */
public class Hr extends Component<Hr> {

    private String s = TableSymbol.HORIZONTAL.toString(); // 水平线默认符号
    private int w = 60; // 水平线默认宽度

    // 构造方法，实例对象从工厂获取
    protected Hr() {
        setHr(s, w);
    }

    /**
     * 设置水平线的宽度
     *
     * @param width 长度
     * @return 可以链式调用
     */
    public Hr setWidth(int width) {
        w = width;
        return setHr(s, w);
    }

    /**
     * 设置水平线的字符
     *
     * @param symbol 设置水平线的字符
     * @return 可以链式调用
     */
    public Hr setSymbol(String symbol) {
        s = symbol;
        return setHr(s, w);
    }

    private Hr setHr(String symbol, int width) {
        this.data.put(TEXT, symbol.repeat(width).substring(0, width) + "\n");
        return this;
    }

    /**
     * 更新绑定器
     * 水平线没有自动更新的必要，它的几个set方法会实时更新，ref方法无效
     *
     * @param callback 变量池函数式接口 ref(v -> v.bind(...))
     * @return 可以链式调用
     */
    @Override
    public Hr ref(ObjectCallback callback) {
        return this;
    }
}
