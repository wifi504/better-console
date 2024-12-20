package com.lhl.bconsole2.component;

import com.lhl.bconsole2.callback.ObjectCallback;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 组件实现类 - 块组件
 * <hr />
 * 如果块内容直接插入了文本，ref可以为该文本内容绑定更新变量
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/5_0:40
 */
public class Block extends Component<Block> {

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
    private Component<?> content; // 块内容
    private String[] preCache; // 块内容预渲染
    private int border = NORMAL; // 边框样式
    private int alignment = LEFT; // 对齐方式
    private boolean showLineNum = false; // 显示行号
    private int scroll = 1; // 每次刷新滚动行数（若可以完全渲染则不滚动）
    private int currentScroll = 0; // 当前滚动位置
    private boolean alwaysShowEnd = false; // 是否永远输出最后一页

    // 构造方法，实例对象从工厂获取
    protected Block() {
    }

    /**
     * 设置块内容
     *
     * @param comp 块内组件
     * @return 可以链式调用
     */
    public Block setContent(Component<?> comp) {
        this.content = comp;
        return this;
    }

    /**
     * 设置块静态文本
     *
     * @param text 内容
     * @return 可以链式调用
     */
    public Block setContent(String text) {
        setContent(cf.newSpanText(text));
        return this;
    }

    /**
     * 设置块宽
     *
     * @param width int 宽度
     * @return 可以链式调用
     */
    public Block setWidth(int width) {
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
    public Block setHeight(int height) {
        // 设置了块高，则显示不下的部分要滚动显示
        this.height = height;
        return this;
    }

    /**
     * 设置对齐方式
     *
     * @param align 对齐方式 Block.LEFT Block.CENTER Block.RIGHT
     * @return 可以链式调用
     */
    public Block setAlignment(int align) {
        alignment = align;
        return this;
    }

    /**
     * 设置边框样式
     *
     * @param type 边框样式
     * @return 可以链式调用
     */
    public Block setBorder(int type) {
        this.border = type;
        return this;
    }

    /**
     * 设置滚屏速度
     *
     * @param speed 速度：行
     * @return 可以链式调用
     */
    public Block setScrollSpeed(int speed) {
        this.scroll = speed;
        return this;
    }

    /**
     * 是否输出行号
     *
     * @param b 输出行号
     * @return 可以链式调用
     */
    public Block showLineNumber(boolean b) {
        showLineNum = b;
        return this;
    }

    /**
     * 是否永远显示最后一页
     *
     * @param b 显示最后一页
     * @return 可以链式调用
     */
    public Block alwaysShowEnd(boolean b) {
        alwaysShowEnd = b;
        return this;
    }

    // 内容预渲染
    private void preRender() {
        String[] temp = content.toString().split("\n");

        // 如果没指定宽度，直接以换行符分隔
        if (width == -1) {
            // 是否要显示行号，如果是，则每行追加显示
            if (showLineNum) {
                for (int i = 0; i < temp.length; i++) {
                    temp[i] = thisLineNumber(i, true, temp.length) + temp[i];
                }
            }
            preCache = temp;
            return;
        }

        // 否则以指定的宽和换行符分隔
        // 先判断要不要加行号
        if (showLineNum) {
            temp = content.toString().replace("\n", "\n@INS_@").split("\n");
            temp[0] = "@INS_@" + temp[0];
            temp = Arrays.copyOf(temp, temp.length - 1);
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < temp.length; i++) {
            int start = 0;
            while (start < temp[i].length()) {
                int end = Math.min(start + width, temp[i].length());
                String sub = temp[i].substring(start, end);
                if (sub.contains("@INS_@")) {
                    sub = sub.replaceAll("@INS_@", thisLineNumber(i, true, temp.length));
                } else if (showLineNum) {
                    sub = thisLineNumber(i, false, temp.length) + sub;
                }
                strings.add(sub);
                start = end;
            }
        }
        preCache = strings.toArray(new String[0]);
    }

    // 绘制行号，传参：行号索引；是否是主行；最大行数
    private String thisLineNumber(int i, boolean isMainLine, int maxLines) {
        // 计算最大行数的字符宽度
        int width = String.valueOf(maxLines).length();

        // 行号字符串，如果是主行则显示行号，否则显示空格
        String lineNumber = isMainLine ? String.format("%" + width + "d", i + 1) : " ".repeat(width);

        // 绘制箭头，行号和垂直分隔符
        return "-> " + lineNumber + brush(TableSymbol.VERTICAL, border) + " ";
    }

    // 获取正确的对齐方式，如果要显示行号，强制左对齐
    private int getAlignment() {
        return showLineNum ? LEFT : alignment;
    }

    // 绘制输出块
    private String toBlock(String[] text, int type) {
        StringBuilder sb = new StringBuilder();
        if (type == NONE) {
            // 无框绘制
            for (String s : text) {
                sb.append(Table.dataLineFormat(s, renderWidth, getAlignment())).append("\n");
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            // 普通框绘制 & 加粗框绘制
            // 绘制框头
            sb.append(brush(TableSymbol.TOP_LEFT_CORNER, type))
                    .append(brush(TableSymbol.HORIZONTAL, type)
                            .repeat(renderWidth))
                    .append(brush(TableSymbol.TOP_RIGHT_CORNER, type))
                    .append("\n");
            // 绘制框体
            for (String s : text) {
                sb.append(brush(TableSymbol.VERTICAL, type))
                        .append(Table.dataLineFormat(s, renderWidth, getAlignment()))
                        .append(brush(TableSymbol.VERTICAL, type))
                        .append("\n");
            }
            // 绘制框尾
            sb.append(brush(TableSymbol.BOTTOM_LEFT_CORNER, type))
                    .append(brush(TableSymbol.HORIZONTAL, type)
                            .repeat(renderWidth))
                    .append(brush(TableSymbol.BOTTOM_RIGHT_CORNER, type));
        }
        return sb.toString();
    }

    // 绘制画笔
    private String brush(TableSymbol s, int type) {
        // 普通绘制
        if (type == NORMAL) {
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

    // 块渲染
    private String blockRender() {
        // 获取要渲染区域的宽高
        int preCacheMaxLength = Arrays.stream(preCache).map(Table::getLength).reduce(0, Integer::max);
        renderWidth = Math.max(preCacheMaxLength, width);
        renderHeight = height == -1 ? preCache.length : height;
        // 截取要渲染的片段
        String[] temp = new String[renderHeight];
        Arrays.fill(temp, "");
        if (alwaysShowEnd) currentScroll = preCache.length - renderHeight; // 永远滚在最后一页
        if (renderHeight >= preCache.length) currentScroll = 0; // 要是显示的下就别滚了
        // 开始渲染
        for (int i = 0; i < renderHeight; i++) {
            try {
                temp[i] = preCache[i + currentScroll]; // 利用数组越界异常重置滚动指针
            } catch (Exception ignore) {
                // 读完啦，后面都是空白行，或者是到头啦！
                if (!alwaysShowEnd) {
                    currentScroll = -scroll;
                }
                break;
            }
        }
        currentScroll += scroll; // 下次渲染滚动
        // 绘制输出块
        return toBlock(temp, border);
    }

    @Override
    protected String thisRender() {
        String renderBlock;
        preRender();
        renderBlock = blockRender();
        this.data.put(TEXT, renderBlock);
        return super.thisRender();
    }

    @Override
    public Block ref(ObjectCallback callback) {
        if (content != null) {
            content.variableRefreshCallback = callback;
        }
        return this;
    }
}
