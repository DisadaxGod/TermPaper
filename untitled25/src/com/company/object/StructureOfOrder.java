package com.company.object;

public class StructureOfOrder {
    private int id;
    private int numberOfOrder;
    private String list;

    public StructureOfOrder(int id, int numberOfOrder, String list) {
        this.id = id;
        this.numberOfOrder = numberOfOrder;
        this.list = list;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public int getId() {
        return id;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public String getList() {
        return list;
    }
}
