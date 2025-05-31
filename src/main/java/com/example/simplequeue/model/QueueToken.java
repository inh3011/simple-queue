package com.example.simplequeue.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public class QueueToken {
    private final String value;

    private QueueToken(String value) {
        this.value = value;
    }

    public static QueueToken generate() {
        return new QueueToken(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        QueueToken that = (QueueToken) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}