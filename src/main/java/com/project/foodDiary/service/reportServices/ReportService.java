package com.project.foodDiary.service.reportServices;

import com.project.foodDiary.entity.enums.ReportType;
import com.project.foodDiary.model.reportObjects.MealHistory;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class ReportService {
    private final Map<ReportType, ReportGenerator> generators;

    public ReportService(ReportGeneratorPDF generatorPDF, ReportGeneratorCSV generatorCSV) {
        this.generators = Map.of(
                ReportType.PDF, generatorPDF,
                ReportType.CSV, generatorCSV
        );
    }


    public String generateReport(MealHistory mealHistory, ReportType type) throws IOException {
        ReportGenerator generator = generators.get(type);
        if (generator != null) {
           return generator.generateReport(mealHistory);
        }
        else {
            throw new IllegalArgumentException("Unknown report type: " + type);
        }
    }

}
