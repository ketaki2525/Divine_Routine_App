package com.example.project;

public class Product {
    private String name;
    private String description;
    private double price;
    private String ingredients;
    private int imageResId;
    // Constructor
    public Product(String name, String description, double price, String ingredients) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
    }
    private String allergens;

    public Product(String name, String description, double price, String ingredients, String allergens) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
        this.allergens = allergens;
    }

    public String getAllergens() {
        return allergens;
    }
    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getIngredients() { return ingredients; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setIngredients(String ingredients) { this.ingredients = ingredients; }
}
