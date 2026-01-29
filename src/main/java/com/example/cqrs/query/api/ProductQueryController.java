package com.example.cqrs.query.api;

import com.example.cqrs.query.dao.ProductsDao;
import com.example.cqrs.query.dto.ProductDisplay;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queries/products")
public class ProductQueryController {

    private final ProductsDao productsDao;

    public ProductQueryController(ProductsDao productsDao) {
        this.productsDao = productsDao;
    }

    @GetMapping("/{productId}")
    public ProductDisplay findById(@PathVariable Long productId) {
        return productsDao.findById(productId);
    }

    @GetMapping
    public List<ProductDisplay> findByName(@RequestParam String name) {
        return productsDao.findByName(name);
    }
}
