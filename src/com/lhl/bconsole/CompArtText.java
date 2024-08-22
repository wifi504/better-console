package com.lhl.bconsole;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 组件实现类 - 艺术字组件
 * 如果字体库没有字体则会跳过输出
 * <hr />
 * ref 绑定 public static 修饰的变量
 *
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/21_11:11
 */
public class CompArtText extends Comp<CompArtText> {

    private CompText text; // 艺术字的内容，可以接受"\n"
    private int verticalSpace = 1; // 设置行间距
    private int horizontalSpace = 1; // 设置字符间距
    private HashMap<Character, String[]> artSymbol; // 艺术字模板
    private String[] strings = new String[6];

    public CompArtText(String text) {
        initArtText();
        artSymbol = new ArtSymbol().getArtMap();
        this.setText(text);
    }

    /**
     * 根据指定内容创建艺术字组件
     *
     * @param text 文本内容，如果有变量需要实时展示，请用$v$魔法变量占位并绑定变量
     */
    public CompArtText setText(String text) {
        this.text = new CompText(text);
        return this;
    }

    /**
     * 设置行间距
     *
     * @param verticalSpace int 行距，默认1
     * @return 可以链式调用
     */
    public CompArtText setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        return this;
    }

    /**
     * 设置字符间距
     *
     * @param horizontalSpace int 字符间距，默认1
     * @return 可以链式调用
     */
    public CompArtText setHorizontalSpace(int horizontalSpace) {
        this.horizontalSpace = horizontalSpace;
        return this;
    }

    // 根据文本内容渲染对应艺术字
    private String artRender(String text) {
        StringBuilder sb = new StringBuilder();
        String[] split = text.split("\n");
        for (String s : split) {
            // 渲染每一行的文本
            sb.append(artTextLineRender(s)).append("\n".repeat(verticalSpace));
        }
        // 删除额外的换行
        sb.delete(sb.length() - verticalSpace, sb.length());
        return sb.toString();
    }

    // 渲染单行艺术字
    private String artTextLineRender(String text) {
        StringBuilder sb = new StringBuilder();
        // 艺术字有6行高
        char[] chars = text.toCharArray();
        for (int i = 0; i < 6; i++) {
            for (char c : chars) {
                sb.append(getArtChar(c)[i]).append(" ".repeat(horizontalSpace));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // 获得单个 ArtText
    private String[] getArtChar(char c) {
        if (c >= 'a' && c <= 'z') {
            c ^= 0b100000;
        }
        try {
            strings = new ArtSymbol().getArtMap().get(c);
        } catch (Exception ignore) { // 说明字体库内没有这个字体
        }
        return strings;
    }

    @Override
    protected String thisRender() {
        this.data.put(Comp.TEXT, artRender(text.toString()));
        return super.thisRender();
    }

    private void initArtText() {
        Arrays.fill(strings, "");
        this.isDirty = true;
    }

    @Override
    public CompArtText ref(ObjectRefresh refresh) {
        // 让 Text 组件更新内容
        text.refAction = refresh;
        text.isDirty = true;
        return this;
    }
}

/**
 * ASCII Art 艺术字
 * 字体：ANSI Shadow
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/21_12:54
 */
class ArtSymbol {

    private HashMap<Character, String[]> artMap;

    ArtSymbol() {
        initSymbols();
    }

    protected HashMap<Character, String[]> getArtMap() {
        return artMap;
    }

    // 在内存中初始化艺术字对照表
    private void initSymbols() {
        artMap = new HashMap<>();
        artMap.put('A', new String[]{
                " █████╗ ",
                "██╔══██╗",
                "███████║",
                "██╔══██║",
                "██║  ██║",
                "╚═╝  ╚═╝"
        });
        artMap.put('B', new String[]{
                "██████╗ ",
                "██╔══██╗",
                "██████╔╝",
                "██╔══██╗",
                "██████╔╝",
                "╚═════╝ "
        });
        artMap.put('C', new String[]{
                " ██████╗",
                "██╔════╝",
                "██║     ",
                "██║     ",
                "╚██████╗",
                " ╚═════╝"
        });
        artMap.put('D', new String[]{
                "██████╗ ",
                "██╔══██╗",
                "██║  ██║",
                "██║  ██║",
                "██████╔╝",
                "╚═════╝ "
        });
        artMap.put('E', new String[]{
                "███████╗",
                "██╔════╝",
                "█████╗  ",
                "██╔══╝  ",
                "███████╗",
                "╚══════╝"
        });
        artMap.put('F', new String[]{
                "███████╗",
                "██╔════╝",
                "█████╗  ",
                "██╔══╝  ",
                "██║     ",
                "╚═╝     "
        });
        artMap.put('G', new String[]{
                " ██████╗ ",
                "██╔════╝ ",
                "██║  ███╗",
                "██║   ██║",
                "╚██████╔╝",
                " ╚═════╝ "
        });
        artMap.put('H', new String[]{
                "██╗  ██╗",
                "██║  ██║",
                "███████║",
                "██╔══██║",
                "██║  ██║",
                "╚═╝  ╚═╝"
        });
        artMap.put('I', new String[]{
                "██╗",
                "██║",
                "██║",
                "██║",
                "██║",
                "╚═╝"
        });
        artMap.put('J', new String[]{
                "     ██╗",
                "     ██║",
                "     ██║",
                "██   ██║",
                "╚█████╔╝",
                " ╚════╝ "
        });
        artMap.put('K', new String[]{
                "██╗  ██╗",
                "██║ ██╔╝",
                "█████╔╝ ",
                "██╔═██╗ ",
                "██║  ██╗",
                "╚═╝  ╚═╝"
        });
        artMap.put('L', new String[]{
                "██╗     ",
                "██║     ",
                "██║     ",
                "██║     ",
                "███████╗",
                "╚══════╝"
        });
        artMap.put('M', new String[]{
                "███╗   ███╗",
                "████╗ ████║",
                "██╔████╔██║",
                "██║╚██╔╝██║",
                "██║ ╚═╝ ██║",
                "╚═╝     ╚═╝"
        });
        artMap.put('N', new String[]{
                "███╗   ██╗",
                "████╗  ██║",
                "██╔██╗ ██║",
                "██║╚██╗██║",
                "██║ ╚████║",
                "╚═╝  ╚═══╝"
        });
        artMap.put('O', new String[]{
                " ██████╗ ",
                "██╔═══██╗",
                "██║   ██║",
                "██║   ██║",
                "╚██████╔╝",
                " ╚═════╝ "
        });
        artMap.put('P', new String[]{
                "██████╗ ",
                "██╔══██╗",
                "██████╔╝",
                "██╔═══╝ ",
                "██║     ",
                "╚═╝     "
        });
        artMap.put('Q', new String[]{
                " ██████╗ ",
                "██╔═══██╗",
                "██║   ██║",
                "██║▄▄ ██║",
                "╚██████╔╝",
                " ╚══▀▀═╝ "
        });
        artMap.put('R', new String[]{
                "██████╗ ",
                "██╔══██╗",
                "██████╔╝",
                "██╔══██╗",
                "██║  ██║",
                "╚═╝  ╚═╝"
        });
        artMap.put('S', new String[]{
                "███████╗",
                "██╔════╝",
                "███████╗",
                "╚════██║",
                "███████║",
                "╚══════╝"
        });
        artMap.put('T', new String[]{
                "████████╗",
                "╚══██╔══╝",
                "   ██║   ",
                "   ██║   ",
                "   ██║   ",
                "   ╚═╝   "
        });
        artMap.put('U', new String[]{
                "██╗   ██╗",
                "██║   ██║",
                "██║   ██║",
                "██║   ██║",
                "╚██████╔╝",
                " ╚═════╝ "
        });
        artMap.put('V', new String[]{
                "██╗   ██╗",
                "██║   ██║",
                "██║   ██║",
                "╚██╗ ██╔╝",
                " ╚████╔╝ ",
                "  ╚═══╝  "
        });
        artMap.put('W', new String[]{
                "██╗    ██╗",
                "██║    ██║",
                "██║ █╗ ██║",
                "██║███╗██║",
                "╚███╔███╔╝",
                " ╚══╝╚══╝ "
        });
        artMap.put('X', new String[]{
                "██╗  ██╗",
                "╚██╗██╔╝",
                " ╚███╔╝ ",
                " ██╔██╗ ",
                "██╔╝ ██╗",
                "╚═╝  ╚═╝"
        });
        artMap.put('Y', new String[]{
                "██╗   ██╗",
                "╚██╗ ██╔╝",
                " ╚████╔╝ ",
                "  ╚██╔╝  ",
                "   ██║   ",
                "   ╚═╝   "
        });
        artMap.put('Z', new String[]{
                "███████╗",
                "╚══███╔╝",
                "  ███╔╝ ",
                " ███╔╝  ",
                "███████╗",
                "╚══════╝"
        });
        artMap.put('0', new String[]{
                " ██████╗ ",
                "██╔══███╗",
                "██║  ╔██║",
                "███╗╔╝██║",
                "╚██████╔╝",
                " ╚═════╝ "
        });
        artMap.put('1', new String[]{
                " ██╗",
                "███║",
                "╚██║",
                " ██║",
                " ██║",
                " ╚═╝"
        });
        artMap.put('2', new String[]{
                "██████╗ ",
                "╚════██╗",
                " █████╔╝",
                "██╔═══╝ ",
                "███████╗",
                "╚══════╝"
        });
        artMap.put('3', new String[]{
                "██████╗ ",
                "╚════██╗",
                " █████╔╝",
                " ╚═══██╗",
                "██████╔╝",
                "╚═════╝ "
        });
        artMap.put('4', new String[]{
                "██╗  ██╗",
                "██║  ██║",
                "███████║",
                "╚════██║",
                "     ██║",
                "     ╚═╝"
        });
        artMap.put('5', new String[]{
                "███████╗",
                "██╔════╝",
                "███████╗",
                "╚════██║",
                "███████║",
                "╚══════╝"
        });
        artMap.put('6', new String[]{
                " ██████╗ ",
                "██╔════╝ ",
                "███████╗ ",
                "██╔═══██╗",
                "╚██████╔╝",
                " ╚═════╝ "
        });
        artMap.put('7', new String[]{
                "███████╗",
                "╚════██║",
                "    ██╔╝",
                "   ██╔╝ ",
                "   ██║  ",
                "   ╚═╝  "
        });
        artMap.put('8', new String[]{
                " █████╗ ",
                "██╔══██╗",
                "╚█████╔╝",
                "██╔══██╗",
                "╚█████╔╝",
                " ╚════╝ "
        });
        artMap.put('9', new String[]{
                " █████╗ ",
                "██╔══██╗",
                "╚██████║",
                " ╚═══██║",
                " █████╔╝",
                " ╚════╝ "
        });
        artMap.put(',', new String[]{
                "   ",
                "   ",
                "   ",
                "   ",
                "▄█╗",
                "╚═╝"
        });
        artMap.put('.', new String[]{
                "   ",
                "   ",
                "   ",
                "   ",
                "██╗",
                "╚═╝"
        });
        artMap.put('<', new String[]{
                "  ██╗",
                " ██╔╝",
                "██╔╝ ",
                "╚██╗ ",
                " ╚██╗",
                "  ╚═╝"
        });
        artMap.put('>', new String[]{
                "██╗  ",
                "╚██╗ ",
                " ╚██╗",
                " ██╔╝",
                "██╔╝ ",
                "╚═╝  "
        });
        artMap.put('/', new String[]{
                "    ██╗",
                "   ██╔╝",
                "  ██╔╝ ",
                " ██╔╝  ",
                "██╔╝   ",
                "╚═╝    "
        });
        artMap.put('?', new String[]{
                "██████╗ ",
                "╚════██╗",
                "  ▄███╔╝",
                "  ▀▀══╝ ",
                "  ██╗   ",
                "  ╚═╝   "
        });
        artMap.put(';', new String[]{
                "   ",
                "██╗",
                "╚═╝",
                "▄█╗",
                "▀═╝",
                "   "
        });
        artMap.put(':', new String[]{
                "   ",
                "██╗",
                "╚═╝",
                "██╗",
                "╚═╝",
                "   "
        });
        artMap.put('\'', new String[]{
                "███╗",
                "╚██╝",
                "▀═╝ ",
                "    ",
                "    ",
                "    "
        });
        artMap.put('\"', new String[]{
                "███╗███╗",
                "╚██╝╚██╝",
                "▀═╝ ▀═╝ ",
                "        ",
                "        ",
                "        "
        });
        artMap.put('|', new String[]{
                "██╗",
                "██║",
                "██║",
                "██║",
                "██║",
                "╚═╝"
        });
        artMap.put('\\', new String[]{
                "██╗    ",
                "╚██╗   ",
                " ╚██╗  ",
                "  ╚██╗ ",
                "   ╚██╗",
                "    ╚═╝"
        });
        artMap.put('[', new String[]{
                "███╗",
                "██╔╝",
                "██║ ",
                "██║ ",
                "███╗",
                "╚══╝"
        });
        artMap.put(']', new String[]{
                "███╗",
                "╚██║",
                " ██║",
                " ██║",
                "███║",
                "╚══╝"
        });
        artMap.put('{', new String[]{
                "  ███╗",
                "  ██╔╝",
                "████║ ",
                " ╚██║ ",
                "  ███╗",
                "  ╚══╝"
        });
        artMap.put('}', new String[]{
                "███╗  ",
                "╚██╗  ",
                " ████╗",
                " ██╔═╝",
                "███║  ",
                "╚══╝  "
        });
        artMap.put('-', new String[]{
                "      ",
                "      ",
                "█████╗",
                "╚════╝",
                "      ",
                "      "
        });
        artMap.put('_', new String[]{
                "        ",
                "        ",
                "        ",
                "        ",
                "███████╗",
                "╚══════╝"
        });
        artMap.put('+', new String[]{
                "       ",
                "  ██╗  ",
                "██████╗",
                "╚═██╔═╝",
                "  ╚═╝  ",
                "       "
        });
        artMap.put('=', new String[]{
                "      ",
                "█████╗",
                "╚════╝",
                "█████╗",
                "╚════╝",
                "      "
        });
        artMap.put('!', new String[]{
                "██╗",
                "██║",
                "██║",
                "╚═╝",
                "██╗",
                "╚═╝"
        });
        artMap.put('@', new String[]{
                " ██████╗ ",
                "██╔═══██╗",
                "██║██╗██║",
                "██║██║██║",
                "╚█║████╔╝",
                " ╚╝╚═══╝ "
        });
        artMap.put('#', new String[]{
                " ██╗ ██╗ ",
                "████████╗",
                "╚██╔═██╔╝",
                "████████╗",
                "╚██╔═██╔╝",
                " ╚═╝ ╚═╝ "
        });
        artMap.put('$', new String[]{
                "▄▄███▄▄╗",
                "██╔█═══╝",
                "███████╗",
                "╚══█═██║",
                "███████║",
                "╚═▀▀▀══╝"
        });
        artMap.put('%', new String[]{
                "██╗ ██╗",
                "╚═╝██╔╝",
                "  ██╔╝ ",
                " ██╔╝  ",
                "██╔╝██╗",
                "╚═╝ ╚═╝"
        });
        artMap.put('^', new String[]{
                " ███╗ ",
                "██╔██╗",
                "╚═╝╚═╝",
                "      ",
                "      ",
                "      "
        });
        artMap.put('&', new String[]{
                "   ██╗   ",
                "   ██║   ",
                "████████╗",
                "██╔═██╔═╝",
                "██████║  ",
                "╚═════╝  "
        });
        artMap.put('*', new String[]{
                "      ",
                "▄ ██╗▄",
                " ████╗",
                "▀╚██╔▀",
                "  ╚═╝ ",
                "      "
        });
        artMap.put('(', new String[]{
                " ██╗",
                "██╔╝",
                "██║ ",
                "██║ ",
                "╚██╗",
                " ╚═╝"
        });
        artMap.put(')', new String[]{
                "██╗ ",
                "╚██╗",
                " ██║",
                " ██║",
                "██╔╝",
                "╚═╝ "
        });

    }
}