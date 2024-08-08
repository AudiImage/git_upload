package com.cathaybk.practice.nt00508726.b;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;


public class Practice5 {




    public static void main(String[] args) {
        //保存用户输入的年份
        int year = LocalDate.now().getYear();
        //保存每个月的天数
        int days = 0;

        //接受用户输入的年份
        Scanner in = new Scanner(System.in);
        System.out.print("輸入介於1-12之間的整數m:");
        int month = in.nextInt();

        //得到一个Calendar对象
        Calendar c = Calendar.getInstance();


        System.out.printf("      %d年%d月            \n", year, month);
        System.out.println("--------------------------");
        System.out.println("日\t 一\t 二\t 三\t 四\t 五\t 六");
        System.out.println("==========================");

        // 判断当前月份的天数
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            days = 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            days = 30;
        }
        if (month == 2) {
            //闰年判断
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                days = 29;
            } else {
                days = 28;
            }
        }


        c.set(year, month - 1, 1);
        //求出第一天是星期几
        int FirstDayInWeek = c.get(Calendar.DAY_OF_WEEK) - 1;

        //用来控制换行
        int cnt = 0;

        //前面的空位
        for (int j = 0; j < FirstDayInWeek; j++) {
            System.out.print("    ");
            cnt++;
        }

        //打印当前月份日历
        for (int i = 1; i <= days; i++) {
            if (cnt == 7) {
                System.out.printf("\n");
                cnt = 0;
            }
            System.out.printf("%2d  ", i);
            cnt++;
        }

        System.out.print("\n");

    }





}
