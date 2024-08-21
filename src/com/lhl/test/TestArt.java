package com.lhl.test;

import com.lhl.bconsole.BConsole;
import com.lhl.bconsole.CompArtText;

import java.util.HashMap;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2024/8/21_13:13
 */
public class TestArt {
    public static void main(String[] args) throws InterruptedException {
        for (int c = '0'; c <= '9'; c++) {
//            System.out.printf("""
//                    artMap.put('%c', new String[]{
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    "",
//                                    ""
//                            });
//                    """, (char) c);
        }

//        String str = """
//                 ██████╗\s
//                ██╔══███╗
//                ██║  ╔██║
//                ███╗╔╝██║
//                ╚██████╔╝
//                 ╚═════╝\s
//                        \s
//                """;
//        String[] split = str.split("\n");
//        for (int i = 0; i < split.length - 1; i++) {
//            if (i < split.length - 2) {
//                System.out.println("\"" + split[i] + "\",");
//            } else {
//                System.out.println("\"" + split[i] + "\"");
//            }
//        }

//        HashMap<Character, String[]> map = new CompArtText("").getArtSymbol();
//        map.forEach((character, strings) -> {
//            int length = strings[0].length();
//            for (String s : strings) {
//                if (s.length() != length) {
//                    System.out.println(character);
//                }
//            }
//        });
//        StringBuilder sb = new StringBuilder("0123456789");
//        sb.delete(sb.length() - 3, sb.length());
//        sb.deleteCharAt(sb.length() - 1);
//        System.out.println(sb);

//        char c = 'A';
//        System.out.println(c ^= 0b100000);

//        CompArtText compArtText = new CompArtText("1314");
//        BConsole.getScreen().reg(compArtText).turnON();
//        Thread.sleep(5000);
//        BConsole.getScreen().turnOFF();
    }
}
