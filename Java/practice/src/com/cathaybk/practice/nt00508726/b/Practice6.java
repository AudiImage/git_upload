package com.cathaybk.practice.nt00508726.b;


import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Practice6 {
    /*
     * 【實作練習6 – 熟悉資料分群累計】套件名稱：com.cathaybk.practice.nt12345(行編).b
     * 下載cars.csv 這個檔案(csv 檔為逗號分隔的檔案)
     *   將檔案在Java 中讀取，並做以下輸出
     * 一筆資料轉存成一個Map，並將所有資料放入List 中並利用Collections 類別的sort 方法，針對Price 這個欄位進行資料排序後(DESC)輸出成另一份檔案，ex: cars2.csv 。
     * */
    public static void main(String[] args) {
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("Java評量_第6題cars.csv"));
             BufferedReader br = new BufferedReader(isr);
             BufferedWriter bw = new BufferedWriter(new FileWriter("cars2.csv"))) {
            java.lang.String line;
            // 忽略第一行標題
            if ((line = br.readLine()) != null) {
            }
            List<Map<String, String>> lists = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String item[] = line.split(",");
                String manufacturer = item[0].trim();
                String type = item[1].trim();
                String min_price = item[2].trim();
                String price = item[3].trim();
                lists.add(new HashMap<String, String>() {{
                    put("manufacturer", manufacturer);
                    put("type", type);
                    put("min_price", min_price);
                    put("price", price);
                }});
            }
            System.out.println(lists);
            Collections.sort(lists, new Comparator<Map<String, String>>() {
                @Override
                public int compare(Map<String, String> o1, Map<String, String> o2) {
                    BigDecimal price1 = new BigDecimal(o1.get("price"));
                    BigDecimal price2 = new BigDecimal(o2.get("price"));
                    return price2.compareTo(price1);
                }
            });
            // 儲存到car2.csv
            bw.write("Manufacturer,TYPE,Min.PRICE,Price\n");
            StringBuilder sb = new StringBuilder();
            for (Map<String, String> map : lists) {
                sb.append(map.get("manufacturer")).append(",").append(map.get("type")).append(",").append(map.get("min_price")).append(",").append(map.get("price")).append("\n");
                bw.write(sb.toString());
                sb.setLength(0);
            }
            // 分組印出
            // 先得到全部的Manufacturer變成一個list 按照字母順序排好
            TreeSet<String> manufactorSet = new TreeSet<>();
            for (Map<String, String> map : lists) {
                manufactorSet.add(map.get("manufacturer"));
            }
            BigDecimal total_min_prices = new BigDecimal(0);
            BigDecimal total_prices = new BigDecimal(0);
            System.out.printf("%-14s%-9s%11s%7s\n", "Manufacturer", "TYPE", "Min.PRICE", "Price");
            for (String manufacturer : manufactorSet) {
                BigDecimal total_min_price = new BigDecimal(0);
                BigDecimal total_price = new BigDecimal(0);
                // 獲得該Manufacturer下的所有資料
                for (Map<String, String> map : lists) {
                    if (manufacturer.equals(map.get("manufacturer"))) {
                        System.out.printf("%-14s%-9s%11s%7s\n", manufacturer, map.get("type"), map.get("min_price"), map.get("price"));
                        total_price = total_price.add(new BigDecimal(map.get("price")));
                        total_min_price = total_min_price.add(new BigDecimal(map.get("min_price")));
                    }
                }
                total_prices = total_prices.add(total_price);
                total_min_prices = total_min_prices.add(total_min_price);
                System.out.printf("%-22s%11s%7s\n", "小計", total_min_price, total_price);
            }
            System.out.printf("%-22s%11s%7s\n", "合計", total_min_prices, total_prices);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
