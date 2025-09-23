package com.example.test.model;

import jakarta.persistence.*;

@Entity
@Table
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int foodId;

    @Column
    private String foodName;

    @Column
    private String foodType;

    @Column
    private String foodCategory;

    @Column
    private String foodDescription;

    @Column
    private double foodPrice;

    @Column
    private String foodImage;  


    public int getFoodId() {
        return foodId;
    }
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodCategory() {
        return foodCategory;
    }
    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getFoodDescription() {
        return foodDescription;
    }
    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public double getFoodPrice() {
        return foodPrice;
    }
    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodImage() {
        return foodImage;
    }
    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }
}
