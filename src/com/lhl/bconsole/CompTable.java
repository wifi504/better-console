package com.lhl.bconsole;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 组件实现类 - 表格组件
 * <hr />
 * 表格组件是一个特殊的组件，你不仅可以在他的单元格中存放基本类型数据(包括String)
 * 还可以直接存放一个组件
 * <br />
 * 表格在最终渲染时，会为所有列绘制竖直分隔线，如果需要在输出行间添加水平分隔线,
 * 请使用 drawLine 在某一行下方绘制水平分隔线。
 * 实验性功能：转置
 * 转置操作的是渲染时缓存，而不是表结构，表对齐方式是渲染时动作
 * 请注意：转置操作后会强制启用全框线渲染模式
 * <br />
 * ref 在此组件中无效，如果你需要动态更新表格中某个元素，
 * 请把该元素封装为一个组件传入，并给该组件绑定变量
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/17_2:18
 */
public class CompTable extends Comp<CompTable> {

    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;

    private LinkedList<HashMap<Integer, Comp<?>>> cells; // 单元格数据
    private HashMap<Integer, Integer> alignments; // 列对齐方式
    private ArrayList<Integer> horizontals; // 水平线坐标缓存
    private String[][] preCache; // 单元格预渲染缓存
    private String[][] rasterizeCache; // 栅格化渲染缓存
    private int[] maxRowsHeight; // 栅格化行统计
    private boolean isTranspositional = false; // 是否转置渲染，默认false
    private boolean isNoneBorder = false; // 是否无框线渲染，默认false
    private boolean isFullBorder = false; // 是否全部框线渲染，默认false
    private int colSize = 0; // 表格列数
    private int rowSize = 0; // 表格行数
    private int pointX = 1; // 编辑指针X
    private int pointY = 1; // 编辑指针Y

    /**
     * 指定单元格坐标设置对应组件
     *
     * @param colX 列（横坐标），索引从1开始
     * @param rowY 行（纵坐标），索引从1开始
     * @param comp 单元格组件
     * @return 可以链式调用
     */
    public CompTable setCell(int colX, int rowY, Comp<?> comp) {
        // 判断是否有这么多行，如果没有就在最后进行追加
        if (rowY > cells.size()) appendRows(rowY - cells.size());
        HashMap<Integer, Comp<?>> row = cells.get(rowY - 1);
        row.put(colX - 1, comp);

        // 更新表格结构信息
        rowSize = cells.size(); // 表格行数
        colSize = Math.max(colX, colSize); // 表格列数
        pointX = colX;
        pointY = rowY + 1;
        return this;
    }

    /**
     * 指定单元格坐标设置对应静态文本
     *
     * @param colX 列（横坐标），索引从1开始
     * @param rowY 行（纵坐标），索引从1开始
     * @param text 内容
     * @return 可以链式调用
     */
    public CompTable setCell(int colX, int rowY, String text) {
        CompText compText = new CompText(text);
        return setCell(colX, rowY, compText);
    }

    /**
     * 在上次编辑的单元格下方设置对应组件
     *
     * @param comp 单元格组件
     * @return 可以链式调用
     */
    public CompTable setCell(Comp<?> comp) {
        return setCell(pointX, pointY, comp);
    }

    /**
     * 在上次编辑的单元格下方设置对应静态文本
     *
     * @param text 内容
     * @return 可以链式调用
     */
    public CompTable setCell(String text) {
        CompText compText = new CompText(text);
        return setCell(compText);
    }

    /**
     * 为指定列设置对齐方式
     *
     * @param colX  列号，索引从1开始
     * @param align 对齐方式 CompTable.LEFT CompTable.CENTER CompTable.RIGHT
     * @return 可以链式调用
     */
    public CompTable setAlignment(int colX, int align) {
        alignments.put(colX - 1, align);
        return this;
    }

    /**
     * 在表格最后一行追加单行
     *
     * @return 可以链式调用
     */
    public CompTable appendRow() {
        cells.add(new HashMap<>());
        return this;
    }

    /**
     * 在表格最后一行追加多行
     *
     * @param n 要追加的行数
     * @return 可以链式调用
     */
    public CompTable appendRows(int n) {
        for (int i = 0; i < n; i++) {
            appendRow();
        }
        return this;
    }

    /**
     * 在指定行前插入单行
     *
     * @param rowY 行号，索引从1开始
     * @return 可以链式调用
     */
    public CompTable insertRow(int rowY) {
        // 如果指定行不存在，则直接在最后一行追加
        if (rowY >= cells.size()) return appendRow();
        // 插入新行
        cells.add(rowY - 1, new HashMap<>());
        return this;
    }

