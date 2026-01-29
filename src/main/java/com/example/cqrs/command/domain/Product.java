package com.example.cqrs.command.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Entity
@Table(name = "products")
public class Product {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private long unitPrice;

    @ElementCollection
    @CollectionTable(name = "product_ratings", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "rating")
    private Map<Long, Integer> ratings = new HashMap<>();

    protected Product() {}

    public Product(String name, String description, long unitPrice) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public void rateProduct(long userId, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("평점은 1이상 5이하만 가능하다.");
        }
        ratings.put(userId, rating);
    }

}
