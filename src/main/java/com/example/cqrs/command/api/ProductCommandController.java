package com.example.cqrs.command.api;

import com.example.cqrs.command.application.ProductCommandService;
import com.example.cqrs.command.application.RateProductCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/products")
public class ProductCommandController {

    private final ProductCommandService commandService;

    public ProductCommandController(ProductCommandService commandService) {
        this.commandService = commandService;
    }

    record CreateProductRequest(String name, String description, long unitPrice) {
    }

    record RateProductRequest(int rating, Long userId) {
    }

    @PostMapping
    public Long create(@RequestBody CreateProductRequest req) {
        return commandService.addNewProduct(req.name, req.description, req.unitPrice);
    }

    @PostMapping("/{productId}/ratings")
    public void rate(@PathVariable Long productId, @RequestBody RateProductRequest req) {
        commandService.handle(new RateProductCommand(productId, req.rating, req.userId));
    }
}