    /**
     * 在指定行前插入多行
     *
     * @param rowY 行号，索引从1开始
     * @param n    要插入的行数
     * @return 可以链式调用
     */
    public CompTable insertRows(int rowY, int n) {
        for (int i = 0; i < n; i++) {
            insertRow(rowY);
        }
        return this;
    }

    /**
     * 删除行
     *
     * @param rowY 行号，索引从1开始
     * @return 可以链式调用
     */
    public CompTable deleteRow(int rowY) {
        cells.remove(rowY - 1);
        return this;
    }

    /**
     * 在表格指定位置绘制水平线
     *
     * @return 可以链式调用
     */
    public CompTable drawLine(int rowY) {
        horizontals.add(rowY);
        return this;
    }

    /**
     * 绘制表格全部框线
     *
     * @param b 是否绘制全部框线
     * @return 可以链式调用
     */
    public CompTable drawFullBorder(boolean b) {
        this.isFullBorder = b;
        return this;
    }

    /**
     * 不绘制表格任何框线
     *
     * @param b 关闭绘制框线
     * @return 可以链式调用
     */
    public CompTable drawNoneBorder(boolean b) {
        this.isNoneBorder = b;
        return this;
    }

    /**
     * 表格转置
     * <hr />
     * 注意：转置不会操作表格本身，只会在表格渲染时以转置形式输出
     *
     * @param b 是否转置输出
     * @return 可以链式调用
     */
    public CompTable transpositional(boolean b) {
        this.isTranspositional = b;
        return this;
    }

    public CompTable() {
        initTable();
    }

    // 初始化表格
    private void initTable() {
        cells = new LinkedList<>();
        alignments = new HashMap<>();
        horizontals = new ArrayList<>();
        this.isDirty = true; // 表格内部皆为组件，需要让表格每次都更新自己的缓存
    }

