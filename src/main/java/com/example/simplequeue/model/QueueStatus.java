package com.example.simplequeue.model;

import lombok.Getter;

@Getter
public class QueueStatus {
    private final int position;
    private final boolean ready;
    private final String message;

    private QueueStatus(int position, boolean ready, String message) {
        this.position = position;
        this.ready = ready;
        this.message = message;
    }

    public static QueueStatus waiting(int position) {
        return new QueueStatus(position, false, "Waiting in queue");
    }

    public static QueueStatus ready() {
        return new QueueStatus(0, true, "Ready to be processed");
    }

}