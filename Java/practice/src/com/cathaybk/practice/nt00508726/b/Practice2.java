package com.cathaybk.practice.nt00508726.b;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Practice2 {
    /*【實作練習 2 樂透 程式 】 套 件名稱： c om.cathaybk. practice . 行 編 ).b
    (1)從 1~49 ，隨機抓取六個不同的數字 ，且 數 字不 得重覆 。
    (2)顯示排序前與排序後（由小到大）之結果。
    * */

    //    public static void main(String[] args) {
//        Random random = new Random();
//        int lottoNum = 0;
//        List<Integer> nums = new ArrayList<>();
//        while (nums.size() < 6) {
//            lottoNum = random.nextInt(49) + 1;
//            if(nums.contains(lottoNum)){
//                continue;
//            }
//            nums.add(lottoNum);
//        }
//        System.out.println("排序前: " + printList(nums));
////        Collections.sort(nums);
//        /*
//        *Collections.sort(nums, Comparator.reverseOrder());
//        *nums.sort((o1, o2) -> o2 - o1);
//        *由大到小排序
//         */
//        System.out.println("排序後: " + printList(nums));
//    }
//
//    public static String printList(List<Integer> nums) {
//        StringBuilder sb = new StringBuilder();
//        for (Integer num : nums) {
//            sb.append(num);
//            sb.append(" ");
//        }
//        return sb.toString();
//    }
    public static void main(String[] args) {
        /*
        *
        * */

        //
        List<Integer> numbers = IntStream.range(0, 50).boxed().collect(Collectors.toList());
        Collections.shuffle(numbers);
        List<Integer> lotto = numbers.stream().limit(5).collect(Collectors.toList());

        String beforeSort = lotto.stream().map(String::valueOf).collect(Collectors.joining(" ", "排序前: ", ""));
        System.out.println(beforeSort);
        Collections.sort(lotto);

        String afterSort = lotto.stream().map(String::valueOf).collect(Collectors.joining(" ", "排序後: ", ""));
        System.out.println(afterSort);

    }
}
