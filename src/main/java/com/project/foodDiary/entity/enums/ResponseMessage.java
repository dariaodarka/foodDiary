package com.project.foodDiary.entity.enums;

public enum ResponseMessage {
    SUCCESS("SUCCESS"),
    BAD_REQUEST("BAD_REQUEST"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
    NOT_FOUND("NOT_FOUND"),
    UNAUTHORIZED("UNAUTHORIZED");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
