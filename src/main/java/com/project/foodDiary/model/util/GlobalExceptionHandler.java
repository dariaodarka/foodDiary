package com.project.foodDiary.model.util;

import com.project.foodDiary.entity.enums.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
        response.setData("Некорректный формат параметра: " + ex.getName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        ApiResponse response = new ApiResponse();
        response.setStatus(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
        response.setData(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

