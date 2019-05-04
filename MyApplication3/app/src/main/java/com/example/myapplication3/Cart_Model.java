package com.example.myapplication3;

public class Cart_Model {
    Integer id;
    String product;
    Integer amount;
    String img;
    Double price;

    public Cart_Model(Integer id, String product, Integer amount, String img) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.img = img;
        this.price = price;
    }

    public Cart_Model(Integer id, String product, Integer amount, String img, Double price) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.img = img;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
