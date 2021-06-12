package com.company.object;

public class Waiter {
    private int id;
    private String name;
    private String secondName;
    private int salary;

    public Waiter(int id, String name, String secondName, int salary) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getSalary() {
        return salary;
    }
}
