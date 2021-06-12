package com.company.object;

public class Order {
    private int id;
    private String waiter;
    private String comment;
    private int price;
    private String time;
    private String table;
    private int ready;

    public Order(int id, String waiter, String comment, int price, String time, String table, int ready) {
        this.id = id;
        this.waiter = waiter;
        this.comment = comment;
        this.price = price;
        this.time = time;
        this.table = table;
        this.ready = ready;
    }



    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public String getWaiter() {
        return waiter;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    public String getTable() {
        return table;
    }

    public int getReady() {
        return ready;
    }

    public int getPrice() {
        return price;
    }
}

