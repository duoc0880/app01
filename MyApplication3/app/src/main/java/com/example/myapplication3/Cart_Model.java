package com.example.myapplication3;

public class Cart_Model {
    Integer id;
    String product;
    Integer amount;
    String img;

    long price;

    public Cart_Model(Integer id, String product, Integer amount, String img) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.img = img;
        this.price = price;
    }

    public Cart_Model(Integer id, String product, Integer amount, String img, long price) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.img = img;
        this.price = price;
    }

    public Cart_Model(Integer id, String product, Integer amount, long price) {
        this.id = id;
        this.product = product;
        this.amount = amount;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
