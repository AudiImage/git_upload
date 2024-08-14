package com.cathaybk.practice.nt00508726.b;

import java.sql.*;
import java.util.*;

public class Practice7Map {
    private static final String CONN_URL = "jdbc:oracle:thin:@//localhost:1521/XE";
    private static final String USER_NAME = "student";
    private static final String PASSWORD = "student123456";
    private static final String SELECT_ALL_CARS_SQL = "select * from STUDENT.CARS";
    public static final String SELECT_CARS_SQL = "select * from STUDENT.CARS where MANUFACTURER =? and TYPE = ?";
    private static final String INSERT_CARS_SQL = "insert into STUDENT.CARS (MANUFACTURER, TYPE, MIN_PRICE, PRICE) values (?, ?, ?, ?)";
    private static String UPDATE_CAR_SET_SQL = "update STUDENT.CARS set ";
    private static String UPDATE_CAR_WHERE_SQL = " where MANUFACTURER = ? and TYPE = ?";
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
            printMap(getMap(rs));

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
    }

    private static void query(Scanner scanner) {

        String manufactor = askQuestion(scanner, "manufactor", "String");
        String type = askQuestion(scanner, "type", "String");
        List<Map<String, String>> maps = exist(manufactor, type);
        if (maps.size() > 0) {
            printMap(maps);
        } else {
            System.out.println("查無此資料!");
        }

    }

    private static List<Map<String, String>> exist(String manufactor, String type) {
        try (Connection conn = DriverManager.getConnection(CONN_URL, USER_NAME, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_CARS_SQL)) {
            pstmt.setString(1, manufactor);
            pstmt.setString(2, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                return getMap(rs);
            } catch (Exception e) {
                System.out.println("查詢失敗");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.out.println("資料庫連接失敗");
            e.printStackTrace();
        }
        return null;
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
            System.out.println("輸入數字選擇需要修改的值:");
            System.out.println("1. 修改MIN_PRICE和PRICE");
            System.out.println("2. 修改MIN_PRICE");
            System.out.println("3. 修改PRICE");
            String userSelect = scanner.next();
            String manufactor = askQuestion(scanner, "manufactor", "String");
            String type = askQuestion(scanner, "type", "String");
            String price = "";
            String minPrice = "";
            StringBuilder sb = new StringBuilder();
            sb.append(UPDATE_CAR_SET_SQL);
            if (userSelect.equals("1")) {
                price = askQuestion(scanner, "price", "double");
                minPrice = askQuestion(scanner, "min_price", "double");
                sb.append("MIN_PRICE = ? ").append(",").append("PRICE = ? ");
            } else if (userSelect.equals("2")) {
                minPrice = askQuestion(scanner, "min_price", "double");
                sb.append("MIN_PRICE = ? ");
            } else if (userSelect.equals("3")) {
                price = askQuestion(scanner, "price", "double");
                sb.append("PRICE = ? ");
            }
            sb.append(UPDATE_CAR_WHERE_SQL);

            try (PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {
                int index = 1;
                if (minPrice.isEmpty()) {

                    pstmt.setDouble(index++, Double.parseDouble(price));
                } else if (price.isEmpty()) {
                    pstmt.setDouble(index++, Double.parseDouble(minPrice));
                } else {
                    pstmt.setDouble(index++, Double.parseDouble(minPrice));
                    pstmt.setDouble(index++, Double.parseDouble(price));
                }
                pstmt.setString(index++, manufactor);
                pstmt.setString(index++, type);
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
                List<Map<String, String>> maps = exist(manufactor, type);
                if (maps.size() > 0) {
                    printMap(maps);
                    System.out.printf("目前資料庫有這%d筆資料，確定要刪除嗎(輸入yes來刪除)\n", maps.size());
                    String input = scanner.next();
                    if (input.toLowerCase().equals("yes")) {
                        pstmt.setString(1, manufactor);
                        pstmt.setString(2, type);
                        pstmt.executeUpdate();
                        System.out.println("刪除成功!");
                        conn.commit();
                    }else{
                        System.out.println("刪除終止!");
                    }
                }else{
                    System.out.println("查無需要刪除資料!");
                }

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
    public static void printMap(List<Map<String, String>> maps) {
        for (Map<String, String> map : maps) {
            System.out.printf("製造商：%s，型號：%s，售價：%s，底價：%s%n",
                    map.get("MANUFACTURER"),
                    map.get("TYPE"),
                    map.get("PRICE"),
                    map.get("MIN_PRICE"));
        }

    }

    public static List<Map<String, String>> getMap(ResultSet rs) throws SQLException {
        ArrayList<Map<String, String>> maps = new ArrayList<>();
        while (rs.next()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("MANUFACTURER", rs.getString("MANUFACTURER"));
            map.put("TYPE", rs.getString("TYPE"));
            map.put("MIN_PRICE", rs.getString("MIN_PRICE"));
            map.put("PRICE", rs.getString("PRICE"));
            maps.add(map);
        }
        return maps;
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
                if (Double.parseDouble(input) > 0) {
                    return true;
                }
                return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }


}
