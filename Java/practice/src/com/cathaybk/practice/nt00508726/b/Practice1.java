package com.cathaybk.practice.nt00508726.b;

public class Practice1 {
    /*【實作練習 1 九 九乘法表 】 套 件名稱： c om.cathaybk. practice . 行 編 ).b
    (1) 由左至右顯示九九乘法乘積之格式為「被乘數 乘數 乘積」，被乘數固定不變，乘數為 1~9 之變動數值
    (2) 由上至下顯示九九乘法之乘積，乘數固定不變，被乘數為 2 ~9 之變動數值
    (3) 顯示九九乘法表由上至下每一列須以「被乘數」對齊
    * */
    public static void main(String[] args) {
        int result = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                result = i * j;
                sb.append(i).append("*").append(j).append(" =").append(String.format("%2d", result)).append(" ");

            }
            System.out.println(sb.toString());
            sb.setLength(0);
        }
    }
}
