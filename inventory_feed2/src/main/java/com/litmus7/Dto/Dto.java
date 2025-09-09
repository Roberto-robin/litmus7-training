package com.litmus7.Dto;

public class Dto {
    private String sku;
    private String productName;
    private int quantity;
    private double price;

   public Dto(String trim, String trim2, int int1, double double1) {
    this.sku = trim;
    this.productName = trim2;
    this.quantity = int1;
    this.price = double1;
       
    }

   public String getSku() {
        return sku;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

 
    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

