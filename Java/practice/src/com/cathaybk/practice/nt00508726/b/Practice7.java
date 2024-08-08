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

            try {
                String manufactor = askQuestion(scanner, "manufactor");
                String type = askQuestion(scanner, "type");

                try (PreparedStatement pstmt = conn.prepareStatement(SELECT_CARS_SQL)) {
                    pstmt.setString(1, manufactor);
                    pstmt.setString(2, type);

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


    private static void insert(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);

            try {
                String manufactor = askQuestion(scanner, "manufactor");
                String type = askQuestion(scanner, "type");
                String price = askQuestion(scanner, "price");
                String minPrice = askQuestion(scanner, "min_price");
                try (PreparedStatement pstmt = conn.prepareStatement(INSERT_CARS_SQL)) {
                    pstmt.setString(1, manufactor);
                    pstmt.setString(2, type);
                    pstmt.setInt(3, Integer.parseInt(minPrice));
                    pstmt.setInt(4, Integer.parseInt(price));
                    pstmt.executeUpdate();
                    System.out.println("新增成功!");
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

    private static void update(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);
            try {
                String manufactor = askQuestion(scanner, "manufactor");
                String type = askQuestion(scanner, "type");
                String price = askQuestion(scanner, "price");
                String minPrice = askQuestion(scanner, "min_price");
                try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_CAR_SQL)) {
                    pstmt.setInt(1, Integer.parseInt(minPrice));
                    pstmt.setInt(2, Integer.parseInt(price));
                    pstmt.setString(3, manufactor);
                    pstmt.setString(4, type);
                    pstmt.executeUpdate();
                    System.out.println("更新成功!");
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

    private static void delete(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD)) {
            conn.setAutoCommit(false);
            try {
                String manufactor = askQuestion(scanner, "manufactor");
                String type = askQuestion(scanner, "type");
                try (PreparedStatement pstmt = conn.prepareStatement(DELETE_CAR_SQL)) {
                    pstmt.setString(1, manufactor);
                    pstmt.setString(2, type);
                    pstmt.executeUpdate();
                    System.out.println("刪除成功!");
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


    public static String askQuestion(Scanner scanner, String key) {
        System.out.print(questions.get(key));
        String input = scanner.next();
        if (input.equals("")) {
            System.out.println("請重新輸入");
            return askQuestion(scanner, key);
        }
        return input;
    }
}
