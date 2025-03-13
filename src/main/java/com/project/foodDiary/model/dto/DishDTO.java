package com.project.foodDiary.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDTO {

    @NotBlank(message = "Пожалуйста укажите название блюда.")
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z\\s]+$", message = "Название может включать толбко буквы.")
    private String name;

    @NotNull(message = "Пожалуйста укажите калораж блюда.")
    @Min(value = 1, message = "Количество калорий должно быть положительным числом.")
    private int calories;

    @NotNull (message = "Пожалуйств укажите количество белков блюда.")
    @Min(value = 1, message = "Количество белков должно быть положительным числом.")
    private int protein;

    @NotNull (message = "Пожалуйста укажите количество углеводов блюда.")
    @Min(value = 1, message = "Количество углеводов должно быть положительным числом.")
    private int carbs;

    @Min(value = 1, message = "Количество жиров должно быть положительным числом.")
    @NotNull (message = "Пожалуйста укажите количество жиров блюда.")
    private int fats;

    public DishDTO(String name, int calories, int protein, int carbs, int fats) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public DishDTO() {}
}
