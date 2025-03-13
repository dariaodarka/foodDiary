package com.project.foodDiary.model.reportObjects;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealHistory {
    private List<DailyReportObject> dailyReportObjects;

    public MealHistory(List<DailyReportObject> dailyReportObjects) {
        this.dailyReportObjects = dailyReportObjects;
    }

    public MealHistory() {}
}
