package com.cathaybk.practice.nt00508726.b.Practice3_4;

public class Sales extends Employee{
    private int bonus;
    private int payment;

    public Sales(String name, String department, int salary, int bonus) {
        super(name, department, salary);
        this.bonus = (int) Math.round(bonus * 0.05);
        this.payment = salary + this.bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("業績獎金: " + bonus);
        System.out.println("總計: " + payment);
    }
}
