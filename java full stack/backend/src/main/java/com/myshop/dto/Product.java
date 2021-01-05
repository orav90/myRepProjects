package com.myshop.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class Product {

    @Id
    private String _id;
    private String name;
    private String image;
    private String description;
    private String brand;
    private String category;
    private Double price;
    private Integer countInStock;
    private Double rating;
    private Integer numReviews;
    private User user;
    private Review reviews;

    public Product(){}

    public Product(/*String _id,*/ String name, String image, String description, String brand, String category, Double price, Integer countInStock, Double rating, Integer numReviews) {
        //this._id = _id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.price = price;
        this.countInStock = countInStock;
        this.rating = rating;
        this.numReviews = numReviews;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCountInStock() {
        return countInStock;
    }

    public void setCountInStock(Integer countInStock) {
        this.countInStock = countInStock;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Review getReviews() {
        return reviews;
    }

    public void setReviews(Review reviews) {
        this.reviews = reviews;
    }
}
