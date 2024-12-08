package com.lhl.bconsole2.component.preset;

import com.lhl.bconsole2.component.*;

/**
 * BConsole 内置错误页
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2024/12/9_2:52
 */
public class ErrorView extends View {

    protected ErrorView(String title, String detail) {
        int width = 80;

        ArtText headArtText = cf.newArtText("ERROR!");
        Text titleText = cf.newDivText("$v$").ref(v -> v.bind(title));
        Text detailText = cf.newDivText("$v$").ref(v -> v.bind(detail));

        ComponentsGroup<Block> blockGroup = new ComponentsGroup<>();
        Block headBlock = cf.newNoneBlock(headArtText + "抱歉！这里似乎发生了错误...").joinGroup(blockGroup);
        Block titleBlock = cf.newNoneBlock("事件名称：" + titleText + "\n事件详情：").joinGroup(blockGroup);
        Block detailBlock = cf.newNoneBlock(detailText).joinGroup(blockGroup);

        blockGroup.applyToAll(block -> block.setWidth(80));

        View errPage = cf.newView();
        errPage.reg(headBlock)
                .reg(cf.newNormalHr(width))
                .reg(titleBlock)
                .reg(detailBlock);


        Block block = cf.newBoldBlock(errPage)
                .setWidth(width + 2)
                .setAlignment(Block.CENTER);

        reg(block);
    }
}
