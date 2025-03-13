package com.project.foodDiary.service.reportServices;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import com.project.foodDiary.model.reportObjects.DishReportObject;
import com.project.foodDiary.model.reportObjects.MealHistory;
import com.project.foodDiary.model.reportObjects.MealReportObject;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Service
public class ReportGeneratorPDF implements ReportGenerator {

    @Override
    public String generateReport(MealHistory mealHistory) throws FileNotFoundException {
        String fileName = "отчет_по_питанию.pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();
        Font boldFont = new Font(Font.HELVETICA, 12, Font.BOLD);
        document.add(new Paragraph("История Питания.", boldFont));

        for (DailyReportObject dailyReportObject : mealHistory.getDailyReportObjects()) {
            document.add(new Paragraph(""));
            document.add(new Paragraph("Отчет по питанию за " + dailyReportObject.getDate() +
                    ": " + dailyReportObject.getCalorieConsumption() + "\n"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            table.addCell("Дата");
            table.addCell("Прием пищи");
            table.addCell("Блюдо");
            table.addCell("Калории");
            table.addCell("Белки");
            table.addCell("Жиры");
            table.addCell("Углеводы");


            for (MealReportObject mealReportObject : dailyReportObject.getMeals()) {

                for (DishReportObject dish : mealReportObject.getDishes()) {

                    table.addCell(dailyReportObject.getDate().toString());
                    table.addCell(mealReportObject.getName());
                    table.addCell(dish.getName());
                    table.addCell(String.valueOf(dish.getCalories()));
                    table.addCell(String.valueOf(dish.getProtein()));
                    table.addCell(String.valueOf(dish.getFats()));
                    table.addCell(String.valueOf(dish.getCarbs()));
                }
            }

            document.add(table);
            document.add(new Paragraph(" \n"));
        }

        document.close();
        return ("PDF-отчет сохранен в " + new File(fileName).getAbsolutePath());
    }
}
