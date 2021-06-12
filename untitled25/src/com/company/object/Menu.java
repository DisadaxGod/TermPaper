package com.company.object;

public class Menu {
    private int id;
    private String name;
    private String structure;
    private int calories;
    private int price;
    private String time;

    public Menu(int id, String name, String structure, int calories, int price, String time) {
        this.id = id;
        this.name = name;
        this.structure = structure;
        this.calories = calories;
        this.price = price;
        this.time = time;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getName(){
        return name;
    }

    public int getId() {
        return id;
    }

    public String getStructure() {
        return structure;
    }

    public int getCalories() {
        return calories;
    }

    public int getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }
}
