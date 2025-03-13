package com.project.foodDiary.model.reportObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealReportObject {
    private String name;
    private List<DishReportObject> dishes;

    public MealReportObject(String name, List<DishReportObject> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public MealReportObject() {}
}
