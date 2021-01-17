package com.abhipeiris.androidassignment1shop.model;

public class ProductModel {
    private Integer id;
    private String category;
    private String productName;
    private String description;
    private Double price;
    private String img;

    public ProductModel(Integer id, String category, String productName, String description, Double price, String img) {
        this.id = id;
        this.category = category;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
