package com.ecommerce;

public class Product {
    private int productID;
    private String name;
    private double price;
    private String codebar;
    private int quantity;
    private String imagePath;

    public Product(int productID, String name, double price, String codebar, String imagePath) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.codebar = codebar;
        this.imagePath = imagePath;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int id) {
        this.productID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCodebar() {
        return codebar;
    }

    public void setCodebar(String codebar) {
        this.codebar = codebar;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String photo) {
        this.imagePath = photo;
    }
}