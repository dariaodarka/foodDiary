package com.project.foodDiary.model.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
public class UserDTO {

    @NotBlank (message = "Пожалуйста заполните свое имя.")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\s]+$", message = "Имя пользователя может включать только буквы.")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Некорректный формат email.")
    @NotBlank(message = "Пожалуйста заполните свой имейл.")
    private String email;

    @Min(value = 18, message = "Возраст должен быть больше 18")
    @NotNull(message = "Пожалуйста укажите свой возраст.")
    private Integer age;

    @Min(value = 1, message = "Вес должен быть положительный числом")
    @NotNull(message = "Пожалуйста укажите свой вес.")
    private Integer weight;

    @Min(value = 1, message = "Рост должен быть положительным числом")
    @NotNull(message = "Пожалуйста укажите свой рост.")
    private Integer height;

    @NotBlank(message = "Пожалуйста укажите свою цель")
    private String aim;

    @NotBlank(message = "Пожалуйста укажите свой пол.")
    private String gender;

    @NotBlank(message = "Пожалуйста укажите свой пол.")
    private String activity;

    @NotBlank (message = "Пожалуйста укажите пароль.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9@#$%]).{8,}$",
            message = "Пароль должен содержать минимум 8 символов, " +
                    "одну заглавную букву, одну строчную букву и либо цифру, либо специальный символ.")
    private String password;

    public UserDTO(String name, String email, Integer age, Integer weight, Integer height, String aim, String gender, String activity, String password) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.aim = aim;
        this.gender = gender;
        this.activity = activity;
        this.password = password;
    }

    public UserDTO() {}
}
