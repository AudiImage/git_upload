package com.cathaybk.practice.nt00508726.b;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extend2 {
    public static void main(String[] args) {
        String input = "2024/08/09";  // 要测试的字符串
        String regex = "(\\d{3})\\.(\\d{2})";  // 正则表达式：3位整数和2位小数
        String regex2 = "(\\d{4})\\/(\\d{2})\\/(\\d{2})";

        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            // 提取整数部分和小数部分
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);


            System.out.println(year + "-" + month + "-" + day);
            LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            switch (date.getDayOfWeek()){
                case SATURDAY:
                    System.out.println("星期六");
                case SUNDAY:
                    System.out.println("星期天");
                case MONDAY:
                    System.out.println("星期一");
                case TUESDAY:
                    System.out.println("星期二");
                case WEDNESDAY:
                    System.out.println("星期三");
                case THURSDAY:
                    System.out.println("星期四");
                case FRIDAY:
                    System.out.println("星期五");
            }
        } else {
            System.out.println("The input does not match the format.");
        }
    }

}
