package com.project.foodDiary.service.reportServices;

import com.opencsv.CSVWriter;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import com.project.foodDiary.model.reportObjects.DishReportObject;
import com.project.foodDiary.model.reportObjects.MealHistory;
import com.project.foodDiary.model.reportObjects.MealReportObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ReportGeneratorCSV implements ReportGenerator {

    @Override
    public String generateReport(MealHistory mealHistory) throws IOException {
        String reportFileName = "отчет_по_питанию.csv";
        String[] header = {"Дата", "Прием пищи", "Блюдо", "Калории", "Белки", "Жиры", "Углеводы"};

        File file = new File(reportFileName);

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeNext(header);

            for (DailyReportObject dailyReportObject : mealHistory.getDailyReportObjects()) {
                for (MealReportObject mealReportObject : dailyReportObject.getMeals()) {
                    for (DishReportObject dish : mealReportObject.getDishes()) {
                        String[] row = {
                                dailyReportObject.getDate().toString(),
                                mealReportObject.getName(),
                                dish.getName(),
                                String.valueOf(dish.getCalories()),
                                String.valueOf(dish.getProtein()),
                                String.valueOf(dish.getFats()),
                                String.valueOf(dish.getCarbs())
                        };
                        writer.writeNext(row);
                    }
                }
            }
        }

        return "CSV-отчет сохранен в " + file.getAbsolutePath();
    }
}
