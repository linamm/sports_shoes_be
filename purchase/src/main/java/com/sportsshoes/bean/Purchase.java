package com.sportsshoes.bean;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Purchase {
    public Purchase() {
        // You can leave this empty or initialize some default values
    }

//    public Purchase(Date date, Category category, Integer quantity, Long productId, Double totalPrice) {
//        this.date = date;
//        this.category = category;
//        this.quantity = quantity;
//        this.productId = productId;
//        this.totalPrice = totalPrice;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String category;
    private Integer quantity;
    private Long productId;
    private Double totalPrice;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }



    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
