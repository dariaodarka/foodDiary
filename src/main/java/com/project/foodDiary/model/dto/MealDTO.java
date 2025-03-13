package com.project.foodDiary.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MealDTO {
    @NotBlank(message = "Пожалуйста укажите название приема пищи.")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\s]+$", message = "Название может включать толбко буквы.")
    private String name;

    @NotNull(message = "Пожалуйста укажите дату приема пищи.")
    private LocalDate date;

    @NotNull (message = "Пожалуйста укажите айди блюд")
    private List<Long> dishIds;

    private long userId;

    public MealDTO(String name, LocalDate date, long userId, List<Long> dishIds) {
        this.name = name;
        this.date = date;
        this.userId = userId;
        this.dishIds = dishIds;
    }

    public MealDTO() {
    }

    public void setDishes(List<String> collect) {
    }
}
