package nl.paulzijlmans.api.event;

import static java.time.ZonedDateTime.now;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;

import java.time.ZonedDateTime;

public record Event<K, T>(Type eventType, K key, T data,
                          @JsonSerialize(using = ZonedDateTimeSerializer.class) ZonedDateTime eventCreatedAt) {

    public enum Type {
        CREATE,
        DELETE
    }

    public Event(Type eventType, K key, T data) {
        this(eventType, key, data, now());
    }
}
