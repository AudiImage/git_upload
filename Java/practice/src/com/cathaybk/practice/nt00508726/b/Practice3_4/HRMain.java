package com.cathaybk.practice.nt00508726.b.Practice3_4;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HRMain {
    /*【實作練習3 – 員工資料存取】套件名稱：com.cathaybk.practice.nt12345(行編).b
        (1)使用繼承關係，如下UML 類別圖所示，建立一個計算員工薪水的薪資系統。
        (2)如 上述 需 求， 員 工分為 2 種：業 務員、主管
        (3)提 示： HRMain 類 別 . 主方 法
            各類別的
            欄位須封裝成 getter 、 setter 並 使 用 List< Employee 。
            由於 Employee 類 別 是 Supervisor 、 Sales 的 父類別 ，故可以集中 在同一個 List 中 。
    * */
    public static void main(String[] args) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Sales("張志誠", "信用卡部", 35000, 6000));
        employeeList.add(new Sales("林大鈞", "保代部", 38000, 4000));
        employeeList.add(new Supervisor("李中白", "資訊部", 65000));
        employeeList.add(new Supervisor("林小中", "理財部", 80000));
        for (Employee employee : employeeList) {
            employee.printInfo();
        }
        System.out.println("------------------");


        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.csv"), StandardCharsets.UTF_8))
        ) {
            int payment;
            for (Employee employee : employeeList) {
                if (employee instanceof Supervisor) {
                    Supervisor supervisor = (Supervisor) employee;
                    payment = supervisor.getPayment();

                } else {
                    Sales sales = (Sales) employee;
                    payment = sales.getPayment();
                }
                System.out.println(employee.getName() + "," + payment);
                bufferedWriter.write(employee.getName() + "," + payment + "\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
