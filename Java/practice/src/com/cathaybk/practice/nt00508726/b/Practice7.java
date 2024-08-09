package com.cathaybk.practice.nt00508726.b;


import java.sql.*;
import java.util.*;


public class Practice7 {

    private static final String CONN_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String USER_NAME = "student";
    private static final String PASSWORD = "student123456";
    private static final String SELECT_ALL_CARS_SQL = "select * from STUDENT.CARS";
    public static final String SELECT_CARS_SQL = "select * from STUDENT.CARS where MANUFACTURER =? and TYPE = ?";
    private static final String INSERT_CARS_SQL = "insert into STUDENT.CARS (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)";
    private static final String UPDATE_CAR_SQL = "update STUDENT.CARS set MIN_PRICE = ?, PRICE = ? where MANUFACTURER = ? and TYPE = ?";
    public static final String DELETE_CAR_SQL = "delete from STUDENT.CARS where MANUFACTURER = ? and TYPE = ?";
    private static final HashMap<String, String> questions;

    static {
        questions = new HashMap<>();
        questions.put("manufactor", "請輸入製造商:");
        questions.put("type", "請輸入類型:");
        questions.put("min_price", "請輸入底價:");
        questions.put("price", "請輸入售價:");
    }

    public static void main(String[] args) {
        //1. 先查詢全部的
        query_all();
        //2. 讓使用者自己選要的指令
        Scanner scanner = new Scanner(System.in);
        System.out.println("請選擇以下指令輸入: select、insert、update、delete");
        String input = scanner.next().toLowerCase();
        switch (input) {
            case "select":
                query(scanner);
                break;
            case "insert":
                insert(scanner);
                break;
            case "update":
                update(scanner);
                break;
            case "delete":
                delete(scanner);
                break;
            default:
                System.out.println("輸入錯誤，請重新輸入指令");
                break;


        }
    }

    public static void query_all() {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false); // 禁用自动提交

            try {
                try (PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_CARS_SQL)) {
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.printf("製造商：%s，型號：%s，售價：%s，底價：%s%n",
                                    rs.getString("MANUFACTURER"),
                                    rs.getString("TYPE"),
                                    rs.getString("PRICE"),
                                    rs.getString("MIN_PRICE"));
                        }
                    }
                }
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("操作失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
    }

    private static void query(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false); // 禁用自动提交
            // 改成兩層try
            try (PreparedStatement pstmt = conn.prepareStatement(SELECT_CARS_SQL);
                 ResultSet rs = pstmt.executeQuery()) {
                String manufactor = askQuestion(scanner, "manufactor", "String");
                String type = askQuestion(scanner, "type", "String");
                pstmt.setString(1, manufactor);
                pstmt.setString(2, type);
                while (rs.next()) {
                    System.out.printf("製造商：%s，型號：%s，售價：%s，底價：%s%n",
                            rs.getString("MANUFACTURER"),
                            rs.getString("TYPE"),
                            rs.getString("PRICE"),
                            rs.getString("MIN_PRICE"));
                }


                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("操作失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
    }


    private static void insert(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(INSERT_CARS_SQL)) {
                String manufactor = askQuestion(scanner, "manufactor", "String");
                String type = askQuestion(scanner, "type", "String");
                String price = askQuestion(scanner, "price", "int");
                String minPrice = askQuestion(scanner, "min_price", "int");
                pstmt.setString(1, manufactor);
                pstmt.setString(2, type);
                pstmt.setInt(3, Integer.parseInt(minPrice));
                pstmt.setInt(4, Integer.parseInt(price));
                pstmt.executeUpdate();
                System.out.println("新增成功!");


                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("操作失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }

    }

    private static void update(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_CAR_SQL)) {
                String manufactor = askQuestion(scanner, "manufactor", "String");
                String type = askQuestion(scanner, "type", "String");
                String price = askQuestion(scanner, "price", "int");
                String minPrice = askQuestion(scanner, "min_price", "int");
                pstmt.setInt(1, Integer.parseInt(minPrice));
                pstmt.setInt(2, Integer.parseInt(price));
                pstmt.setString(3, manufactor);
                pstmt.setString(4, type);
                pstmt.executeUpdate();
                System.out.println("更新成功!");

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("操作失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
    }

    private static void delete(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);
            try (PreparedStatement pstmt = conn.prepareStatement(DELETE_CAR_SQL)) {
                String manufactor = askQuestion(scanner, "manufactor", "String");
                String type = askQuestion(scanner, "type", "String");
                pstmt.setString(1, manufactor);
                pstmt.setString(2, type);
                pstmt.executeUpdate();
                System.out.println("刪除成功!");
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                System.out.println("操作失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }


    }

    /*
     * 對於非數字處理
     * */
    public static String askQuestion(Scanner scanner, String key, String type) {
        System.out.print(questions.get(key));
        String input = scanner.next();
        if (!isValid(input, type)) {
            System.out.println("輸入格式錯誤，請重新輸入");
            return askQuestion(scanner, key, type);
        }
        return input;
    }

    /*
    * 對輸入值進行類別檢查
    * */
    private static boolean isValid(String input, String type) {
        if ("String".equals(type)) {
            return !input.trim().isEmpty();
        }
        if ("int".equals(type)) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
// CR提出的問題
//1.(補充)有使用String.format("%2d", result)去處理排版，其實第15行可以把內容精簡成用一個
//String.format()去處理
//2.(補充)可將List改為Set，透過Set不重複的特性免去19~21的重複判斷(補充)
//這邊是自訂一個Comparator，但一般由小到大有預設的Comparator可使用
//nums.sort(Comparator.naturalOrder());
//那如果題目改成由大到小的話，你可以再看看有沒有其他預設的Comparator去處理
//3.4.
//        (CR)Employee.java 第42行，多個字串串接，改用String.format
//        (CR)HRMain.java 第42/43行，多個字串串接，改用String.format或StringBuilder
//5.(優化)
//目前當前月份天數跟閏年判斷都是用數學計算處理。
//建議用LocalDate之類的去寫看看，有既有的工具可以去判斷當前月份天數
//6.(CR)第37行的println是多的，請移除
//7.(CR)query insert update delete，try有點多層，可以簡化成兩層try
