package com.company.object;

public class Bar {
    private int id;
    private String name;
    private int volume;
    private String structure;
    private int price;

    public Bar(int id, String name, String structure, int volume, int price) {
        this.id = id;
        this.name = name;
        this.volume = volume;
        this.structure = structure;
        this.price = price;
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

    public int getVolume() {
        return volume;
    }

    public String getStructure() {
        return structure;
    }

    public int getPrice() {
        return price;
    }
}
