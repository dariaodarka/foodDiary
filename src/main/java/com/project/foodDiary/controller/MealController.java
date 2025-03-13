package com.project.foodDiary.controller;

import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.model.dto.MealDTO;
import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.model.util.ValidatorDTO;
import com.project.foodDiary.model.jwt.JwtService;
import com.project.foodDiary.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class MealController {
    private final MealService mealService;
    private final JwtService jwtService;
    public MealController(MealService mealService, JwtService jwtService) {
        this.mealService = mealService;
        this.jwtService = jwtService;
    }

    @PostMapping("/createmeal")
    public ResponseEntity<?> createMeal(@RequestHeader ("Authorization") String authHeader, @RequestBody @Validated MealDTO mealDTO, BindingResult bindingResult) {

        ApiResponse response = new ApiResponse();
        Meal meal = new Meal();

        if(!authHeader.startsWith("Bearer ")) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Токен отсутсвует или невалидный!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);
        String userId;
        try {
            userId = jwtService.extractUserId(token);
        } catch (Exception e) {
            response.setStatus(ResponseMessage.UNAUTHORIZED.getMessage());
            response.setData("Недействительный токен!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        if (bindingResult.hasErrors()) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(ValidatorDTO.chekErrors(bindingResult).toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        mealDTO.setUserId(Integer.parseInt(userId));
        try {
            meal = mealService.createMeal(mealDTO);
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
        response.setData("Прием пищи '" + meal.getName() + "' успешно добавлен");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
