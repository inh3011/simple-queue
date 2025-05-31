package com.example.simplequeue.service;

import com.example.simplequeue.model.QueueEntry;
import com.example.simplequeue.model.QueueStatus;
import com.example.simplequeue.model.QueueToken;

import java.util.Optional;

public interface QueueRepository {
    QueueEntry enqueue(String clientId);

    Optional<QueueEntry> dequeue();

    Optional<QueueEntry> findByToken(QueueToken token);

    QueueStatus getStatus(QueueToken token);

    boolean remove(QueueToken token);

    int size();
}