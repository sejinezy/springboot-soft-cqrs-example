package com.example.cqrs.command.application;

import java.util.UUID;

public interface Command {
    UUID getId();
}
