package com.project.foodDiary.controller;

import com.project.foodDiary.entity.enums.ResponseMessage;
import com.project.foodDiary.model.util.ApiResponse;
import com.project.foodDiary.model.dto.UserDTO;
import com.project.foodDiary.entity.User;
import com.project.foodDiary.model.util.ValidatorDTO;
import com.project.foodDiary.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ApiResponse response = new ApiResponse();

        if(!ValidatorDTO.validateUserDTO(userDTO).isEmpty()){
         response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
         response.setData(ValidatorDTO.validateUserDTO(userDTO).toString());
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (bindingResult.hasErrors()) {
            response.setStatus(ResponseMessage.BAD_REQUEST.getMessage());
            response.setData(ValidatorDTO.chekErrors(bindingResult).toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user;
        try {
            user = userService.registerUser(userDTO);
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
        response.setData("Вы успешно зарегестрированы. Ваша суточная норма калорий " + user.getDailyCalories());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
