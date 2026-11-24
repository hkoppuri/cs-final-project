package org.example;

public class Food {

    private String name;
    private double calories;
    private double protein;
    private double carbs;
    private double fat;


    public Food(String name, int calories, int protein, int carbs, int fat) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
    }

    public String getName() {
        return name;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

}
