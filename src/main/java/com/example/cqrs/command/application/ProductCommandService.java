package com.example.cqrs.command.application;

import com.example.cqrs.command.domain.Product;
import com.example.cqrs.command.infra.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Long addNewProduct(String name, String description, long unitPrice) {
        Product product = new Product(name, description, unitPrice);
        return productRepository.save(product).getId();
    }

    @Transactional
    public void handle(RateProductCommand command) {
        Product product = productRepository.findById(command.getProductID())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        product.rateProduct(command.getUserId(), command.getRating());

        productRepository.save(product);
    }
}
