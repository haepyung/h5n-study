package org.kimbs.demo.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorResponse {

    private ZonedDateTime timestamp;

    private String status;

    private String message;

    private String details;

    ErrorResponse(ZonedDateTime timestamp, String status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}