    // 预渲染
    private void toPreCache() {
        // 正常渲染
        if (!isTranspositional) preCache = new String[rowSize][colSize];
        // 转置渲染
        if (isTranspositional) preCache = new String[colSize][rowSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                String temp;
                try {
                    temp = cells.get(i).get(j).toString();
                } catch (Exception ignore) {
                    temp = ""; // 说明此单元格为空
                }
                if (!isTranspositional) {
                    preCache[i][j] = temp;
                } else {
                    preCache[j][i] = temp;
                }
            }
        }
    }

    // 栅格化渲染
    private void toRasterizeCache() {
        rasterizeCache = new String
                [getRasterizeRow(preCache.length)]
                [preCache[0].length];
        for (String[] strings : rasterizeCache) {
            Arrays.fill(strings, "");
        }
        for (int i = 0; i < preCache.length; i++) {
            for (int j = 0; j < preCache[i].length; j++) {
                String[] lines = preCache[i][j].split("\n");
                for (int k = 0; k < lines.length; k++) {
                    rasterizeCache[k + getRasterizeRow(i)][j] = lines[k];
                }
            }
        }
    }

    // 初始化栅格化行统计
    private void initRasterizeRow() {
        // 存储 preCache 中每个单元中的行高，并计算每行的最大行高
        int[][] rowsHeight = new int[preCache.length][preCache[0].length];
        maxRowsHeight = new int[preCache.length];
        for (int i = 0; i < preCache.length; i++) {
            for (int j = 0; j < preCache[0].length; j++) {
                long count = preCache[i][j].chars().filter(c -> c == '\n').count();
                rowsHeight[i][j] = (int) count + 1;
            }
            maxRowsHeight[i] = Arrays.stream(rowsHeight[i]).max().getAsInt();
        }
    }

    // 表格行获取栅格化行
    private int getRasterizeRow(int row) {
        int result = 0;
        for (int i = 0; i < row; i++) {
            try {
                result += maxRowsHeight[i];
            } catch (Exception ignore) {
                return result; // 越界说明已经达到最大值，这一步算是对支持转置的妥协
            }
        }
        return result;
    }

    // 栅格化行获取表格行(-1为该行是表格行子行，不存在)
    private int getPreRow(int row) {
        for (int i = 0; i < preCache.length; i++) {
            if (row == getRasterizeRow(i)) {
                return i;
            }
        }
        return -1;
    }

    // 栅格化行是否是表格行的内容行
    private boolean isContent(int row) {
        int current = getPreRow(row);
        return current > 0 && current < preCache.length;
    }

    // 文本行渲染器
    private String dataLineRender(int row, int[] maxColsWidth) {
        StringBuilder sb = new StringBuilder();
        // 绘制左边的边框线
        sb.append(brush(TableSymbol.VERTICAL));
        // 开始遍历元素，逐个添加分隔线
        for (int i = 0; i < maxColsWidth.length; i++) {
            int align = CompTable.LEFT;
            try {
                align = alignments.get(i);
            } catch (Exception ignore) {
            }
            sb.append(dataLineFormat(rasterizeCache[row][i], maxColsWidth[i], align));
            sb.append(brush(TableSymbol.VERTICAL));
        }
        sb.append("\n");
        return sb.toString();
    }

    // 行元素格式化（块组件也借用了此方法进行渲染）
    protected static String dataLineFormat(String text, int width, int align) {
        String blank = " ".repeat(width - getLength(text));
        if (align == CompTable.CENTER) {
            // 居中对齐
            int half = blank.length() / 2;
            return " ".repeat(half) + text + " ".repeat(blank.length() - half);
        }
        if (align == CompTable.RIGHT) {
            // 右对齐
            return blank + text;
        }
        // 默认情况，左对齐
        return text + blank;
    }

    // 横线渲染器
    private String horizontalRender(int row, int[] maxColsWidth) {
        StringBuilder sb = new StringBuilder();
        if (row == 0) {
            // 渲染表头
            sb.append(brush(TableSymbol.TOP_LEFT_CORNER));
            for (int i = 0; i < maxColsWidth.length; i++) {
                sb.append(brush(TableSymbol.HORIZONTAL).repeat(maxColsWidth[i]));
                sb.append(brush(TableSymbol.TOP_T));
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(brush(TableSymbol.TOP_RIGHT_CORNER));
            sb.append("\n");
            return sb.toString();
        } else if (row == getRasterizeRow(preCache.length)) {
            // 渲染表尾
            sb.append(brush(TableSymbol.BOTTOM_LEFT_CORNER));
            for (int i = 0; i < maxColsWidth.length; i++) {
                sb.append(brush(TableSymbol.HORIZONTAL).repeat(maxColsWidth[i]));
                sb.append(brush(TableSymbol.BOTTOM_T));
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(brush(TableSymbol.BOTTOM_RIGHT_CORNER));
            return sb.toString();
        } else {
            // 渲染中间横线
            sb.append(brush(TableSymbol.LEFT_T));
            for (int i = 0; i < maxColsWidth.length; i++) {
                sb.append(brush(TableSymbol.HORIZONTAL).repeat(maxColsWidth[i]));
                sb.append(brush(TableSymbol.CROSS));
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(brush(TableSymbol.RIGHT_T));
            sb.append("\n");
            return sb.toString();
        }
    }

    // 表渲染器
    private String tableRender() {
        // 得到每列最大宽度
        int[] maxColsWidth = new int[rasterizeCache[0].length];
        for (String[] rows : rasterizeCache) {
            for (int j = 0; j < rasterizeCache[0].length; j++) {
                maxColsWidth[j] = Math.max(maxColsWidth[j], getLength(rows[j]));
            }
        }
        // 判断类型，交给对应行渲染器
        StringBuilder sb = new StringBuilder();
        // 渲染表头
        sb.append(horizontalRender(0, maxColsWidth));
        for (int i = 0; i < rasterizeCache.length; i++) {
            ArrayList<Integer> rasterizeHorizontals = new ArrayList<>();
            horizontals.forEach(integer -> rasterizeHorizontals.add(getRasterizeRow(integer)));
            if (rasterizeHorizontals.contains(i) || ((isFullBorder || isTranspositional) && isContent(i))) {
                // 中间横线
                sb.append(horizontalRender(i, maxColsWidth));
            }
            // 文本行
            sb.append(dataLineRender(i, maxColsWidth));
        }
        // 渲染表尾
        sb.append(horizontalRender(rasterizeCache.length, maxColsWidth));
        return sb.toString();
    }

    // 渲染画笔
    private String brush(TableSymbol s) {
        if (isNoneBorder) return " ";
        return s.toString();
    }

    //     真实长度计算（中文字符认定为2个长度）（块组件也借用了此方法进行渲染）
    protected static int getLength(String str) {
        int length = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch >= '\u4E00' && ch <= '\u9FFF' || (ch >= '\uF900' && ch <= '\uFAFF')
                    || ch >= '\u3000' && ch <= '\u303F' || (ch >= '\uFF00' && ch <= '\uFFEF')) {
                length += 2; // 中文字符或扩展区域的字符，计为2
            } else {
                length += 1; // 其他字符计为1
            }
        }
        return length;
    }

    @Override
    protected String thisRender() {
        String renderTable;
        toPreCache(); // 预渲染
        initRasterizeRow(); // 统计栅格化行
        toRasterizeCache(); // 栅格化渲染
        renderTable = tableRender(); // 表渲染
        this.data.put(Comp.TEXT, renderTable);
        return super.thisRender();
    }

    @Override
    @Deprecated(since = "0 表格不支持绑定更新回调")
    @InvalidUsage(reason = "表格不支持绑定更新回调")
    public CompTable ref(ObjectRefresh refresh) {
        return this;
    }
}
