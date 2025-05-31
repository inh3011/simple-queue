package com.example.simplequeue.service;

import com.example.simplequeue.model.QueueEntry;
import com.example.simplequeue.model.QueueStatus;
import com.example.simplequeue.model.QueueToken;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class InMemoryQueueRepository implements QueueRepository {
    private final Queue<QueueEntry> queue;
    private final Map<QueueToken, QueueEntry> entryMap;

    public InMemoryQueueRepository() {
        this.queue = new ConcurrentLinkedQueue<>();
        this.entryMap = new ConcurrentHashMap<>();
    }

    @Override
    public QueueEntry enqueue(String clientId) {
        QueueToken token = QueueToken.generate();
        QueueEntry entry = new QueueEntry(token, clientId);
        queue.offer(entry);
        entryMap.put(token, entry);
        return entry;
    }

    @Override
    public Optional<QueueEntry> dequeue() {
        QueueEntry entry = queue.poll();
        if (entry != null) {
            entryMap.remove(entry.getToken());
        }
        return Optional.ofNullable(entry);
    }

    @Override
    public Optional<QueueEntry> findByToken(QueueToken token) {
        return Optional.ofNullable(entryMap.get(token));
    }

    @Override
    public QueueStatus getStatus(QueueToken token) {
        QueueEntry entry = entryMap.get(token);
        if (entry == null) {
            return QueueStatus.waiting(-1);
        }

        if (queue.peek() != null && queue.peek().getToken().equals(token)) {
            return QueueStatus.ready();
        }

        int position = 0;
        for (QueueEntry e : queue) {
            if (e.getToken().equals(token)) {
                return QueueStatus.waiting(position);
            }
            position++;
        }

        return QueueStatus.waiting(-1);
    }

    @Override
    public boolean remove(QueueToken token) {
        QueueEntry entry = entryMap.remove(token);
        if (entry != null) {
            queue.remove(entry);
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return queue.size();
    }
}