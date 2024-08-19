package com.lhl.bconsole;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 组件实现类 - 块组件
 * <hr />
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/19_13:22
 */
public class CompBlock extends Comp<CompBlock> {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int NONE = 0;
    public static final int NORMAL = 1;
    public static final int BOLD = 2;

    private int width = -1; // 块宽（-1为自动块宽）
    private int height = -1; // 块高（-1为自动块高）
    private int renderWidth; // 块渲染宽
    private int renderHeight; // 块渲染高
    private Comp<?> content; // 块内容
    private String[] preCache; // 块内容预渲染
    private int border = CompBlock.NORMAL; // 边框样式
    private int alignment = CompBlock.LEFT; // 对齐方式
    private boolean showLineNumber = false; // 显示行号
    private int scoll = 1; // 每次刷新滚动行数（若可以完全渲染则不滚动）
    private int currentScoll = 0; // 当前滚动位置

    public CompBlock() {
        initBlock();
    }

    public CompBlock(String text) {
        this();
        setContent(text);
    }

    public CompBlock(Comp<?> comp) {
        this();
        setContent(comp);
    }

    /**
     * 设置块内容
     *
     * @param comp 块内组件
     * @return 可以链式调用
     */
    public CompBlock setContent(Comp<?> comp) {
        this.content = comp;
        return this;
    }

    /**
     * 设置块静态文本
     *
     * @param text 内容
     * @return 可以链式调用
     */
    public CompBlock setContent(String text) {
        setContent(new CompText(text));
        return this;
    }
    
    /**
     * 设置块宽
     *
     * @param width int 宽度
     * @return 可以链式调用
     */
    public CompBlock setWidth(int width) {
        // 设置了块宽，则显示不下的部分要换行显示
        this.width = width;
        return this;
    }

    /**
     * 设置块高
     *
     * @param height int 高度
     * @return 可以链式调用
     */
    public CompBlock setHeight(int height) {
        // 设置了块高，则显示不下的部分要滚动显示
        this.height = height;
        return this;
    }

    /**
     * 设置对齐方式
     *
     * @param align 对齐方式 CompBlock.LEFT CompBlock.CENTER CompTable.RIGHT
     * @return 可以链式调用
     */
    public CompBlock setAlignment(int align) {
        alignment = align;
        return this;
    }

    // 内容预渲染
    private void preRender() {
        String[] temp = content.toString().split("\n");
        // 如果没指定宽度，直接以换行符分隔
        if (width == -1) {
            preCache = temp;
            return;
        }
        // 否则以指定的宽和换行符分隔
        ArrayList<String> strings = new ArrayList<>();
        for (String s : temp) {
            int start = 0;
            while (start < s.length()) {
                int end = Math.min(start + width, s.length());
                strings.add(s.substring(start, end));
                start = end;
            }
        }
        preCache = strings.toArray(new String[0]);
    }

    // 绘制输出块
    private String toBlock(String[] text, int type) {
        StringBuilder sb = new StringBuilder();
        if (type == CompBlock.NONE) {
            // 无框绘制
            for (String s : text) {
                sb.append(s).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            // 普通框绘制 & 加粗框绘制
            // 绘制框头
            sb.append(brush(TableSymbol.TOP_LEFT_CORNER, type)).append(brush(TableSymbol.HORIZONTAL, type).repeat(renderWidth)).append(brush(TableSymbol.TOP_RIGHT_CORNER, type)).append("\n");
            // 绘制框体
            for (String s : text) {
                sb.append(brush(TableSymbol.VERTICAL, type)).append(CompTable.dataLineFormat(s, renderWidth, alignment)).append(brush(TableSymbol.VERTICAL, type)).append("\n");
            }
            // 绘制框尾
            sb.append(brush(TableSymbol.BOTTOM_LEFT_CORNER, type)).append(brush(TableSymbol.HORIZONTAL, type).repeat(renderWidth)).append(brush(TableSymbol.BOTTOM_RIGHT_CORNER, type));
        }
        return sb.toString();
    }

    // 绘制画笔
    private String brush(TableSymbol s, int type) {
        // 普通绘制
        if (type == CompBlock.NORMAL) {
            return s.toString();
        }
        // 加粗绘制
        return switch (s) {
            case HORIZONTAL -> TableSymbol.DOUBLE_HORIZONTAL.toString();
            case VERTICAL -> TableSymbol.DOUBLE_VERTICAL.toString();
            case TOP_LEFT_CORNER -> TableSymbol.DOUBLE_TOP_LEFT_CORNER.toString();
            case TOP_RIGHT_CORNER -> TableSymbol.DOUBLE_TOP_RIGHT_CORNER.toString();
            case BOTTOM_LEFT_CORNER -> TableSymbol.DOUBLE_BOTTOM_LEFT_CORNER.toString();
            case BOTTOM_RIGHT_CORNER -> TableSymbol.DOUBLE_BOTTOM_RIGHT_CORNER.toString();
            default -> "?render?";
        };
    }

    // 绘制行号


    // 块渲染
    private String blockRender() {
        // 获取要渲染区域的宽高
        renderWidth = Arrays.stream(preCache).map(CompTable::getLength).reduce(0, Integer::max);
        renderHeight = height == -1 ? preCache.length : height;
        // 截取要渲染的片段
        String[] temp = new String[renderHeight];
        Arrays.fill(temp, "");
        for (int i = 0; i <= renderHeight; i++) { // 利用数组越界异常重置滚动指针
            try {
                temp[i] = preCache[i + currentScoll];
            } catch (Exception ignore) {
                // 读完啦，后面都是空白行，或者是到头啦！
                currentScoll = -1;
                break;
            }
        }
        currentScoll += scoll; // 下次渲染时滚动
        // 绘制输出块
        return toBlock(temp, border);
    }

    @Override
    protected String thisRender() {
        String renderBlock;
        preRender();
        renderBlock = blockRender();
        this.data.put(Comp.TEXT, renderBlock);
        return super.thisRender();
    }

    // 初始化块
    private void initBlock() {
        this.isDirty = true; // 块内部皆为组件，需要让块每次都更新自己的缓存
    }

    @Override
    public CompBlock ref(ObjectRefresh refresh) {
        return null;
    }
}
