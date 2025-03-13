package com.project.foodDiary.model.reportObjects;

import com.project.foodDiary.entity.Dish;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishReportObject {
    private String name;
    private int calories;
    private int protein;
    private int fats;
    private int carbs;

    public DishReportObject(Dish dish) {
        this.name = dish.getName();
        this.calories = dish.getCalories();
        this.protein = dish.getProtein();
        this.fats = dish.getFats();
        this.carbs = dish.getCarbs();
    }
    public DishReportObject() {}

    @Override
    public String toString() {
        return "DishReportObject{" +
                "name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fats=" + fats +
                ", carbs=" + carbs +
                '}';
    }
}
