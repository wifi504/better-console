package com.lhl.bconsole2.component;

import com.lhl.bconsole2.Render;
import com.lhl.bconsole2.callback.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 根组件抽象类
 *
 * @author WIFI连接超时
 * @version 2.0
 * Create Time 2024/12/3_1:15
 */
public abstract class Component<T extends Component<?>> {

    // 组件数据
    protected static final String TEXT = "text";
    protected static final String VALUES = "values";
    protected static final String CHILDREN = "children";
    protected final HashMap<String, Object> data = new HashMap<>();

    // 渲染缓存
    protected boolean isDirty = true; // 是否为脏数据，默认为true，缓存机制，复用组件不会在一次刷新中重复更新
    protected String cache = ""; // 组件渲染缓存，非脏不二次渲染

    // 组件变量刷新回调
    protected ObjectCallback variableRefreshCallback = null;

    // 生命周期钩子
    protected Callback beforeRenderCallback = null;
    protected Callback afterRenderCallback = null;

    // 组件结束换行符数量（默认 BLOCK ，会自动确保最后以该数量换行作为结束，块级组件）
    public static final int INLINE = 0;
    public static final int BLOCK = 1;
    protected int warpNum = BLOCK;

    // 私有唯一组件工厂
    protected static final ComponentFactory cf = new ComponentFactory();

    // 组件构造方法
    public Component() {
        this.data.put(TEXT, ""); // 初始化内容，默认值 ""
        this.data.put(VALUES, new VariablePool<>()); // 初始化变量池
        this.data.put(CHILDREN, new VariablePool<>()); // 初始化子组件池
    }

    /**
     * 更新绑定器
     * 为组件的魔法变量绑定对应内容
     * <br />
     * 该方法允许被覆写，因为部分组件涉及绘制问题，动态数据会导致绘制错位
     *
     * @param callback 变量池函数式接口 ref(v -> v.bind(...))
     * @return 可以链式调用
     */
    public T ref(ObjectCallback callback) {
        this.variableRefreshCallback = callback;
        return (T) this;
    }

    /**
     * 组件注册
     *
     * @param component 被注册组件
     * @return 可以链式调用
     */
    public final T reg(Component<?> component) {
        @SuppressWarnings("unchecked") VariablePool<Component<?>> children = (VariablePool<Component<?>>) this.data.get(CHILDREN);
        children.add(component);
        return (T) this;
    }

    /**
     * 组件注册
     *
     * @param text 以纯文本形式注册组件，自动封装成 Text 组件
     * @return 可以链式调用
     */
    public final T reg(String text) {
        reg(cf.newDivText(text));
        return (T) this;
    }

    /**
     * 注册生命周期回调：组件被渲染前调用
     *
     * @param callback 回调函数
     * @return 可以链式调用
     */
    public final T onBeforeRender(Callback callback) {
        this.beforeRenderCallback = callback;
        return (T) this;
    }

    /**
     * 注册生命周期回调：组件被渲染后调用
     *
     * @param callback 回调函数
     * @return 可以链式调用
     */
    public final T onAfterRender(Callback callback) {
        this.afterRenderCallback = callback;
        return (T) this;
    }

    /**
     * 设置组件类型（行级组件 / 块级组件）
     * 通过控制组件结束换行符数量来实现
     *
     * @param warpNum 末尾换行符数量
     * @return 可以链式调用
     */
    public final T setLayout(int warpNum) {
        if (warpNum < 0) {
            this.warpNum = INLINE;
        } else {
            this.warpNum = warpNum;
        }
        return (T) this;
    }

    /**
     * 设置组件为脏状态
     */
    public void setDirty() {
        this.isDirty = true;
    }

    // 组件渲染
    protected final String render() {
        String result;
        if (!this.isDirty) {
            // 如果数据是干净的，直接返回缓存
            result = this.cache + childrenRender();
        } else {
            // 否则执行渲染
            result = thisRender() + childrenRender();
            // 渲染结束标记数据为干净的，并且追加进已缓存
            this.isDirty = false;
            Render.rendered.add(this);
        }
        return result;
    }

    // 渲染当前组件的内容
    protected String thisRender() {
        // 渲染前回调
        if (beforeRenderCallback != null) {
            beforeRenderCallback.call();
        }
        this.cache = "";
        String text = (String) this.data.get(TEXT);
        @SuppressWarnings("unchecked")
        VariablePool<Object> variablePool = (VariablePool<Object>) this.data.get(VALUES);
        if (variableRefreshCallback != null) {
            // 绑定了变量的组件，要更新变量
            variableRefreshCallback.call(variablePool);
        }
        if (variableRefreshCallback != null && variablePool.getSize() > 0) {
            List<String> texts = Arrays.asList(text.split("\\$v\\$"));
            if (!texts.isEmpty()) {
                // 如果是正常文本组件则正常组合渲染
                texts.forEach(t -> this.cache += t + (!variablePool.isEnd() ? Objects.toString(variablePool.next()) : ""));
                variablePool.reset();
            } else {
                // 只有变量，渲染变量
                this.cache = Objects.toString(variablePool.next());
                variablePool.reset();
            }
        } else {
            // 静态组件
            this.cache = text;
        }
        // 控制最后的换行符数量
        this.cache = this.cache.stripTrailing() + "\n".repeat(warpNum);
        // 渲染后回调
        if (afterRenderCallback != null) {
            afterRenderCallback.call();
        }
        return this.cache;
    }

    // 递归渲染子组件
    protected final String childrenRender() {
        @SuppressWarnings("unchecked") VariablePool<Component<?>> children = (VariablePool<Component<?>>) this.data.get(CHILDREN);
        if (children.isEnd()) return ""; // 无子组件，返回空串
        StringBuilder sb = new StringBuilder();
        children.forEach(sb::append);
        return sb.toString();
    }

    @Override
    public final String toString() {
        return render();
    }
}