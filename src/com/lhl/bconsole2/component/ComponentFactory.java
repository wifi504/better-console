package com.lhl.bconsole2.component;

/**
 * 组件工厂
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/3_1:15
 */
public final class ComponentFactory {

    // ---------------- ...Text 文本组件 ---------------- //

    /**
     * 根据 文本内容 实例化行级文本组件
     *
     * @param text 文本内容
     * @return Text
     */
    public Text newSpanText(String text) {
        return new Text(text).setLayout(Component.INLINE);
    }

    /**
     * 根据 文本内容 实例化块级文本组件
     *
     * @param text 文本内容
     * @return Text
     */
    public Text newDivText(String text) {
        return new Text(text).setLayout(Component.BLOCK);
    }

    // ---------------- ArtText 艺术字组件 ---------------- //

    /**
     * 根据 指定文本 实例化艺术字组件
     *
     * @param text 指定文本
     * @return ArtText
     */
    public ArtText newArtText(String text) {
        return new ArtText(text);
    }

    // ---------------- ...Block 块组件 ---------------- //

    /**
     * 根据 文本内容 实例化普通块组件
     *
     * @param text 文本内容
     * @return Block
     */
    public Block newNormalBlock(String text) {
        return new Block().setContent(text);
    }

    /**
     * 根据 任意组件 实例化普通块组件
     *
     * @param component 任意组件
     * @return Block
     */
    public Block newNormalBlock(Component<?> component) {
        return new Block().setContent(component);
    }

    /**
     * 根据 文本内容 实例化加粗块组件
     *
     * @param text 文本内容
     * @return Block
     */
    public Block newBoldBlock(String text) {
        return new Block().setContent(text).setBorder(Block.BOLD);
    }

    /**
     * 根据 任意组件 实例化加粗块组件
     *
     * @param component 任意组件
     * @return Block
     */
    public Block newBoldBlock(Component<?> component) {
        return new Block().setContent(component).setBorder(Block.BOLD);
    }

    /**
     * 根据 文本内容 实例化无边框块组件
     *
     * @param text 文本内容
     * @return Block
     */
    public Block newNoneBlock(String text) {
        return new Block().setContent(text).setBorder(Block.NONE);
    }

    /**
     * 根据 任意组件 实例化无边框块组件
     *
     * @param component 任意组件
     * @return Block
     */
    public Block newNoneBlock(Component<?> component) {
        return new Block().setContent(component).setBorder(Block.NONE);
    }

    // ---------------- ...Hr 水平线组件 ---------------- //

    /**
     * 根据 默认宽度 实例化普通水平线组件
     * <hr />
     * 水平线字符：'─'<br />
     * 水平线宽度：60
     *
     * @return Hr
     */
    public Hr newNormalHr() {
        return new Hr();
    }

    /**
     * 根据 设定宽度 实例化普通水平线组件
     * <hr />
     * 水平线字符：'─'
     *
     * @param width 设定宽度
     * @return Hr
     */
    public Hr newNormalHr(int width) {
        return new Hr().setWidth(width);
    }

    /**
     * 根据 默认宽度 实例化加粗水平线组件
     * <hr />
     * 水平线字符：'═'<br />
     * 水平线宽度：60
     *
     * @return Hr
     */
    public Hr newBoldHr() {
        return new Hr().setSymbol(TableSymbol.DOUBLE_HORIZONTAL.toString());
    }

    /**
     * 根据 设定宽度 实例化加粗水平线组件
     * <hr />
     * 水平线字符：'═'<br />
     *
     * @param width 设定宽度
     * @return Hr
     */
    public Hr newBoldHr(int width) {
        return new Hr().setWidth(width)
                .setSymbol(TableSymbol.DOUBLE_HORIZONTAL.toString());
    }

    /**
     * 根据 自定义 实例化加粗水平线组件
     * <hr />
     *
     * @param symbol 设定水平线字符
     * @param width  设定宽度
     * @return Hr
     */
    public Hr newCustomHr(String symbol, int width) {
        return new Hr().setSymbol(symbol).setWidth(width);
    }

    // ---------------- Bar 进度条组件 ---------------- //

    /**
     * 实例化一个原始的进度条组件
     *
     * @return Bar
     */
    public Bar newBar() {
        return new Bar();
    }

    // ---------------- Table 表格组件 ---------------- //

    /**
     * 实例化一个空的表格组件<br />
     * 表格组件既可以用来展示数据，也可以用来绘图排版
     *
     * @return Table
     */
    public Table newTable() {
        return new Table();
    }

    // ---------------- View 视图组件 ---------------- //

    /**
     * 实例化一个空的视图组件
     *
     * @return View
     */
    public View newView() {
        return new View();
    }
}
