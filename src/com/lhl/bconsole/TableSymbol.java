package com.lhl.bconsole;

/**
 * 制表符号枚举集
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time: 2024/8/17_2:40
 */

@SuppressWarnings("unused")
public enum TableSymbol {

    HORIZONTAL('─'),
    VERTICAL('│'),
    CROSS('┼'),
    TOP_LEFT_CORNER('┌'),
    TOP_RIGHT_CORNER('┐'),
    BOTTOM_LEFT_CORNER('└'),
    BOTTOM_RIGHT_CORNER('┘'),
    TOP_T('┬'),
    BOTTOM_T('┴'),
    LEFT_T('├'),
    RIGHT_T('┤'),
    DOUBLE_HORIZONTAL('═'),
    DOUBLE_VERTICAL('║'),
    DOUBLE_TOP_LEFT_CORNER('╔'),
    DOUBLE_TOP_RIGHT_CORNER('╗'),
    DOUBLE_BOTTOM_LEFT_CORNER('╚'),
    DOUBLE_BOTTOM_RIGHT_CORNER('╝');

    private final char character;

    TableSymbol(char character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }
}
