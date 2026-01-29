package com.example.cqrs.query.dao;

import com.example.cqrs.query.dto.ProductDisplay;
import java.util.List;

public interface ProductsDao {
    ProductDisplay findById(Long productId);
    List<ProductDisplay> findByName(String name);
}
