package com.cathaybk.practice.nt00508726.b;

public class Extend1 {
    public static void main(String[] args) {
        //印出前面空白
        for (int i = 4; i > -1; i--) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append("  ");
            }
            for (int j = 0; j < 2*(5 - i) - 1; j++) {
                sb.append("* ");
            }
            System.out.println(sb);
        }

        for (int i = 1; i < 5; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append("  ");
            }
            for (int j = 0; j < 2*(5 - i) - 1; j++) {
                sb.append("* ");
            }
            System.out.println(sb);
        }

    }
}
