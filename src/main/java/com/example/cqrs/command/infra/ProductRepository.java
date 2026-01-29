package com.example.cqrs.command.infra;

import com.example.cqrs.command.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
