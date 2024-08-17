package com.lhl.bconsole;

/**
 * 组件实现类 - 文本组件
 * <hr />
 * ref 绑定 public static 修饰的 String
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/16_10:07
 */
public class CompText extends Comp<CompText> {
    /**
     * 根据指定内容创建文本组件
     *
     * @param text 文本内容，如果有变量需要实时展示，请用$v$魔法变量占位并绑定变量
     */
    public CompText setText(String text) {
        this.data.put(Comp.TEXT, text);
        return submitSet();
    }

    public CompText(String text) {
        super();
        this.setText(text);
    }

    @Override
    public CompText ref(StringRefresh refresh) {
        this.refAction = refresh;
        this.isDirty = true; // 一旦ref被调用，说明从此以后此组件不再是常量组件
        return this;
    }
}
