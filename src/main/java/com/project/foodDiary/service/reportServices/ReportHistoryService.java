package com.project.foodDiary.service.reportServices;

import com.project.foodDiary.entity.Meal;
import com.project.foodDiary.model.reportObjects.DailyReportObject;
import com.project.foodDiary.model.reportObjects.DishReportObject;
import com.project.foodDiary.model.reportObjects.MealHistory;
import com.project.foodDiary.model.reportObjects.MealReportObject;
import com.project.foodDiary.repository.MealRespository;
import com.project.foodDiary.service.CalorieService;
import com.project.foodDiary.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportHistoryService {
    private final MealRespository mealRespository;
    private final UserService userService;
    private final CalorieService calorieService;

    public ReportHistoryService(MealRespository mealRespository, UserService userService, CalorieService calorieService) {
        this.mealRespository = mealRespository;
        this.userService = userService;
        this.calorieService = calorieService;
    }

    public DailyReportObject getDailyReport(LocalDate date, long userId) {
        List<Meal> meals = mealRespository.findMealsByUserAndDate(userId, date);
        if(meals.isEmpty()){
            throw new IllegalArgumentException("У данного пользователя с id " + userId + " нет истории за " + date + ".");
        }
        int baseCalorieConsumption = userService.getCalorieConsumption(userId);

        DailyReportObject dailyReportObject = new DailyReportObject();
        dailyReportObject.setDate(date);
        List<MealReportObject> mealReportObjects = new ArrayList<>();

        for (Meal meal : meals) {
            MealReportObject mealReportObject = new MealReportObject();
            mealReportObject.setName(meal.getName());
            mealReportObject.setDishes(meal.getDishes().stream()
                    .map(Dish -> new DishReportObject(Dish))
                    .collect(Collectors.toList()));
            mealReportObjects.add(mealReportObject);
        }

        dailyReportObject.setMeals(mealReportObjects);
        calorieService.setKBJU(dailyReportObject, meals, baseCalorieConsumption);
        return dailyReportObject;
    }

    public MealHistory getTotalMealHistory(int userId) {
        MealHistory mealHistory = new MealHistory();
        List<Meal> meals = mealRespository.findMealsByUser((long) userId);

        if(meals.isEmpty()){
            throw new IllegalArgumentException("У данного пользователя с id " + userId + " нет истории.");
        }
        Set<LocalDate> dates = meals.stream()
                .map(meal -> meal.getDate())
                .collect(Collectors.toSet());

        List<DailyReportObject> dailyReportObjects = dates.stream()
                .map(date -> getDailyReport(date, userId))
                .toList();


        mealHistory.setDailyReportObjects(dailyReportObjects);

        return mealHistory;
    }

    public MealHistory getMealHistoryByStartEndDate(int userId, LocalDate startDate, LocalDate endDate) {
        MealHistory mealHistory = new MealHistory();
        List<Meal> meals = mealRespository.findMealsByUserAndStartEndDate((long) userId, startDate, endDate);

        if(meals.isEmpty()){
            throw new IllegalArgumentException("У данного пользователя с id " + userId + " нет истории.");
        }

        Set<LocalDate> dates = meals.stream()
                .map(meal -> meal.getDate())
                .collect(Collectors.toSet());

        List<DailyReportObject> dailyReportObjects = dates.stream()
                .map(date -> getDailyReport(date, userId))
                .toList();

        for (DailyReportObject dailyReportObject : dailyReportObjects) {
            System.out.println(dailyReportObject);
        }

        mealHistory.setDailyReportObjects(dailyReportObjects);

        return mealHistory;
    }
}
