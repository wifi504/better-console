package com.lhl.bconsole2.component;

/**
 * 组件实现类 - 文本组件
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_14:34
 */
public class Text extends Component<Text> {

    // 构造方法，实例对象从工厂获取
    protected Text(String text) {
        setText(text);
    }


    /**
     * 设置文本组件的显示内容
     *
     * @param text 如果有变量需要实时展示，请用 $v$ 魔法变量占位并绑定变量
     * @return 可以链式调用
     */
    public Text setText(String text) {
        this.data.put(TEXT, text);
        return this;
    }
}
