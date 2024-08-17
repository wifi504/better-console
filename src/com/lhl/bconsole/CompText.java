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

    public CompText(String text) {
        super();
        this.setComp(text);
        this.isDirty = false; // 默认为常量组件
    }

    /**
     * 根据指定内容创建文本组件
     *
     * @param num 直接把数字作为文本内容创建
     * @return 可以链式调用
     */
    @Override
    public CompText setComp(int num) {
        this.setComp(String.valueOf(num));
        this.cache = this.forceRender();
        return this;
    }

    /**
     * 根据指定内容创建文本组件
     *
     * @param text 文本内容，如果有变量需要实时展示，请用$v$魔法变量占位并绑定变量
     * @return 可以链式调用
     */
    @Override
    public CompText setComp(String text) {
        this.data.put(Comp.TEXT, text);
        this.cache = this.forceRender();
        return this;
    }

    @Override
    public CompText ref(StringRefresh refresh) {
        this.refAction = refresh;
        this.isDirty = true; // 一旦ref被调用，说明从此以后此组件不再是常量组件
        return this;
    }
}
