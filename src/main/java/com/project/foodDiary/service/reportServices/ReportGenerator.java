package com.project.foodDiary.service.reportServices;

import com.project.foodDiary.model.reportObjects.MealHistory;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ReportGenerator {
    String generateReport(MealHistory mealHistory) throws IOException;
}
