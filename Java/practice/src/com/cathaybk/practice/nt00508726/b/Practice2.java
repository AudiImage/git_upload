package com.cathaybk.practice.nt00508726.b;

import java.util.*;

public class Practice2 {
    /*【實作練習 2 樂透 程式 】 套 件名稱： c om.cathaybk. practice . 行 編 ).b
    (1)從 1~49 ，隨機抓取六個不同的數字 ，且 數 字不 得重覆 。
    (2)顯示排序前與排序後（由小到大）之結果。

    Comparactor由大到小 Comparator.naturalOrder
    * */
    public static void main(String[] args) {
        Random random = new Random();
        int lottoNum = 0;
        List<Integer> nums = new ArrayList<>();
        while (nums.size() < 6) {
            lottoNum = random.nextInt(49) + 1;
            if(nums.contains(lottoNum)){
                continue;
            }
            nums.add(lottoNum);
        }
        System.out.println("排序前: " + printList(nums));
        nums.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
//        Collections.sort(nums, Comparator.reverseOrder());
        System.out.println("排序後: " + printList(nums));
    }

    public static String printList(List<Integer> nums) {
        StringBuilder sb = new StringBuilder();
        for (Integer num : nums) {
            sb.append(num);
            sb.append(" ");
        }
        return sb.toString();
    }
}
