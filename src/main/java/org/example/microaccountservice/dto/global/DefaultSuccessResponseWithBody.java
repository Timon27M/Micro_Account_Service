package org.example.microaccountservice.dto.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DefaultSuccessResponseWithBody<T>(
        int status,
        String message,
        T data,
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        LocalDateTime timestamp
) {
    // Константы для переиспользования
    private static final int DEFAULT_STATUS = 200;
    private static final String DEFAULT_MESSAGE = "SUCCESS";

    // Фабричные методы вместо конструкторов
    public static <T> DefaultSuccessResponseWithBody<T> of(T data) {
        return DefaultSuccessResponseWithBody.<T>builder()
                .status(DEFAULT_STATUS)
                .message(DEFAULT_MESSAGE)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Без данных (только сообщение)
    public static DefaultSuccessResponseWithBody<Void> ok() {
        return DefaultSuccessResponseWithBody.<Void>builder()
                .status(DEFAULT_STATUS)
                .message(DEFAULT_MESSAGE)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Кастомное сообщение без данных
    public static DefaultSuccessResponseWithBody<Void> ok(String message) {
        return DefaultSuccessResponseWithBody.<Void>builder()
                .status(DEFAULT_STATUS)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // Для десериализации (Jackson)
    @Builder
    public DefaultSuccessResponseWithBody {}
}