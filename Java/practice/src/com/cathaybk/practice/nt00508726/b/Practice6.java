package com.cathaybk.practice.nt00508726.b;


import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Practice6 {
    /*
     * 【實作練習6 – 熟悉資料分群累計】套件名稱：com.cathaybk.practice.nt12345(行編).b
     * 下載cars.csv 這個檔案(csv 檔為逗號分隔的檔案)
     *   將檔案在Java 中讀取，並做以下輸出
     * 一筆資料轉存成一個Map，並將所有資料放入List 中並利用Collections 類別的sort 方法，針對Price 這個欄位進行資料排序後(DESC)輸出成另一份檔案，ex: cars2.csv 。
     * */
    public static void main(String[] args) {
        List<Map<String, String>> lists;
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("Java評量_第6題cars.csv"));
             BufferedReader br = new BufferedReader(isr);
             BufferedWriter bw = new BufferedWriter(new FileWriter("cars2.csv"))) {
            String line;
            // 忽略第一行標題
            if ((line = br.readLine()) != null) {
            }
            lists = new ArrayList<>();
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
            Collections.sort(lists, (o1, o2) ->
                    new BigDecimal(o2.get("price")).compareTo(new BigDecimal(o1.get("price")))
            );
            // 儲存到car2.csv
            bw.write("Manufacturer,TYPE,Min.PRICE,Price\n");
            StringBuilder sb = new StringBuilder();
            for (Map<String, String> map : lists) {
                sb.append(map.get("manufacturer")).append(",").append(map.get("type")).append(",").append(map.get("min_price")).append(",").append(map.get("price")).append("\n");
                bw.write(sb.toString());
                sb.setLength(0);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            // 分組印出
            //用不是stream的寫法
//            TreeMap<String, List<Map<String, String>>> groupByManufacturer = new TreeMap<>();
//            for (Map<String, String> car : lists) {
//                String manufacturer = car.get("manufacturer");
//                if (!groupByManufacturer.containsKey(manufacturer)) {
//                    List<Map<String, String>> cars = new ArrayList<>();
//                    groupByManufacturer.put(manufacturer, cars);
//                }
//                groupByManufacturer.get(manufacturer).add(car);
//            }
//            BigDecimal total_price = BigDecimal.ZERO;
//            BigDecimal total_min_price = BigDecimal.ZERO;
//            for (String manufacturer : groupByManufacturer.keySet()) {
//                List<Map<String, String>> cars = groupByManufacturer.get(manufacturer);
//                BigDecimal total_manu_price = BigDecimal.ZERO;
//                BigDecimal total_manu_min_price = BigDecimal.ZERO;
//                for (Map<String, String> map : cars) {
//                    System.out.printf("%-14s%-9s%11s%7s\n", manufacturer, map.get("type"), map.get("min_price"), map.get("price"));
//                    total_manu_price = total_manu_price.add(new BigDecimal(map.get("price")));
//                    total_manu_min_price = total_manu_min_price.add(new BigDecimal(map.get("min_price")));
//
//                }
//                System.out.printf("%-22s%11s%7s\n", "小計", total_manu_min_price, total_manu_price);
//                total_price = total_price.add(total_manu_price);
//                total_min_price = total_min_price.add(total_manu_min_price);
//            }
//            System.out.printf("%-22s%11s%7s\n", "合計", total_min_price, total_price);
            //用stream寫
            System.out.printf("%-14s%-9s%11s%7s\n", "Manufacturer", "TYPE", "Min.PRICE", "Price");
            ArrayList<BigDecimal> totalCarsPrice = new ArrayList<>();
            ArrayList<BigDecimal> totalCarsMinPrice = new ArrayList<>();
            Map<String, List<Map<String, String>>> groupByManufacturer = lists.stream().collect(Collectors.groupingBy(map -> map.get("manufacturer"),
                    TreeMap::new, Collectors.toList()));
            groupByManufacturer.forEach((manufacturer, cars) -> {
                BigDecimal total_manu_price = BigDecimal.ZERO;
                BigDecimal total_manu_min_price = BigDecimal.ZERO;
                for (Map<String, String> map : cars) {
                    System.out.printf("%-14s%-9s%11s%7s\n", manufacturer, map.get("type"), map.get("min_price"), map.get("price"));
                    total_manu_price = total_manu_price.add(new BigDecimal(map.get("price")));
                    total_manu_min_price = total_manu_min_price.add(new BigDecimal(map.get("min_price")));
                }
                System.out.printf("%-22s%11s%7s\n", "小計", total_manu_min_price, total_manu_price);
                totalCarsPrice.add(total_manu_price);
                totalCarsMinPrice.add(total_manu_min_price);
            });
            System.out.printf("%-22s%11s%7s\n", "合計", totalCarsMinPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add), totalCarsPrice.stream().reduce(BigDecimal.ZERO, BigDecimal::add));


    }
}
