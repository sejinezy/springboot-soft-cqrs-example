package com.example.cqrs.query.dto;

public record ProductDisplay(
        Long id,
        String name,
        String description,
        long unitPrice,
        boolean outOfStock,
        double userRating
) {}
