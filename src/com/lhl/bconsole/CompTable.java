package com.lhl.bconsole;

/**
 * 组件实现类 - 表格组件
 * <hr />
 * ref 在此组件中无效
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/17_2:18
 */
public class CompTable extends Comp<CompTable> {

    public static final String ADAPTIVE_TABLE = "0"; // 自适应动态表(m*n)
    public static final String BLOCK = "1"; // 文本块(1*1)

    private CompTable[][] tableContent; // 表格内容
    private String textContent; // 文本块内容

    @Override
    public CompTable setComp(int type) {
        return null;
    }

    @Override
    public CompTable setComp(String type) {
        return null;
    }

    @Override
    public CompTable ref(StringRefresh refresh) {
        return null;
    }
}
