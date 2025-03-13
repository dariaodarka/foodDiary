package com.project.foodDiary.controller;

import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.model.dto.DishDTO;
import com.project.foodDiary.entity.Dish;
import com.project.foodDiary.model.util.ValidatorDTO;
import com.project.foodDiary.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class DishController {
    private final DishService dishService;
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping("/createdish")
    public ResponseEntity<?> createDish(@RequestBody @Validated DishDTO dishDTO, BindingResult bindingResult) {
        ApiResponse response = new ApiResponse();
        Dish dish;

        if (bindingResult.hasErrors()) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(ValidatorDTO.chekErrors(bindingResult).toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            dish = dishService.createDish(dishDTO);
        } catch (IllegalArgumentException ex) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
            response.setData(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

        response.setStatus(ResponseMessage.SUCCESS.getMessage());
        response.setData("Блюдо '" + dish.getName() + "' успешно добавлено.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping ("/getDishes")
    public ResponseEntity<?> getDishes() {
        ApiResponse response = new ApiResponse();
        try {
            List <Dish> dishes = dishService.findAll();
            response.setStatus(ResponseMessage.SUCCESS.getMessage());
            response.setData(dishes.toString());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            response.setStatus(ResponseMessage.INTERNAL_SERVER_ERROR.getMessage());
            response.setData(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
