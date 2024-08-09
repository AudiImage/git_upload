package com.cathaybk.practice.nt00508726.b;

import java.sql.*;
import java.util.*;

public class Practice7Hard {
    private static final String CONN_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String USER_NAME = "student";
    private static final String PASSWORD = "student123456";
    private static final String SELECT_ALL_CARS_SQL = "select * from STUDENT.CARS";
    public static final String SELECT_CARS_SQL = "select * from STUDENT.CARS where MANUFACTURER =? and TYPE = ?";
    private static final String INSERT_CARS_SQL = "insert into STUDENT.CARS (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)";
    private static final String UPDATE_CAR_SQL = "update STUDENT.CARS set MIN_PRICE = ?, PRICE = ? where MANUFACTURER = ? and TYPE = ?";
    public static final String DELETE_CAR_SQL = "delete from STUDENT.CARS where MANUFACTURER = ? and TYPE = ?";
    private static final HashMap<String, String> questions;
    private static final Map<String, Runnable> commands;
    public static final Scanner scanner;

    static {
        questions = new HashMap<>();
        questions.put("manufactor", "請輸入製造商:");
        questions.put("type", "請輸入類型:");
        questions.put("min_price", "請輸入底價:");
        questions.put("price", "請輸入售價:");
        commands = new HashMap<>();
        scanner = new Scanner(System.in);
        commands.put("select", () -> query(scanner));
        commands.put("insert", () -> insert(scanner));
        commands.put("update", () -> update(scanner));
        commands.put("delete", () -> delete(scanner));
    }


    public static void main(String[] args) {

        query_all();

        while (true) {
            System.out.println("請選擇以下指令輸入: select、insert、update、delete");
            String input = scanner.next().toLowerCase();
            Runnable command = commands.get(input);
            if (command != null) {
                command.run();
                break;
            }
            System.out.println("輸入錯誤，請重新輸入指令");
        }
    }

    public static void query_all() {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_CARS_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            printData(rs);
        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
    }

    private static void query(Scanner scanner) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_CARS_SQL)) {
            String manufactor = askQuestion(scanner, "manufactor", "String");
            String type = askQuestion(scanner, "type", "String");
            pstmt.setString(1, manufactor);
            pstmt.setString(2, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                printData(rs);
            } catch (Exception e) {
                System.out.println("查詢失敗");
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
                String price = askQuestion(scanner, "price", "double");
                String minPrice = askQuestion(scanner, "min_price", "double");
                pstmt.setString(1, manufactor);
                pstmt.setString(2, type);
                pstmt.setDouble(3, Double.parseDouble(minPrice));
                pstmt.setDouble(4, Double.parseDouble(price));
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
                String price = askQuestion(scanner, "price", "double");
                String minPrice = askQuestion(scanner, "min_price", "double");
                pstmt.setDouble(1, Double.parseDouble(minPrice));
                pstmt.setDouble(2, Double.parseDouble(price));
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
     * 打印資料
     */
    public static void printData(ResultSet rs) throws SQLException {
        while (rs.next()) {
            System.out.printf("製造商：%s，型號：%s，售價：%s，底價：%s%n",
                    rs.getString("MANUFACTURER"),
                    rs.getString("TYPE"),
                    rs.getString("PRICE"),
                    rs.getString("MIN_PRICE"));
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
        if ("double".equals(type)) {
            try {
                Double.parseDouble(input);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }


}
