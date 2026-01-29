package com.example.cqrs.command.application;

import java.util.UUID;
import lombok.Getter;
@Getter
public class RateProductCommand implements Command {
    private final UUID id = UUID.randomUUID();
    private final Long productID;
    private final int rating;
    private final Long userId;

    public RateProductCommand(Long productID, int rating, Long userId) {
        this.productID = productID;
        this.rating = rating;
        this.userId = userId;
    }

}
