package com.example.simplequeue.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueueEntry {
    private final QueueToken token;
    private final LocalDateTime createdAt;
    private final String clientId;

    public QueueEntry(QueueToken token, String clientId) {
        this.token = token;
        this.clientId = clientId;
        this.createdAt = LocalDateTime.now();
    }

}