package com.project.foodDiary.model.reportObjects;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class DailyReportObject {
    private LocalDate date;
    private List<MealReportObject> meals;
    private int totalCalories;
    private int totalProtein;
    private int totalFats;
    private int totalCarbs;
    private String calorieConsumption;

    public DailyReportObject(List<MealReportObject> meals, LocalDate date, int totalCalories, int totalProtein, int totalFats, int totalCarbs, String calorieConsumption) {
        this.meals = meals;
        this.date = date;
        this.totalCalories = totalCalories;
        this.totalProtein = totalProtein;
        this.totalFats = totalFats;
        this.totalCarbs = totalCarbs;
        this.calorieConsumption = calorieConsumption;
    }

    public DailyReportObject() {}
}
