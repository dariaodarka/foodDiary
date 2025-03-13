package com.project.foodDiary.model.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String status;
    private String data;

    public ApiResponse(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public ApiResponse() {}

}
